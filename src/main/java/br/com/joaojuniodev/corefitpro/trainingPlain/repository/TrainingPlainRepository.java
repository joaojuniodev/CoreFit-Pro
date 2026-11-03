package br.com.joaojuniodev.corefitpro.trainingPlain.repository;

import br.com.joaojuniodev.corefitpro.trainingPlain.model.TrainingPlain;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface TrainingPlainRepository extends JpaRepository<TrainingPlain, UUID> {

    @EntityGraph(attributePaths = {"personalTrainer", "trainee"})
    List<TrainingPlain> findAll();

    @EntityGraph(attributePaths = {
        "personalTrainer",
        "trainee",
        "trainings",
        "trainings.training",
        "trainings.training.muscleGroups",
        "trainings.training.exerciseItems",
        "trainings.training.exerciseItems.exercise",
    })
    Optional<TrainingPlain> findById(UUID id);

    @Query("SELECT tp FROM TrainingPlain tp WHERE tp.trainee.id = :traineeId AND tp.active = true")
    List<TrainingPlain> findAllActiveByTrainee(@Param("traineeId") UUID traineeId);

    Optional<TrainingPlain> findFirstByTraineeIdAndActiveTrueOrderByCreatedAtDescIdDesc(UUID traineeId);

    default Optional<TrainingPlain> findByTrainee(UUID traineeId) {
        return findFirstByTraineeIdAndActiveTrueOrderByCreatedAtDescIdDesc(traineeId);
    }

    Long countByActiveTrueAndPersonalTrainerId(UUID personalTrainerId);
}
