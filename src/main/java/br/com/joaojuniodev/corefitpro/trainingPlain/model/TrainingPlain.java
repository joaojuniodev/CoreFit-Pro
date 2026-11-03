package br.com.joaojuniodev.corefitpro.trainingPlain.model;

import br.com.joaojuniodev.corefitpro.personalTrainer.model.PersonalTrainer;
import br.com.joaojuniodev.corefitpro.trainee.model.Trainee;
import br.com.joaojuniodev.corefitpro.trainingItem.model.TrainingItem;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "training_plans")
public class TrainingPlain {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "personal_trainer_id")
    private PersonalTrainer personalTrainer;

    @ManyToOne
    @JoinColumn(name = "trainee_id")
    private Trainee trainee;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    public LocalDateTime getCreatedAt() { return createdAt; }

    @Column
    private String description;

    @Column
    private Boolean active;

    @Column
    private String objective;

    @Column
    private String goal;

    @OneToMany(mappedBy = "trainingPlain")
    private List<TrainingItem> trainings;

    public TrainingPlain() {}

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public PersonalTrainer getPersonalTrainer() {
        return personalTrainer;
    }

    public void setPersonalTrainer(PersonalTrainer personalTrainer) {
        this.personalTrainer = personalTrainer;
    }

    public Trainee getTrainee() {
        return trainee;
    }

    public void setTrainee(Trainee trainee) {
        this.trainee = trainee;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public String getObjective() {
        return objective;
    }

    public void setObjective(String objective) {
        this.objective = objective;
    }

    public String getGoal() {
        return goal;
    }

    public void setGoal(String goal) {
        this.goal = goal;
    }

    public List<TrainingItem> getTrainings() {
        return trainings;
    }

    public void setTrainings(List<TrainingItem> trainings) {
        this.trainings = trainings;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;

        TrainingPlain that = (TrainingPlain) o;
        return Objects.equals(getId(), that.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }
}
