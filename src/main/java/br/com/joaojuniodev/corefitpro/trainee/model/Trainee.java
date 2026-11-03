package br.com.joaojuniodev.corefitpro.trainee.model;

import br.com.joaojuniodev.corefitpro.personalTrainer.model.PersonalTrainer;
import br.com.joaojuniodev.corefitpro.physicalAssessment.model.PhysicalAssessment;
import br.com.joaojuniodev.corefitpro.security.model.User;
import jakarta.persistence.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "trainees")
public class Trainee {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column
    private String firstName;

    @Column
    private String lastName;

    @Column
    private String avatarKey;

    @Column
    private String avatarFileId;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "personal_trainer_id")
    private PersonalTrainer personalTrainer;

    @OneToMany(mappedBy = "trainee")
    private List<PhysicalAssessment> physicalAssessments;

    public Trainee() {}

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getAvatarKey() {
        return avatarKey;
    }

    public void setAvatarKey(String avatarKey) {
        this.avatarKey = avatarKey;
    }

    public String getAvatarFileId() {
        return avatarFileId;
    }

    public void setAvatarFileId(String avatarFileId) {
        this.avatarFileId = avatarFileId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public PersonalTrainer getPersonalTrainer() {
        return personalTrainer;
    }

    public void setPersonalTrainer(PersonalTrainer personalTrainer) {
        this.personalTrainer = personalTrainer;
    }

    public List<PhysicalAssessment> getPhysicalAssessments() {
        return physicalAssessments;
    }

    public void setPhysicalAssessments(List<PhysicalAssessment> physicalAssessments) {
        this.physicalAssessments = physicalAssessments;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;

        Trainee trainee = (Trainee) o;
        return Objects.equals(getId(), trainee.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }
}