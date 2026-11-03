package br.com.joaojuniodev.corefitpro.mapper.trainingItem;

import br.com.joaojuniodev.corefitpro.exceptions.NotFoundException;
import br.com.joaojuniodev.corefitpro.mapper.ObjectMapper;
import br.com.joaojuniodev.corefitpro.mapper.trainee.TraineeMapper;
import br.com.joaojuniodev.corefitpro.mapper.training.TrainingMapper;
import br.com.joaojuniodev.corefitpro.trainee.dto.response.TraineeSummaryDTO;
import br.com.joaojuniodev.corefitpro.training.repository.TrainingRepository;
import br.com.joaojuniodev.corefitpro.trainingItem.dto.request.TrainingItemRequestDTO;
import br.com.joaojuniodev.corefitpro.trainingItem.dto.response.TrainingItemResponseDTO;
import br.com.joaojuniodev.corefitpro.trainingItem.dto.response.TrainingItemSummaryDTO;
import br.com.joaojuniodev.corefitpro.trainingItem.model.TrainingItem;
import br.com.joaojuniodev.corefitpro.trainingPlain.repository.TrainingPlainRepository;
import org.springframework.stereotype.Component;
import br.com.joaojuniodev.corefitpro.trainingItem.util.WorkoutProgress;

@Component
public class TrainingItemMapper implements ObjectMapper<TrainingItem, TrainingItemResponseDTO, TrainingItemRequestDTO> {

    private final TrainingRepository trainingRepository;
    private final TrainingPlainRepository trainingPlainRepository;
    private final TrainingMapper trainingMapper;

    public TrainingItemMapper(TrainingRepository trainingRepository, TrainingPlainRepository trainingPlainRepository, TrainingMapper trainingMapper) {
        this.trainingRepository = trainingRepository;
        this.trainingPlainRepository = trainingPlainRepository;
        this.trainingMapper = trainingMapper;
    }

    @Override
    public TrainingItem toEntity(TrainingItemRequestDTO request) {
        var training = trainingRepository.findById(request.trainingId())
            .orElseThrow(() -> new NotFoundException("Not found Training Id: " + request.trainingId()));
        var trainingPlain = trainingPlainRepository.findById(request.trainingPlainId())
            .orElseThrow(() -> new NotFoundException("Not found Training Plain Id: " + request.trainingPlainId()));

        TrainingItem trainingItem = new TrainingItem();
        trainingItem.setId(request.id());
        trainingItem.setDayOfWeek(request.dayOfWeek());
        trainingItem.setCompleted(false);
        trainingItem.setTraining(training);
        trainingItem.setTrainingPlain(trainingPlain);
        return trainingItem;
    }

    @Override
    public TrainingItemResponseDTO toResponse(TrainingItem entity) {
        return new TrainingItemResponseDTO(
            entity.getId(),
            entity.getDayOfWeek(),
            WorkoutProgress.isCompletedThisWeek(entity),
            trainingMapper.toResponse(entity.getTraining())
        );
    }

    public TrainingItemSummaryDTO toSummary(TrainingItem entity) {

        var trainee = entity.getTrainingPlain().getTrainee();

        var traineeSummary = new TraineeSummaryDTO(
            trainee.getId(),
            trainee.getFirstName(),
            trainee.getLastName()
        );

        return new TrainingItemSummaryDTO(
            entity.getId(),
            entity.getDayOfWeek(),
            WorkoutProgress.isCompletedThisWeek(entity),
            trainingMapper.toSummary(entity.getTraining()),
            traineeSummary
        );
    }
}
