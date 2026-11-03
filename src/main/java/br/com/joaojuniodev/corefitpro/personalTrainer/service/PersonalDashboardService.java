package br.com.joaojuniodev.corefitpro.personalTrainer.service;

import br.com.joaojuniodev.corefitpro.mapper.trainingItem.TrainingItemMapper;
import br.com.joaojuniodev.corefitpro.personalTrainer.dto.response.AttentionResponseDTO;
import br.com.joaojuniodev.corefitpro.personalTrainer.dto.response.DashboardResponseDTO;
import br.com.joaojuniodev.corefitpro.personalTrainer.dto.response.WeeklyRhythmCompletedWorkoutsResponseDTO;
import br.com.joaojuniodev.corefitpro.personalTrainer.enums.SeverityAttentionEnum;
import br.com.joaojuniodev.corefitpro.recentAcitivty.service.RecentActivityService;
import br.com.joaojuniodev.corefitpro.trainee.repository.TraineeRepository;
import br.com.joaojuniodev.corefitpro.trainingItem.dto.projection.NotCompletedTrainingsProjection;
import br.com.joaojuniodev.corefitpro.trainingItem.dto.projection.WeeklyRhythmProjection;
import br.com.joaojuniodev.corefitpro.trainingItem.enums.DaysOfWeek;
import br.com.joaojuniodev.corefitpro.trainingItem.repository.TrainingItemRepository;
import br.com.joaojuniodev.corefitpro.trainingPlain.repository.TrainingPlainRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import br.com.joaojuniodev.corefitpro.trainingItem.util.WorkoutProgress;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Service
@Transactional(readOnly = true)
public class PersonalDashboardService {
    private static final Logger logger = LoggerFactory.getLogger(PersonalDashboardService.class.getName());

    private final RecentActivityService recentActivityService;
    private final TraineeRepository traineeRepository;
    private final TrainingPlainRepository trainingPlainRepository;
    private final TrainingItemRepository trainingItemRepository;
    private final TrainingItemMapper trainingItemMapper;

    public PersonalDashboardService(RecentActivityService recentActivityService, TraineeRepository traineeRepository, TrainingPlainRepository trainingPlainRepository, TrainingItemRepository trainingItemRepository, TrainingItemMapper trainingItemMapper) {
        this.recentActivityService = recentActivityService;
        this.traineeRepository = traineeRepository;
        this.trainingPlainRepository = trainingPlainRepository;
        this.trainingItemRepository = trainingItemRepository;
        this.trainingItemMapper = trainingItemMapper;
    }

    public DashboardResponseDTO getDashboard(UUID id) {
        var today = LocalDate.now(WorkoutProgress.ZONE).getDayOfWeek();
        var day = DaysOfWeek.valueOf(today.name());

        var activeTrainees = traineeRepository.countByUserEnabledTrueAndPersonalTrainerId(id);
        var activePlans = trainingPlainRepository.countByActiveTrueAndPersonalTrainerId(id);
        var todayTrainings = trainingItemRepository.findByDayOfWeekAndPersonalTrainerId(day, id)
            .stream()
            .map(trainingItemMapper::toSummary)
            .toList();
        long totalTrainingsOfToday = todayTrainings.size();
        long workoutsCompletedToday = todayTrainings.stream()
            .filter(item -> Boolean.TRUE.equals(item.completed())).count();
        long pendings = totalTrainingsOfToday - workoutsCompletedToday;
        double percentageWorkoutsCompletedToday = WorkoutProgress.percentage(workoutsCompletedToday, totalTrainingsOfToday);
        var necessaryAttention = getAttentions(id);
        var rhythmWeekly = getWeeklyRhythm(id);
        var recentActivities = recentActivityService.getAll(id);

        return new DashboardResponseDTO(
            activeTrainees,
            activePlans,
            pendings,
            workoutsCompletedToday,
            percentageWorkoutsCompletedToday,
            todayTrainings,
            necessaryAttention,
            rhythmWeekly,
            recentActivities
        );
    }

    private List<AttentionResponseDTO> getAttentions(UUID personalTrainerId) {
        List<NotCompletedTrainingsProjection> results = trainingItemRepository.findTrainingsIncomplete(personalTrainerId);

        return results.stream()
            .map(r -> {
                final String traineeName = r.getTraineeName();
                final String message = "Faltou aos últimos " + r.getCountIncompleteTraining() + " treinos.";
                final String url = "http://localhost:5173/personal/alunos";
                return new AttentionResponseDTO(
                    traineeName,
                    message,
                    url,
                    r.getCountIncompleteTraining() > 3
                        ? SeverityAttentionEnum.HIGH
                        : SeverityAttentionEnum.MEDIUM
                );
            })
            .limit(3)
            .toList();
    }

    private List<WeeklyRhythmCompletedWorkoutsResponseDTO> getWeeklyRhythm(UUID personalTrainerId) {
        List<WeeklyRhythmProjection> results = trainingItemRepository.findWeeklyRhythm(personalTrainerId,
            WorkoutProgress.weekStart().atStartOfDay(), WorkoutProgress.weekStart().plusWeeks(1).atStartOfDay());

        return Arrays.stream(DaysOfWeek.values())
            .map(day -> {
                WeeklyRhythmProjection result = results.stream()
                    .filter(r -> r.getDayOfWeek() == day)
                    .findFirst()
                    .orElse(null);

                if (result == null) {
                    return new WeeklyRhythmCompletedWorkoutsResponseDTO(day, 0L, 0L);
                }

                return new WeeklyRhythmCompletedWorkoutsResponseDTO(
                    day,
                    result.getTotalTrainings(),
                    result.getCompletedTrainings()
                );
            })
            .toList();
    }
}
