package br.com.joaojuniodev.corefitpro.trainingItem.service;

import br.com.joaojuniodev.corefitpro.exceptions.NotFoundException;
import br.com.joaojuniodev.corefitpro.mapper.trainingItem.TrainingItemMapper;
import br.com.joaojuniodev.corefitpro.training.model.Training;
import br.com.joaojuniodev.corefitpro.training.repository.TrainingRepository;
import br.com.joaojuniodev.corefitpro.trainingItem.dto.request.TrainingItemRequestDTO;
import br.com.joaojuniodev.corefitpro.trainingItem.dto.response.TrainingItemResponseDTO;
import br.com.joaojuniodev.corefitpro.trainingItem.model.TrainingItem;
import br.com.joaojuniodev.corefitpro.trainingItem.repository.TrainingItemRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public class TrainingItemService {
    private static final Logger logger = LoggerFactory.getLogger(TrainingItemService.class.getName());

    private final TrainingItemRepository trainingItemRepository;
    private final TrainingRepository trainingRepository;
    private final TrainingItemMapper trainingItemMapper;

    public TrainingItemService(TrainingItemRepository trainingItemRepository, TrainingRepository trainingRepository, TrainingItemMapper trainingItemMapper) {
        this.trainingItemRepository = trainingItemRepository;
        this.trainingRepository = trainingRepository;
        this.trainingItemMapper = trainingItemMapper;
    }

    public List<TrainingItemResponseDTO> getAll() {
        logger.info("Getting All Training Items");

        return trainingItemRepository.findAll()
            .stream()
            .map(trainingItemMapper::toResponse)
            .toList();
    }

    public TrainingItemResponseDTO getById(UUID id) {
        logger.info("Getting By Training Item Id");

        var entity = trainingItemRepository.findById(id)
            .orElseThrow(() -> new NotFoundException("Not found this Training Id: " + id));
        return trainingItemMapper.toResponse(entity);
    }

    @Transactional
    public TrainingItemResponseDTO create(TrainingItemRequestDTO trainingItem) {
        logger.info("Creating new Training Item");

        var entity = trainingItemMapper.toEntity(trainingItem);
        var trainingItemCreated = trainingItemRepository.save(entity);

        return trainingItemMapper.toResponse(trainingItemCreated);
    }

    @Transactional
    public TrainingItemResponseDTO update(TrainingItemRequestDTO trainingItem) {
        logger.info("Updating Training Item");

        Training training = trainingItem != null
            ? trainingRepository.findById(trainingItem.trainingId())
            .orElseThrow(() -> new NotFoundException("Not found Training Id: " + trainingItem.trainingId()))
            : null;

        var entity = trainingItemRepository.findById(trainingItem.id())
            .orElseThrow(() -> new NotFoundException("Not found this Training Id: " + trainingItem.id()));
        entity.setDayOfWeek(trainingItem.dayOfWeek());
        entity.setTraining(training);

        return trainingItemMapper.toResponse(entity);
    }

    @Transactional
    public TrainingItemResponseDTO markCompletedTraining(UUID id) {
        logger.info("Marking Training Item this Completed");

        var entity = trainingItemRepository.findById(id)
            .orElseThrow(() -> new NotFoundException("Not found this Training Item Id: " + id));
        entity.setCompleted(true);
        entity.setDateTime(java.time.LocalDateTime.now(java.time.ZoneId.of("America/Sao_Paulo")));
        return trainingItemMapper.toResponse(trainingItemRepository.save(entity));
    }

    public void delete(UUID id) {
        logger.info("Deleting Training Item");

        var entity = trainingItemRepository.findById(id)
            .orElseThrow(() -> new NotFoundException("Not found this Training Item Id: " + id));
        trainingItemRepository.delete(entity);
    }

    @Transactional
    public void resetCompletedWorkouts() {
        List<TrainingItem> trainingItems = trainingItemRepository.findAll();
        var monday = java.time.LocalDate.now(java.time.ZoneId.of("America/Sao_Paulo"))
            .with(java.time.temporal.TemporalAdjusters.previousOrSame(java.time.DayOfWeek.MONDAY));
        trainingItems.stream().filter(item -> Boolean.TRUE.equals(item.getCompleted()) &&
            (item.getDateTime() == null || item.getDateTime().toLocalDate().isBefore(monday))).forEach(this::reset);

        logger.info("All workouts marked as incomplete");
    }

    private void reset(TrainingItem trainingItem) {
        trainingItem.setCompleted(false);
        trainingItemRepository.save(trainingItem);

        logger.info("Workout marked as not completed");
    }
}
