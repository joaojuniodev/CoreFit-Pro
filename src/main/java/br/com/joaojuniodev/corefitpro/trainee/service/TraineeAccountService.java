package br.com.joaojuniodev.corefitpro.trainee.service;

import br.com.joaojuniodev.corefitpro.security.repository.UserRepository;
import br.com.joaojuniodev.corefitpro.trainee.dto.request.TraineeAccountRequestDTO;
import br.com.joaojuniodev.corefitpro.trainee.dto.request.TraineePasswordRequestDTO;
import br.com.joaojuniodev.corefitpro.trainee.dto.response.TraineeAccountResponseDTO;
import br.com.joaojuniodev.corefitpro.trainee.model.Trainee;
import br.com.joaojuniodev.corefitpro.trainee.repository.TraineeRepository;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import java.util.UUID;

@Service
@Transactional(readOnly = true)
public class TraineeAccountService {
    private static final String EMAIL_PATTERN = "^[^\\s@]+@[^\\s@]+\\.[^\\s@]+$";
    private final TraineeRepository trainees;
    private final UserRepository users;
    private final PasswordEncoder encoder;

    public TraineeAccountService(TraineeRepository trainees, UserRepository users, PasswordEncoder encoder) {
        this.trainees = trainees;
        this.users = users;
        this.encoder = encoder;
    }

    public TraineeAccountResponseDTO get(UUID traineeId, String trainerUsername) {
        return toResponse(ownedTrainee(traineeId, trainerUsername));
    }

    @Transactional
    public TraineeAccountResponseDTO update(UUID traineeId, String trainerUsername, TraineeAccountRequestDTO request) {
        var trainee = ownedTrainee(traineeId, trainerUsername);
        var user = trainee.getUser();
        validate(request);
        String username = request.username().trim();
        String email = request.email().trim();
        if (users.existsByUsernameAndIdNot(username, user.getId())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Este usuário já está cadastrado. Escolha outro usuário.");
        }
        if (users.existsByEmailIgnoreCaseAndIdNot(email, user.getId())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Este e-mail já está vinculado a outra conta.");
        }
        trainee.setFirstName(request.firstName().trim());
        trainee.setLastName(request.lastName().trim());
        user.setUsername(username);
        user.setFullName(trainee.getFirstName() + " " + trainee.getLastName());
        user.setEmail(email);
        user.setRecoveryEmail(request.recoveryEmail() == null || request.recoveryEmail().isBlank()
            ? email : request.recoveryEmail().trim());
        user.setEnabled(request.enabled());
        users.saveAndFlush(user);
        trainees.save(trainee);
        return toResponse(trainee);
    }

    @Transactional
    public void resetPassword(UUID traineeId, String trainerUsername, TraineePasswordRequestDTO request) {
        var trainee = ownedTrainee(traineeId, trainerUsername);
        if (request == null || request.newPassword() == null ||
            request.newPassword().length() < 6 || request.newPassword().length() > 128 ||
            request.newPassword().isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "A nova senha deve ter entre 6 e 128 caracteres.");
        }
        var user = trainee.getUser();
        user.setPassword(encoder.encode(request.newPassword()));
        user.setCredentialsNonExpired(true);
        users.save(user);
    }

    private Trainee ownedTrainee(UUID traineeId, String trainerUsername) {
        var trainee = trainees.findById(traineeId)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Aluno não encontrado."));
        var trainer = trainee.getPersonalTrainer();
        if (trainer == null || trainer.getUser() == null || !trainer.getUser().getUsername().equals(trainerUsername)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Você só pode gerenciar as contas dos seus alunos.");
        }
        if (trainee.getUser() == null || trainee.getUser().getRoles().stream().noneMatch(role -> "TRAINEE".equals(role.getName()))) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "O aluno não possui uma conta de acesso válida.");
        }
        return trainee;
    }

    private void validate(TraineeAccountRequestDTO request) {
        if (request == null || blankOrTooLong(request.firstName(), 80) || blankOrTooLong(request.lastName(), 80) ||
            (request.firstName().trim() + " " + request.lastName().trim()).length() > 150) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Informe nome e sobrenome válidos (nome completo com até 150 caracteres).");
        }
        if (blankOrTooLong(request.username(), 50) || !request.username().trim().matches("^[\\p{L}\\p{N}_.@-]+$")) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Informe um usuário com até 50 caracteres, sem espaços.");
        }
        if (blankOrTooLong(request.email(), 150) || !request.email().trim().matches(EMAIL_PATTERN) ||
            (request.recoveryEmail() != null && !request.recoveryEmail().isBlank() &&
             (request.recoveryEmail().trim().length() > 150 || !request.recoveryEmail().trim().matches(EMAIL_PATTERN)))) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Informe e-mails válidos com até 150 caracteres.");
        }
        if (request.enabled() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Informe se o acesso do aluno está habilitado.");
        }
    }

    private boolean blankOrTooLong(String value, int maximum) {
        return value == null || value.isBlank() || value.trim().length() > maximum;
    }

    private TraineeAccountResponseDTO toResponse(Trainee trainee) {
        var user = trainee.getUser();
        return new TraineeAccountResponseDTO(trainee.getId(), trainee.getFirstName(), trainee.getLastName(),
            user.getUsername(), user.getEmail(), user.getRecoveryEmail(), Boolean.TRUE.equals(user.getEnabled()));
    }
}
