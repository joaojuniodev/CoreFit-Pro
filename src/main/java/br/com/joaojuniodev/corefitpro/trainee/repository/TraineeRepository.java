package br.com.joaojuniodev.corefitpro.trainee.repository;

import br.com.joaojuniodev.corefitpro.trainee.model.Trainee;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface TraineeRepository extends JpaRepository<Trainee, UUID> {

    @EntityGraph(attributePaths = {"physicalAssessments"})
    List<Trainee> findAll(Specification<Trainee> spec);

    @EntityGraph(attributePaths = {"physicalAssessments"})
    Optional<Trainee> findById(UUID id);

    @EntityGraph(attributePaths = {"physicalAssessments", "user", "personalTrainer"})
    Optional<Trainee> findByUserUsername(String username);

    Long countByUserEnabledTrueAndPersonalTrainerId(UUID personalTrainerId);
}
