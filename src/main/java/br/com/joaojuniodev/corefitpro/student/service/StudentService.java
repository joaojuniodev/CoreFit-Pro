package br.com.joaojuniodev.corefitpro.student.service;

import br.com.joaojuniodev.corefitpro.mapper.trainingPlain.TrainingPlainMapper;
import br.com.joaojuniodev.corefitpro.student.dto.StudentHomeDTO;
import br.com.joaojuniodev.corefitpro.trainee.model.Trainee;
import br.com.joaojuniodev.corefitpro.trainee.repository.TraineeRepository;
import br.com.joaojuniodev.corefitpro.trainingItem.model.TrainingItem;
import br.com.joaojuniodev.corefitpro.trainingItem.repository.TrainingItemRepository;
import br.com.joaojuniodev.corefitpro.trainingPlain.repository.TrainingPlainRepository;
import br.com.joaojuniodev.corefitpro.infrastructure.backblaze.B2StorageGateway;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import java.time.*;
import java.time.temporal.TemporalAdjusters;
import java.util.UUID;

@Service
@Transactional(readOnly = true)
public class StudentService {
    private static final ZoneId ZONE = ZoneId.of("America/Sao_Paulo");
    private final TraineeRepository trainees;
    private final TrainingPlainRepository plans;
    private final TrainingItemRepository items;
    private final TrainingPlainMapper mapper;
    private final B2StorageGateway storage;

    public StudentService(TraineeRepository trainees, TrainingPlainRepository plans,
                          TrainingItemRepository items, TrainingPlainMapper mapper, B2StorageGateway storage) {
        this.trainees = trainees;
        this.plans = plans;
        this.items = items;
        this.mapper = mapper;
        this.storage = storage;
    }

    private Trainee currentStudent(String username) {
        return trainees.findByUserUsername(username)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Esta conta ainda não está vinculada a um aluno. Fale com seu personal."));
    }

    public StudentHomeDTO getHome(String username) {
        var trainee = currentStudent(username);
        var trainer = trainee.getPersonalTrainer();
        var plan = plans.findByTrainee(trainee.getId()).orElse(null);
        LocalDate monday = LocalDate.now(ZONE).with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));
        var details = plan == null ? null : mapper.toDetails(plan, items.findAllByTrainingPlainId(plan.getId()));
        return new StudentHomeDTO(trainee.getId(), trainee.getFirstName(), trainee.getLastName(),
            trainee.getAvatarKey() == null ? null : storage.getTemporaryUrl(trainee.getAvatarKey(), Duration.ofHours(1)),
            trainer == null ? null : trainer.getFirstName() + " " + trainer.getLastName(), monday, details);
    }

    @Transactional
    public void complete(String username, UUID itemId, boolean completed) {
        Trainee trainee = currentStudent(username);
        TrainingItem item = items.findById(itemId)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Treino não encontrado."));
        var activePlan = plans.findByTrainee(trainee.getId()).orElse(null);
        if (activePlan == null || item.getTrainingPlain() == null ||
            !activePlan.getId().equals(item.getTrainingPlain().getId())) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Este treino não pertence ao seu plano ativo.");
        }
        item.setCompleted(completed);
        item.setDateTime(completed ? LocalDateTime.now(ZONE) : null);
        items.save(item);
    }
}
