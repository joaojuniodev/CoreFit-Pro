package br.com.joaojuniodev.corefitpro.training.service;

import br.com.joaojuniodev.corefitpro.exceptions.IsEmptyObjectException;
import br.com.joaojuniodev.corefitpro.exceptions.NotFoundException;
import br.com.joaojuniodev.corefitpro.exerciseItem.dto.request.ExerciseItemRequestDTO;
import br.com.joaojuniodev.corefitpro.exerciseItem.model.ExerciseItem;
import br.com.joaojuniodev.corefitpro.exerciseItem.repository.ExerciseItemRepository;
import br.com.joaojuniodev.corefitpro.mapper.exerciseItem.ExerciseItemMapper;
import br.com.joaojuniodev.corefitpro.mapper.training.TrainingMapper;
import br.com.joaojuniodev.corefitpro.muscleGroup.model.MuscleGroup;
import br.com.joaojuniodev.corefitpro.muscleGroup.repository.MuscleGroupRepository;
import br.com.joaojuniodev.corefitpro.training.dto.request.TrainingRequestDTO;
import br.com.joaojuniodev.corefitpro.training.dto.response.TrainingResponseDTO;
import br.com.joaojuniodev.corefitpro.training.model.Training;
import br.com.joaojuniodev.corefitpro.training.repository.TrainingRepository;
import br.com.joaojuniodev.corefitpro.training.repository.spec.TrainingSpecification;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Service
@Transactional(readOnly = true)
public class TrainingService {
    private static final Logger logger = LoggerFactory.getLogger(TrainingService.class.getName());

    private final TrainingRepository trainingRepository;
    private final MuscleGroupRepository muscleGroupRepository;
    private final ExerciseItemRepository exerciseItemRepository;
    private final TrainingMapper trainingMapper;
    private final ExerciseItemMapper exerciseItemMapper;

    public TrainingService(TrainingRepository trainingRepository, MuscleGroupRepository muscleGroupRepository, ExerciseItemRepository exerciseItemRepository, TrainingMapper trainingMapper, ExerciseItemMapper exerciseItemMapper) {
        this.trainingRepository = trainingRepository;
        this.muscleGroupRepository = muscleGroupRepository;
        this.exerciseItemRepository = exerciseItemRepository;
        this.trainingMapper = trainingMapper;
        this.exerciseItemMapper = exerciseItemMapper;
    }

    public List<TrainingResponseDTO> getAll(UUID personalTrainerId) {
        logger.info("Getting All Trainings");

        TrainingSpecification spec = new TrainingSpecification();
        spec.addToSpecifications(personalTrainerId);

        return trainingRepository
            .findAll(spec.apply())
            .stream()
            .map(trainingMapper::toResponse)
            .toList();
    }

    public TrainingResponseDTO getById(UUID id) {
        logger.info("Getting By Training Id");

        var entity = trainingRepository.findById(id)
            .orElseThrow(() -> new NotFoundException("Not found this Training Id: " + id));
        return trainingMapper.toResponse(entity);
    }

    @Transactional
    public TrainingResponseDTO create(TrainingRequestDTO training) {
        logger.info("Creating new Training");

        if (training.exerciseItems().isEmpty()) throw new IsEmptyObjectException("Exercise Items is empty");
        if (training.muscleGroupsId().isEmpty()) throw new IsEmptyObjectException(("Muscle Groups Id is empty"));

        Training entity = trainingMapper.toEntity(training);

        List<MuscleGroup> muscleGroups = muscleGroupRepository.findAllById(training.muscleGroupsId());
        entity.setMuscleGroups(new HashSet<>(muscleGroups));

        var saved = trainingRepository.save(entity);

        Set<ExerciseItem> exerciseItems = new HashSet<>();

        for (ExerciseItemRequestDTO exerciseItem : training.exerciseItems()) {
            var exerciseItemEntity = exerciseItemMapper.toEntity(exerciseItem);
            exerciseItemEntity.setTraining(entity);
            exerciseItems.add(exerciseItemRepository.save(exerciseItemEntity));
        }

        saved.setExerciseItems(exerciseItems);
        var trainingCreated = trainingRepository.save(saved);

        return trainingMapper.toResponse(trainingCreated);
    }

    @Transactional
    public TrainingResponseDTO update(TrainingRequestDTO training) {
        logger.info("Updating Training");

        var entity = trainingRepository.findById(training.id())
            .orElseThrow(() -> new NotFoundException("Not found this Exercise Id: " + training.id()));
        if (training.exerciseItems() == null || training.exerciseItems().isEmpty() ||
            training.muscleGroupsId() == null || training.muscleGroupsId().isEmpty()) {
            throw new IsEmptyObjectException("Adicione exercícios e agrupamentos musculares ao treinamento.");
        }
        var currentIds = entity.getExerciseItems().stream().map(ExerciseItem::getId).collect(java.util.stream.Collectors.toSet());
        var requestedIds = training.exerciseItems().stream().map(ExerciseItemRequestDTO::id)
            .filter(java.util.Objects::nonNull).collect(java.util.stream.Collectors.toSet());
        if (!currentIds.containsAll(requestedIds)) {
            throw new IsEmptyObjectException("Um dos exercícios selecionados não pertence a este treinamento.");
        }
        var removed = entity.getExerciseItems().stream().filter(item -> !requestedIds.contains(item.getId())).toList();
        exerciseItemRepository.deleteAll(removed);
        entity.setTitle(training.title());
        entity.setDescription(training.description());

        List<MuscleGroup> muscleGroups = muscleGroupRepository.findAllById(training.muscleGroupsId());
        entity.setMuscleGroups(new HashSet<>(muscleGroups));

        var saved = trainingRepository.save(entity);

        Set<ExerciseItem> exerciseItems = new HashSet<>();

        for (ExerciseItemRequestDTO exerciseItem : training.exerciseItems()) {
            var exerciseItemEntity = exerciseItemMapper.toEntity(exerciseItem);
            exerciseItemEntity.setTraining(entity);
            exerciseItems.add(exerciseItemRepository.save(exerciseItemEntity));
        }

        saved.setExerciseItems(exerciseItems);
        var trainingUpdated = trainingRepository.save(saved);

        return trainingMapper.toResponse(trainingUpdated);
    }

    @Transactional
    public void delete(UUID id) {
        logger.info("Deleting Training");

        var entity = trainingRepository.findById(id)
            .orElseThrow(() -> new NotFoundException("Not found this Exercise Id: " + id));
        trainingRepository.delete(entity);
    }
}
