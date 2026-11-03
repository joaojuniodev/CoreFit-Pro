package br.com.joaojuniodev.corefitpro.trainee.service;

import br.com.joaojuniodev.corefitpro.exceptions.NotFoundException;
import br.com.joaojuniodev.corefitpro.exceptions.ObjectIsNullException;
import br.com.joaojuniodev.corefitpro.infrastructure.backblaze.B2StorageGateway;
import br.com.joaojuniodev.corefitpro.mapper.trainee.TraineeMapper;
import br.com.joaojuniodev.corefitpro.security.service.UserService;
import br.com.joaojuniodev.corefitpro.trainee.dto.request.TraineeRequestDTO;
import br.com.joaojuniodev.corefitpro.trainee.dto.response.TraineeDetailsDTO;
import br.com.joaojuniodev.corefitpro.trainee.dto.response.TraineeResponseDTO;
import br.com.joaojuniodev.corefitpro.trainee.dto.response.TraineeSummaryDTO;
import br.com.joaojuniodev.corefitpro.trainee.repository.TraineeRepository;
import br.com.joaojuniodev.corefitpro.trainee.repository.spec.TraineeSpecification;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

@Service
@Transactional(readOnly = true)
public class TraineeService {
    private static final Logger logger = LoggerFactory.getLogger(TraineeService.class.getName());

    private final TraineeRepository traineeRepository;
    private final TraineeMapper traineeMapper;
    private final UserService userService;
    private final B2StorageGateway b2Storage;

    public TraineeService(TraineeRepository traineeRepository, TraineeMapper traineeMapper, UserService userService, B2StorageGateway b2Storage) {
        this.traineeRepository = traineeRepository;
        this.traineeMapper = traineeMapper;
        this.userService = userService;
        this.b2Storage = b2Storage;
    }

    public List<TraineeResponseDTO> getAll(UUID personalTrainerId) {
        logger.info("Getting All Trainees");

        TraineeSpecification spec = new TraineeSpecification();
        spec.addToSpecifications(personalTrainerId);

        return traineeRepository
            .findAll(spec.apply())
            .stream()
            .map(traineeMapper::toResponse)
            .toList();
    }

    public TraineeResponseDTO getById(UUID id) {
        logger.info("Getting By Trainee Id");

        var entity = traineeRepository.findById(id)
            .orElseThrow(() -> new NotFoundException("Not found this Trainee Id: " + id));
        return traineeMapper.toResponse(entity);
    }

    public TraineeDetailsDTO getDetails(UUID id) {
        logger.info("Getting Trainee Details by Id");

        var entity = traineeRepository.findById(id)
            .orElseThrow(() -> new NotFoundException("Not found this Trainee Id: " + id));
        return traineeMapper.toDetails(entity);
    }

    @Transactional
    public TraineeSummaryDTO create(TraineeRequestDTO trainee, MultipartFile avatar) {
        logger.info("Creating new Exercise");

        var userCreated = userService.registerByTrainee(trainee.credentials());
        if (userCreated == null) {
            throw new ObjectIsNullException("Fail is creating a new User");
        }

        var entity = traineeMapper.toEntity(trainee);

        if (avatar != null && !avatar.isEmpty()) {
            var storedResponse = b2Storage.upload(avatar, "trainees/profiles");
            entity.setAvatarFileId(storedResponse.fileId());
            entity.setAvatarKey(storedResponse.fileKey());
        }

        var traineeCreated = traineeRepository.save(entity);
        return traineeMapper.toSummary(traineeCreated);
    }

    @Transactional
    public TraineeResponseDTO update(TraineeRequestDTO trainee) {
        logger.info("Updating Exercise");

        var entity = traineeRepository.findById(trainee.id())
            .orElseThrow(() -> new NotFoundException("Not found this Trainee Id: " + trainee.id()));
        entity.setFirstName(trainee.firstName());
        entity.setLastName(trainee.lastName());
        var traineeUpdated = traineeRepository.save(entity);
        return traineeMapper.toResponse(traineeUpdated);
    }

    @Transactional
    public void delete(UUID id) {
        logger.info("Deleting Trainee");

        var entity = traineeRepository.findById(id)
            .orElseThrow(() -> new NotFoundException("Not found this Trainee Id: " + id));
        traineeRepository.delete(entity);
    }
}
