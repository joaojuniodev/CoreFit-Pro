package br.com.joaojuniodev.corefitpro.mapper.exercise;

import br.com.joaojuniodev.corefitpro.exceptions.NotFoundException;
import br.com.joaojuniodev.corefitpro.exercise.dto.request.ExerciseRequestDTO;
import br.com.joaojuniodev.corefitpro.exercise.dto.response.ExerciseResponseDTO;
import br.com.joaojuniodev.corefitpro.exercise.model.Exercise;
import br.com.joaojuniodev.corefitpro.infrastructure.backblaze.B2StorageGateway;
import br.com.joaojuniodev.corefitpro.mapper.ObjectMapper;
import br.com.joaojuniodev.corefitpro.mapper.muscleGroup.MuscleGroupMapper;
import br.com.joaojuniodev.corefitpro.personalTrainer.repository.PersonalTrainerRepository;
import org.springframework.stereotype.Component;

import java.time.Duration;

@Component
public class ExerciseMapper implements ObjectMapper<Exercise, ExerciseResponseDTO, ExerciseRequestDTO> {

    private final MuscleGroupMapper muscleGroupMapper;
    private final PersonalTrainerRepository personalTrainerRepository;
    private final B2StorageGateway b2Storage;

    public ExerciseMapper(MuscleGroupMapper muscleGroupMapper, PersonalTrainerRepository personalTrainerRepository, B2StorageGateway b2Storage) {
        this.muscleGroupMapper = muscleGroupMapper;
        this.personalTrainerRepository = personalTrainerRepository;
        this.b2Storage = b2Storage;
    }

    @Override
    public Exercise toEntity(ExerciseRequestDTO request) {
        Exercise exercise = new Exercise();
        exercise.setId(request.id());
        exercise.setName(request.name());
        exercise.setFavorite(request.favorite());

        if (request.personalTrainerId() != null) {
            var personalTrainer = personalTrainerRepository.findById(request.personalTrainerId())
                .orElseThrow(() -> new NotFoundException("Not found Personal Trainer Id: " + request.personalTrainerId()));
            exercise.setPersonalTrainer(personalTrainer);
        }

        return exercise;
    }

    private String mediaUrl(String key) {
        if (key == null || key.isBlank()) return null;
        if (key.startsWith("https://") || key.startsWith("http://")) return key;
        return b2Storage.getTemporaryUrl(key, Duration.ofHours(1));
    }

    @Override
    public ExerciseResponseDTO toResponse(Exercise entity) {
        return new ExerciseResponseDTO(
            entity.getId(),
            entity.getName(),
            entity.getSystem(),
            entity.getFavorite(),
            mediaUrl(entity.getPhotoKey()),
            mediaUrl(entity.getVideoKey()),
            entity.getPhotoFileId(),
            entity.getVideoFileId(),
            entity.getMuscleGroups().stream().map(muscleGroupMapper::toResponse).toList()
        );
    }
}
