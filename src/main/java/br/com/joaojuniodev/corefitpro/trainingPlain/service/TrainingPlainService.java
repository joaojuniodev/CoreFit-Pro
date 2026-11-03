package br.com.joaojuniodev.corefitpro.trainingPlain.service;

import br.com.joaojuniodev.corefitpro.exceptions.NotFoundException;
import br.com.joaojuniodev.corefitpro.mapper.trainingPlain.TrainingPlainMapper;
import br.com.joaojuniodev.corefitpro.trainingPlain.dto.request.TrainingPlainRequestDTO;
import br.com.joaojuniodev.corefitpro.trainingPlain.dto.response.TrainingPlainDetailsDTO;
import br.com.joaojuniodev.corefitpro.trainingPlain.dto.response.TrainingPlainResponseDTO;
import br.com.joaojuniodev.corefitpro.trainingPlain.repository.TrainingPlainRepository;
import br.com.joaojuniodev.corefitpro.trainingItem.repository.TrainingItemRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.Map;
import java.util.HashMap;
import java.util.HashSet;
import br.com.joaojuniodev.corefitpro.trainingPlain.dto.request.TrainingPlanSaveDTO;
import br.com.joaojuniodev.corefitpro.trainingItem.model.TrainingItem;
import br.com.joaojuniodev.corefitpro.trainingItem.enums.DaysOfWeek;
import br.com.joaojuniodev.corefitpro.training.repository.TrainingRepository;
import br.com.joaojuniodev.corefitpro.trainee.repository.TraineeRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

@Service
@Transactional(readOnly = true)
public class TrainingPlainService {
    private static final Logger logger = LoggerFactory.getLogger(TrainingPlainService.class.getName());

    private final TrainingPlainRepository trainingPlainRepository;
    private final TrainingItemRepository trainingItemRepository;
    private final TrainingPlainMapper trainingPlainMapper;
    private final TrainingRepository trainings;
    private final TraineeRepository trainees;

    public TrainingPlainService(TrainingPlainRepository trainingPlainRepository, TrainingItemRepository trainingItemRepository, TrainingPlainMapper trainingPlainMapper, TrainingRepository trainings, TraineeRepository trainees) {
        this.trainingPlainRepository = trainingPlainRepository;
        this.trainingItemRepository = trainingItemRepository;
        this.trainingPlainMapper = trainingPlainMapper;
        this.trainings = trainings;
        this.trainees = trainees;
    }

    public List<TrainingPlainResponseDTO> getAll() {
        logger.info("Getting All Trainings Plain");

        return trainingPlainRepository.findAll()
            .stream()
            .map(trainingPlainMapper::toResponse)
            .toList();
    }

    public TrainingPlainDetailsDTO getById(UUID id) {
        logger.info("Getting By Training Plain Id");

        var entity = trainingPlainRepository.findById(id)
            .orElseThrow(() -> new NotFoundException("Not found this Training Plain Id: " + id));
        return trainingPlainMapper.toDetails(entity, trainingItemRepository.findAllByTrainingPlainId(entity.getId()));
    }

    @Transactional(readOnly = true)
    public TrainingPlainDetailsDTO getByTrainee(UUID traineeId) {
        logger.info("Getting Training Plain by Trainee Id");

        var entity = trainingPlainRepository.findByTrainee(traineeId)
            .orElseThrow(() ->
                new NotFoundException(
                    "Not found Training Plan for Trainee Id: " + traineeId
                )
            );

        var trainingItems =
            trainingItemRepository.findAllByTrainingPlainId(entity.getId());

        return trainingPlainMapper.toDetails(entity, trainingItems);
    }

    @Transactional
    public TrainingPlainResponseDTO create(TrainingPlainRequestDTO trainingPlain) {
        logger.info("Creating new Training Plain");

        trainingPlainRepository.findAllActiveByTrainee(trainingPlain.traineeId())
            .forEach(plan -> plan.setActive(false));
        trainingPlainRepository.flush();
        var entity = trainingPlainMapper.toEntity(trainingPlain);
        var trainingPlainCreated = trainingPlainRepository.save(entity);
        return trainingPlainMapper.toResponse(trainingPlainCreated);
    }

    @Transactional
    public TrainingPlainResponseDTO update(TrainingPlainRequestDTO trainingPlain) {
        logger.info("Updating Training Plain");

        var entity = trainingPlainRepository.findById(trainingPlain.id())
            .orElseThrow(() -> new NotFoundException("Not found this Training Plain Id: " + trainingPlain.id()));
        entity.setDescription(trainingPlain.description());
        entity.setObjective(trainingPlain.objective());
        entity.setGoal(trainingPlain.goal());

        var trainingPlainUpdated = trainingPlainRepository.save(entity);

        return trainingPlainMapper.toResponse(trainingPlainUpdated);
    }

    @Transactional
    public TrainingPlainDetailsDTO saveComplete(TrainingPlanSaveDTO request, String username) {
        if (request.traineeId() == null || request.personalTrainerId() == null ||
            request.description() == null || request.description().isBlank() || request.description().length() > 500 ||
            request.objective() == null || request.objective().isBlank() || request.objective().length() > 255 ||
            (request.goal() != null && request.goal().length() > 255) || request.trainings() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Preencha descrição e objetivo e confira os limites dos campos.");
        }
        var trainee = trainees.findById(request.traineeId())
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Aluno não encontrado."));
        var trainer = trainee.getPersonalTrainer();
        if (trainer == null || !trainer.getId().equals(request.personalTrainerId()) ||
            !trainer.getUser().getUsername().equals(username)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Este aluno não pertence ao seu perfil.");
        }
        var days = new HashSet<DaysOfWeek>();
        for (var day : request.trainings()) {
            if (day == null || day.dayOfWeek() == null || day.trainingId() == null || !days.add(day.dayOfWeek())) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Informe apenas um treinamento por dia.");
            }
            var training = trainings.findById(day.trainingId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Treinamento não encontrado."));
            if (training.getPersonalTrainer() == null || !training.getPersonalTrainer().getId().equals(trainer.getId())) {
                throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Selecione treinamentos do seu catálogo.");
            }
        }
        var planRequest = new TrainingPlainRequestDTO(request.id(), request.description().trim(),
            request.objective().trim(), trainer.getId(), trainee.getId(), request.goal() == null ? null : request.goal().trim());
        UUID planId;
        if (request.id() == null) {
            planId = create(planRequest).id();
        } else {
            var current = trainingPlainRepository.findById(request.id())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Plano não encontrado."));
            if (!current.getTrainee().getId().equals(trainee.getId()) || !Boolean.TRUE.equals(current.getActive())) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Este plano não é o plano ativo do aluno. Reabra a edição.");
            }
            planId = update(planRequest).id();
        }
        var plan = trainingPlainRepository.findById(planId).orElseThrow();
        Map<DaysOfWeek, TrainingItem> existing = new HashMap<>();
        for (var item : trainingItemRepository.findAllByTrainingPlainId(planId)) {
            var duplicate = existing.putIfAbsent(item.getDayOfWeek(), item);
            if (duplicate != null || !days.contains(item.getDayOfWeek())) trainingItemRepository.delete(item);
        }
        for (var day : request.trainings()) {
            var item = existing.get(day.dayOfWeek());
            if (item == null) {
                item = new TrainingItem();
                item.setTrainingPlain(plan);
                item.setDayOfWeek(day.dayOfWeek());
                item.setCompleted(false);
            }
            if (item.getTraining() == null || !item.getTraining().getId().equals(day.trainingId())) {
                item.setCompleted(false);
                item.setDateTime(null);
            }
            item.setTraining(trainings.getReferenceById(day.trainingId()));
            trainingItemRepository.save(item);
        }
        trainingItemRepository.flush();
        return trainingPlainMapper.toDetails(plan, trainingItemRepository.findAllByTrainingPlainId(planId));
    }

    @Transactional
    public void delete(UUID id) {
        logger.info("Deleting Training Plain");

        var entity = trainingPlainRepository.findById(id)
            .orElseThrow(() -> new NotFoundException("Not found this Training Plain Id: " + id));
        trainingPlainRepository.delete(entity);
    }
}
