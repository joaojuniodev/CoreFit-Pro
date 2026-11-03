package br.com.joaojuniodev.corefitpro.mapper.physicalAssessment;

import br.com.joaojuniodev.corefitpro.exceptions.NotFoundException;
import br.com.joaojuniodev.corefitpro.infrastructure.backblaze.B2StorageGateway;
import br.com.joaojuniodev.corefitpro.mapper.ObjectMapper;
import br.com.joaojuniodev.corefitpro.physicalAssessment.dto.request.PhysicalAssessmentRequestDTO;
import br.com.joaojuniodev.corefitpro.physicalAssessment.dto.response.PhysicalAssessmentResponseDTO;
import br.com.joaojuniodev.corefitpro.physicalAssessment.model.PhysicalAssessment;
import br.com.joaojuniodev.corefitpro.trainee.repository.TraineeRepository;
import org.springframework.stereotype.Component;

import java.time.Duration;

@Component
public class PhysicalAssessmentMapper implements ObjectMapper<PhysicalAssessment, PhysicalAssessmentResponseDTO, PhysicalAssessmentRequestDTO> {

    private TraineeRepository traineeRepository;
    private final B2StorageGateway b2Storage;

    public PhysicalAssessmentMapper(TraineeRepository traineeRepository, B2StorageGateway b2Storage) {
        this.traineeRepository = traineeRepository;
        this.b2Storage = b2Storage;
    }

    @Override
    public PhysicalAssessment toEntity(PhysicalAssessmentRequestDTO request) {
        var trainee = traineeRepository.findById(request.traineeId())
            .orElseThrow(() -> new NotFoundException("Not found Physical Assessment Id: " + request.traineeId()));

        PhysicalAssessment physicalAssessment = new PhysicalAssessment();
        physicalAssessment.setId(request.id());
        physicalAssessment.setTrainee(trainee);
        physicalAssessment.setRegisteredAt(request.registeredAt());
        physicalAssessment.setAge(request.age());
        physicalAssessment.setWeight(request.weight());
        physicalAssessment.setHeight(request.height());
        physicalAssessment.setImc(request.imc());
        physicalAssessment.setBodyFat(request.bodyFat());
        physicalAssessment.setFatMass(request.fatMass());
        physicalAssessment.setMuscleMass(request.muscleMass());
        physicalAssessment.setChest(request.chest());
        physicalAssessment.setWaist(request.waist());
        physicalAssessment.setAbdomen(request.abdomen());
        physicalAssessment.setHip(request.hip());
        physicalAssessment.setRightArm(request.rightArm());
        physicalAssessment.setLeftArm(request.leftArm());
        physicalAssessment.setRightThigh(request.rightThigh());
        physicalAssessment.setLeftThigh(request.leftThigh());
        physicalAssessment.setActivityLevel(request.activityLevel());
        physicalAssessment.setNotes(request.notes());
        return physicalAssessment;
    }

    @Override
    public PhysicalAssessmentResponseDTO toResponse(PhysicalAssessment entity) {
        return new PhysicalAssessmentResponseDTO(
            entity.getId(),
            entity.getRegisteredAt(),
            entity.getAge(),
            entity.getWeight(),
            entity.getHeight(),
            entity.getImc(),
            entity.getBodyFat(),
            entity.getFatMass(),
            entity.getMuscleMass(),
            entity.getChest(),
            entity.getWaist(),
            entity.getAbdomen(),
            entity.getHip(),
            entity.getRightArm(),
            entity.getLeftArm(),
            entity.getRightThigh(),
            entity.getLeftThigh(),
            entity.getActivityLevel(),
            entity.getNotes(),
            entity.getFrontViewPhotoKey() != null
                ? b2Storage.getTemporaryUrl(entity.getFrontViewPhotoKey(), Duration.ofHours(1))
                : null,
            entity.getBackViewPhotoKey() != null
                ? b2Storage.getTemporaryUrl(entity.getBackViewPhotoKey(), Duration.ofHours(1))
                : null,
            entity.getLeftViewPhotoKey() != null
                ? b2Storage.getTemporaryUrl(entity.getLeftViewPhotoKey(), Duration.ofHours(1))
                : null,
            entity.getRightViewPhotoKey() != null
                ? b2Storage.getTemporaryUrl(entity.getRightViewPhotoKey(), Duration.ofHours(1))
                : null
        );
    }
}