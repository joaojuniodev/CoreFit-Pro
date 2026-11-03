package br.com.joaojuniodev.corefitpro.trainingItem.repository;

import br.com.joaojuniodev.corefitpro.trainingItem.dto.projection.NotCompletedTrainingsProjection;
import br.com.joaojuniodev.corefitpro.trainingItem.dto.projection.WeeklyRhythmProjection;
import br.com.joaojuniodev.corefitpro.trainingItem.enums.DaysOfWeek;
import br.com.joaojuniodev.corefitpro.trainingItem.model.TrainingItem;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Repository
public interface TrainingItemRepository extends JpaRepository<TrainingItem, UUID> {

    @Query("""
        SELECT COUNT(ti.id)
        FROM TrainingItem ti
        WHERE ti.dayOfWeek = :dayOfWeek
          AND ti.trainingPlain.personalTrainer.id = :personalTrainerId
          AND ti.trainingPlain.active = true
    """)
    Long countByDayOfWeekAndPersonalTrainerId(
        @Param("dayOfWeek") DaysOfWeek dayOfWeek,
        @Param("personalTrainerId") UUID personalTrainerId
    );

    @Query("""
        SELECT COUNT(ti.id)
        FROM TrainingItem ti
        WHERE ti.dayOfWeek = :dayOfWeek
          AND ti.completed = false
          AND ti.trainingPlain.personalTrainer.id = :personalTrainerId
          AND ti.trainingPlain.active = true
    """)
    Long countPendingByDayOfWeekAndPersonalTrainerId(
        @Param("dayOfWeek") DaysOfWeek dayOfWeek,
        @Param("personalTrainerId") UUID personalTrainerId
    );

    @Query("""
        SELECT COUNT(ti.id)
        FROM TrainingItem ti
        WHERE ti.dayOfWeek = :dayOfWeek
          AND ti.completed = true
          AND ti.trainingPlain.personalTrainer.id = :personalTrainerId
          AND ti.trainingPlain.active = true
    """)
    Long countCompletedByDayOfWeekAndPersonalTrainerId(
        @Param("dayOfWeek") DaysOfWeek dayOfWeek,
        @Param("personalTrainerId") UUID personalTrainerId
    );

    @Query("""
        SELECT COUNT(DISTINCT ti.trainingPlain.trainee.id)
        FROM TrainingItem ti
        WHERE ti.training.id = :trainingId
          AND ti.trainingPlain.active = true
    """)
    Long countTraineesUseThisTraining(UUID trainingId);

    @EntityGraph(attributePaths = {
        "training",
        "training.exerciseItems",
        "training.exerciseItems.exercise",
        "training.exerciseItems.exercise.muscleGroups",
        "training.muscleGroups",
    })
    @Query("""
        SELECT ti
        FROM TrainingItem ti
        WHERE ti.trainingPlain.trainee.id = :traineeId
          AND ti.trainingPlain.active = true
    """)
    List<TrainingItem> findByTrainee(@Param("traineeId") UUID traineeId);

    @EntityGraph(attributePaths = {
        "training",
        "training.exerciseItems",
        "training.exerciseItems.exercise",
        "training.muscleGroups",
    })
    List<TrainingItem> findAllByTrainingPlainId(UUID trainingPlainId);

    @EntityGraph(attributePaths = {
        "training.exerciseItems",
        "trainingPlain.trainee",
    })
    @Query("""
        SELECT ti
        FROM TrainingItem ti
        WHERE ti.dayOfWeek = :dayOfWeek
          AND ti.trainingPlain.personalTrainer.id = :personalTrainerId
          AND ti.trainingPlain.active = true
    """)
    List<TrainingItem> findByDayOfWeekAndPersonalTrainerId(
        @Param("dayOfWeek") DaysOfWeek dayOfWeek,
        @Param("personalTrainerId") UUID personalTrainerId
    );

    @Query("""
        SELECT
            ti.trainingPlain.trainee.id AS traineeId,
            COUNT(ti.id) AS countIncompleteTraining,
            CONCAT(
                ti.trainingPlain.trainee.firstName,
                ' ',
                ti.trainingPlain.trainee.lastName
            ) AS traineeName
        FROM TrainingItem ti
        WHERE ti.completed = false
          AND ti.trainingPlain.personalTrainer.id = :personalTrainerId
          AND ti.trainingPlain.active = true
        GROUP BY
            ti.trainingPlain.trainee.id,
            ti.trainingPlain.trainee.firstName,
            ti.trainingPlain.trainee.lastName
    """)
    List<NotCompletedTrainingsProjection> findTrainingsIncomplete(
        @Param("personalTrainerId") UUID personalTrainerId
    );

    @Query("""
        SELECT
            ti.dayOfWeek AS dayOfWeek,
            COUNT(ti.id) AS totalTrainings,
            SUM(
                CASE
                    WHEN ti.completed = true AND ti.dateTime >= :weekStart AND ti.dateTime < :weekEnd THEN 1
                    ELSE 0
                END
            ) AS completedTrainings
        FROM TrainingItem ti
        WHERE ti.trainingPlain.personalTrainer.id = :personalTrainerId
          AND ti.trainingPlain.active = true
        GROUP BY ti.dayOfWeek
        ORDER BY ti.dayOfWeek
    """)
    List<WeeklyRhythmProjection> findWeeklyRhythm(
        @Param("personalTrainerId") UUID personalTrainerId,
        @Param("weekStart") LocalDateTime weekStart,
        @Param("weekEnd") LocalDateTime weekEnd
    );
}
