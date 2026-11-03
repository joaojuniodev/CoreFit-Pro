package br.com.joaojuniodev.corefitpro.physicalAssessment.service;

import br.com.joaojuniodev.corefitpro.exceptions.NotFoundException;
import br.com.joaojuniodev.corefitpro.infrastructure.backblaze.B2StorageGateway;
import br.com.joaojuniodev.corefitpro.mapper.physicalAssessment.PhysicalAssessmentMapper;
import br.com.joaojuniodev.corefitpro.physicalAssessment.dto.request.PhysicalAssessmentRequestDTO;
import br.com.joaojuniodev.corefitpro.physicalAssessment.dto.response.PhysicalAssessmentResponseDTO;
import br.com.joaojuniodev.corefitpro.physicalAssessment.repository.PhysicalAssessmentRepository;
import br.com.joaojuniodev.corefitpro.physicalAssessment.repository.spec.PhysicalAssessmentSpecification;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

@Service
public class PhysicalAssessmentService {
    private static final Logger logger = LoggerFactory.getLogger(PhysicalAssessmentService.class.getName());

    private final PhysicalAssessmentRepository physicalAssessmentRepository;
    private final PhysicalAssessmentMapper physicalAssessmentMapper;
    private final B2StorageGateway b2Storage;

    public PhysicalAssessmentService(PhysicalAssessmentRepository physicalAssessmentRepository, PhysicalAssessmentMapper physicalAssessmentMapper, B2StorageGateway b2Storage) {
        this.physicalAssessmentRepository = physicalAssessmentRepository;
        this.physicalAssessmentMapper = physicalAssessmentMapper;
        this.b2Storage = b2Storage;
    }

    public List<PhysicalAssessmentResponseDTO> getAll(UUID traineeId) {
        logger.info("Getting All Physical Assessments");

        PhysicalAssessmentSpecification spec = new PhysicalAssessmentSpecification();
        spec.addToSpecifications(traineeId);

        return physicalAssessmentRepository
            .findAll(spec.apply())
            .stream()
            .map(physicalAssessmentMapper::toResponse)
            .toList();
    }

    public PhysicalAssessmentResponseDTO getById(UUID id) {
        logger.info("Getting By Physical Assessment Id");

        var entity = physicalAssessmentRepository.findById(id)
            .orElseThrow(() -> new NotFoundException("Not found this Physical Assessment Id: " + id));
        return physicalAssessmentMapper.toResponse(entity);
    }

    public PhysicalAssessmentResponseDTO create(
        PhysicalAssessmentRequestDTO physicalAssessment,
        MultipartFile frontPhoto, MultipartFile backPhoto,
        MultipartFile leftPhoto, MultipartFile rightPhoto
    ) {
        logger.info("Creating new Physical Assessment");

        var entity = physicalAssessmentMapper.toEntity(physicalAssessment);

        if (frontPhoto != null) {
            var storedResponseFrontPhoto = b2Storage.upload(frontPhoto, "trainees/physical-assessments/front");
            entity.setFrontViewPhotoFileId(storedResponseFrontPhoto.fileId());
            entity.setFrontViewPhotoKey(storedResponseFrontPhoto.fileKey());
        }

        if (backPhoto != null) {
            var storedResponseBackPhoto = b2Storage.upload(backPhoto, "trainees/physical-assessments/back");
            entity.setBackViewPhotoFileId(storedResponseBackPhoto.fileId());
            entity.setBackViewPhotoKey(storedResponseBackPhoto.fileKey());
        }

        if (leftPhoto != null) {
            var storedResponseLeftPhoto = b2Storage.upload(leftPhoto, "trainees/physical-assessments/left");
            entity.setLeftViewPhotoFileId(storedResponseLeftPhoto.fileId());
            entity.setLeftViewPhotoKey(storedResponseLeftPhoto.fileKey());
        }

        if (rightPhoto != null) {
            var storedResponseRightPhoto = b2Storage.upload(rightPhoto, "trainees/physical-assessments/right");
            entity.setRightViewPhotoFileId(storedResponseRightPhoto.fileId());
            entity.setRightViewPhotoKey(storedResponseRightPhoto.fileKey());
        }

        var physicalAssessmentCreated = physicalAssessmentRepository.save(entity);
        return physicalAssessmentMapper.toResponse(physicalAssessmentCreated);
    }

    public PhysicalAssessmentResponseDTO update(PhysicalAssessmentRequestDTO physicalAssessment) {
        logger.info("Updating Physical Assessment");

        var entity = physicalAssessmentRepository.findById(physicalAssessment.id())
            .orElseThrow(() -> new NotFoundException("Not found this Physical Assessment Id: " + physicalAssessment.id()));

        entity.setRegisteredAt(physicalAssessment.registeredAt());
        entity.setAge(physicalAssessment.age());
        entity.setWeight(physicalAssessment.weight());
        entity.setHeight(physicalAssessment.height());
        entity.setImc(physicalAssessment.imc());
        entity.setBodyFat(physicalAssessment.bodyFat());
        entity.setFatMass(physicalAssessment.fatMass());
        entity.setMuscleMass(physicalAssessment.muscleMass());
        entity.setChest(physicalAssessment.chest());
        entity.setWaist(physicalAssessment.waist());
        entity.setAbdomen(physicalAssessment.abdomen());
        entity.setHip(physicalAssessment.hip());
        entity.setRightArm(physicalAssessment.rightArm());
        entity.setLeftArm(physicalAssessment.leftArm());
        entity.setRightThigh(physicalAssessment.rightThigh());
        entity.setLeftThigh(physicalAssessment.leftThigh());
        entity.setActivityLevel(physicalAssessment.activityLevel());
        entity.setNotes(physicalAssessment.notes());
        var physicalAssessmentUpdated = physicalAssessmentRepository.save(entity);

        return physicalAssessmentMapper.toResponse(physicalAssessmentUpdated);
    }

    public PhysicalAssessmentResponseDTO updatePhotos(
        UUID id,
        MultipartFile frontPhoto, MultipartFile backPhoto,
        MultipartFile leftPhoto, MultipartFile rightPhoto
    ) {
        var entity = physicalAssessmentRepository.findById(id)
            .orElseThrow(() -> new NotFoundException("Not found this Physical Assessment Id: " + id));

        if (frontPhoto != null && !frontPhoto.isEmpty()) {
            var stored = b2Storage.upload(frontPhoto, "trainees/physical-assessments/front");
            entity.setFrontViewPhotoFileId(stored.fileId());
            entity.setFrontViewPhotoKey(stored.fileKey());
        }
        if (backPhoto != null && !backPhoto.isEmpty()) {
            var stored = b2Storage.upload(backPhoto, "trainees/physical-assessments/back");
            entity.setBackViewPhotoFileId(stored.fileId());
            entity.setBackViewPhotoKey(stored.fileKey());
        }
        if (leftPhoto != null && !leftPhoto.isEmpty()) {
            var stored = b2Storage.upload(leftPhoto, "trainees/physical-assessments/left");
            entity.setLeftViewPhotoFileId(stored.fileId());
            entity.setLeftViewPhotoKey(stored.fileKey());
        }
        if (rightPhoto != null && !rightPhoto.isEmpty()) {
            var stored = b2Storage.upload(rightPhoto, "trainees/physical-assessments/right");
            entity.setRightViewPhotoFileId(stored.fileId());
            entity.setRightViewPhotoKey(stored.fileKey());
        }

        return physicalAssessmentMapper.toResponse(physicalAssessmentRepository.save(entity));
    }

    public void delete(UUID id) {
        logger.info("Deleting Physical Assessment");

        var entity = physicalAssessmentRepository.findById(id)
            .orElseThrow(() -> new NotFoundException("Not found this Physical Assessment Id: " + id));
        physicalAssessmentRepository.delete(entity);
    }
}
