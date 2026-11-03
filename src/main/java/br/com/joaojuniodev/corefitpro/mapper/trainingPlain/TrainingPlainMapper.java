package br.com.joaojuniodev.corefitpro.mapper.trainingPlain;

import br.com.joaojuniodev.corefitpro.exceptions.NotFoundException;
import br.com.joaojuniodev.corefitpro.mapper.ObjectMapper;
import br.com.joaojuniodev.corefitpro.mapper.personalTrainer.PersonalTrainerMapper;
import br.com.joaojuniodev.corefitpro.mapper.trainee.TraineeMapper;
import br.com.joaojuniodev.corefitpro.mapper.trainingItem.TrainingItemMapper;
import br.com.joaojuniodev.corefitpro.personalTrainer.repository.PersonalTrainerRepository;
import br.com.joaojuniodev.corefitpro.trainee.repository.TraineeRepository;
import br.com.joaojuniodev.corefitpro.trainingPlain.dto.request.TrainingPlainRequestDTO;
import br.com.joaojuniodev.corefitpro.trainingPlain.dto.response.TrainingPlainDetailsDTO;
import br.com.joaojuniodev.corefitpro.trainingPlain.dto.response.TrainingPlainResponseDTO;
import br.com.joaojuniodev.corefitpro.trainingPlain.model.TrainingPlain;
import br.com.joaojuniodev.corefitpro.trainingItem.model.TrainingItem;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class TrainingPlainMapper implements ObjectMapper<TrainingPlain, TrainingPlainResponseDTO, TrainingPlainRequestDTO> {

    private final PersonalTrainerRepository personalTrainerRepository;
    private final TraineeRepository traineeRepository;
    private final PersonalTrainerMapper personalTrainerMapper;
    private final TraineeMapper traineeMapper;
    private final TrainingItemMapper trainingItemMapper;

    public TrainingPlainMapper(PersonalTrainerRepository personalTrainerRepository, TraineeRepository traineeRepository, PersonalTrainerMapper personalTrainerMapper, TraineeMapper traineeMapper, TrainingItemMapper trainingItemMapper) {
        this.personalTrainerRepository = personalTrainerRepository;
        this.traineeRepository = traineeRepository;
        this.personalTrainerMapper = personalTrainerMapper;
        this.traineeMapper = traineeMapper;
        this.trainingItemMapper = trainingItemMapper;
    }

    @Override
    public TrainingPlain toEntity(TrainingPlainRequestDTO request) {
        var personalTrainer = personalTrainerRepository.findById(request.personalTrainerId())
            .orElseThrow(() -> new NotFoundException("Not found Personal Trainer Id: " + request.personalTrainerId()));
        var trainee = traineeRepository.findById(request.traineeId())
            .orElseThrow(() -> new NotFoundException("Not found Trainee Id: " + request.traineeId()));

        TrainingPlain trainingPlain = new TrainingPlain();
        trainingPlain.setId(request.id());
        trainingPlain.setDescription(request.description());
        trainingPlain.setActive(true);
        trainingPlain.setObjective(request.objective());
        trainingPlain.setGoal(request.goal());
        trainingPlain.setPersonalTrainer(personalTrainer);
        trainingPlain.setTrainee(trainee);
        return trainingPlain;
    }

    @Override
    public TrainingPlainResponseDTO toResponse(TrainingPlain entity) {
        return new TrainingPlainResponseDTO(
            entity.getId(),
            personalTrainerMapper.toSummary(entity.getPersonalTrainer()),
            traineeMapper.toResponse(entity.getTrainee()),
            entity.getDescription(),
            entity.getActive(),
            entity.getObjective(),
            entity.getGoal()
        );
    }

    public TrainingPlainDetailsDTO toDetails(TrainingPlain entity) {
        return toDetails(entity, entity.getTrainings());
    }

    public TrainingPlainDetailsDTO toDetails(TrainingPlain entity, List<TrainingItem> trainingItems) {
        return new TrainingPlainDetailsDTO(
            entity.getId(),
            personalTrainerMapper.toSummary(entity.getPersonalTrainer()),
            traineeMapper.toResponse(entity.getTrainee()),
            entity.getDescription(),
            entity.getObjective(),
            entity.getGoal(),
            trainingItems != null
                ? trainingItems.stream().map(trainingItemMapper::toResponse).toList()
                : List.of()
        );
    }
}
