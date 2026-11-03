package br.com.joaojuniodev.corefitpro.mapper.trainee;

import br.com.joaojuniodev.corefitpro.exceptions.NotFoundException;
import br.com.joaojuniodev.corefitpro.infrastructure.backblaze.B2StorageGateway;
import br.com.joaojuniodev.corefitpro.mapper.ObjectMapper;
import br.com.joaojuniodev.corefitpro.mapper.physicalAssessment.PhysicalAssessmentMapper;
import br.com.joaojuniodev.corefitpro.mapper.trainingItem.TrainingItemMapper;
import br.com.joaojuniodev.corefitpro.personalTrainer.repository.PersonalTrainerRepository;
import br.com.joaojuniodev.corefitpro.physicalAssessment.model.PhysicalAssessment;
import br.com.joaojuniodev.corefitpro.security.repository.UserRepository;
import br.com.joaojuniodev.corefitpro.trainee.dto.request.TraineeRequestDTO;
import br.com.joaojuniodev.corefitpro.trainee.dto.response.TraineeDetailsDTO;
import br.com.joaojuniodev.corefitpro.trainee.dto.response.TraineeResponseDTO;
import br.com.joaojuniodev.corefitpro.trainee.dto.response.TraineeSummaryDTO;
import br.com.joaojuniodev.corefitpro.trainee.dto.response.TraineeWeeklyTraining;
import br.com.joaojuniodev.corefitpro.trainee.model.Trainee;
import br.com.joaojuniodev.corefitpro.trainingItem.enums.DaysOfWeek;
import br.com.joaojuniodev.corefitpro.trainingItem.enums.TrainingStatus;
import br.com.joaojuniodev.corefitpro.trainingItem.model.TrainingItem;
import br.com.joaojuniodev.corefitpro.trainingItem.repository.TrainingItemRepository;
import br.com.joaojuniodev.corefitpro.trainingPlain.repository.TrainingPlainRepository;
import org.springframework.stereotype.Component;

import br.com.joaojuniodev.corefitpro.trainingItem.util.WorkoutProgress;
import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class TraineeMapper implements ObjectMapper<Trainee, TraineeResponseDTO, TraineeRequestDTO> {

    private final PersonalTrainerRepository personalTrainerRepository;
    private final UserRepository userRepository;
    private final TrainingItemRepository trainingItemRepository;
    private final TrainingPlainRepository trainingPlainRepository;
    private final PhysicalAssessmentMapper physicalAssessmentMapper;
    private final B2StorageGateway b2Storage;

    public TraineeMapper(PersonalTrainerRepository personalTrainerRepository, UserRepository userRepository, TrainingItemRepository trainingItemRepository, TrainingPlainRepository trainingPlainRepository, PhysicalAssessmentMapper physicalAssessmentMapper, B2StorageGateway b2Storage) {
        this.personalTrainerRepository = personalTrainerRepository;
        this.userRepository = userRepository;
        this.trainingItemRepository = trainingItemRepository;
        this.trainingPlainRepository = trainingPlainRepository;
        this.physicalAssessmentMapper = physicalAssessmentMapper;
        this.b2Storage = b2Storage;
    }

    @Override
    public Trainee toEntity(TraineeRequestDTO request) {
        var user = userRepository.findByUserName(request.credentials().getUsername());
        var personalTrainer = personalTrainerRepository.findById(request.personalTrainerId())
            .orElseThrow(() -> new NotFoundException("Not found Personal Trainer Id: " + request.personalTrainerId()));

        Trainee trainee = new Trainee();
        trainee.setId(request.id());
        trainee.setFirstName(request.firstName());
        trainee.setLastName(request.lastName());
        trainee.setUser(user);
        trainee.setPersonalTrainer(personalTrainer);
        return trainee;
    }

    @Override
    public TraineeResponseDTO toResponse(Trainee entity) {
        var trainingPlainForTrainee = trainingPlainRepository
            .findByTrainee(entity.getId())
            .orElse(null);

        var lastPhysicalAssessment = entity.getPhysicalAssessments()
            .stream()
            .max(Comparator.comparing(PhysicalAssessment::getRegisteredAt))
            .orElse(null);

        var trainingsOfWeekly = trainingItemRepository.findByTrainee(entity.getId());

        long totalTrainings = trainingsOfWeekly.size();

        long completedTrainings = trainingsOfWeekly.stream()
            .filter(WorkoutProgress::isCompletedThisWeek)
            .count();

        double progress = WorkoutProgress.percentage(completedTrainings, totalTrainings);

        return new TraineeResponseDTO(
            entity.getId(),
            entity.getFirstName(),
            entity.getLastName(),
            entity.getAvatarKey() != null
                ? b2Storage.getTemporaryUrl(entity.getAvatarKey(), Duration.ofHours(1))
                : null,
            lastPhysicalAssessment != null ? lastPhysicalAssessment.getAge() : null,
            lastPhysicalAssessment != null ? lastPhysicalAssessment.getWeight() : null,
            trainingPlainForTrainee != null ? trainingPlainForTrainee.getObjective() : null,
            trainingPlainForTrainee != null ? trainingPlainForTrainee.getActive() : null,
            progress
        );
    }

    public TraineeDetailsDTO toDetails(Trainee entity) {
        var trainingsOfWeekly = trainingItemRepository.findByTrainee(entity.getId());

        var totalTrainings = trainingsOfWeekly.stream().count();
        var completedTrainings = trainingsOfWeekly.stream()
            .filter(WorkoutProgress::isCompletedThisWeek)
            .count();

        return new TraineeDetailsDTO(
            toResponse(entity),
            entity.getPhysicalAssessments()
                .stream()
                .map(physicalAssessmentMapper::toResponse).toList(),
            buildWeeklySchedule(trainingsOfWeekly),
            totalTrainings,
            completedTrainings
        );
    }

    private List<TraineeWeeklyTraining> buildWeeklySchedule(List<TrainingItem> trainingItems) {
        Map<DaysOfWeek, TrainingItem> itemsByDay = trainingItems.stream()
            .collect(Collectors.toMap(TrainingItem::getDayOfWeek, Function.identity(), (first, duplicate) -> first));

        LocalDate monday = LocalDate.now(java.time.ZoneId.of("America/Sao_Paulo")).with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));
        LocalDate today = LocalDate.now(WorkoutProgress.ZONE);

        List<TraineeWeeklyTraining> weeklySchedule = new ArrayList<>();

        for (DaysOfWeek day : DaysOfWeek.values()) {
            TrainingItem item = itemsByDay.get(day);

            if (item == null) {
                LocalDateTime restDateTime = monday.plusDays(day.ordinal()).atStartOfDay();
                boolean isToday = restDateTime.toLocalDate().equals(today);
                weeklySchedule.add(new TraineeWeeklyTraining(null, day, isToday, null, null, restDateTime, TrainingStatus.REST));
                continue;
            }

            TrainingStatus status = WorkoutProgress.isCompletedThisWeek(item)
                ? TrainingStatus.COMPLETED
                : TrainingStatus.PENDING;

            String title = item.getTraining() != null ? item.getTraining().getTitle() : null;
            String focus = item.getTraining() != null ? item.getTraining().getDescription() : null;
            LocalDateTime scheduledDate = monday.plusDays(day.ordinal()).atStartOfDay();
            boolean isToday = scheduledDate.toLocalDate().equals(today);

            weeklySchedule.add(new TraineeWeeklyTraining(item.getId(), day, isToday, title, focus, scheduledDate, status));
        }

        return weeklySchedule;
    }

    public TraineeSummaryDTO toSummary(Trainee entity) {
        return new TraineeSummaryDTO(
            entity.getId(),
            entity.getFirstName(),
            entity.getLastName()
        );
    }
}
