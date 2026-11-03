package br.com.joaojuniodev.corefitpro.physicalAssessment.model;

import br.com.joaojuniodev.corefitpro.physicalAssessment.enums.ActivityLevel;
import br.com.joaojuniodev.corefitpro.trainee.model.Trainee;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "physical_assessments")
public class PhysicalAssessment {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "trainee_id")
    private Trainee trainee;

    @Column(name = "registered_at")
    private LocalDate registeredAt;

    @Column
    private Integer age;

    @Column
    private Double weight;

    @Column
    private Double height;

    @Column
    private Double imc;

    @Column(name = "body_fat")
    private Double bodyFat;

    @Column(name = "fat_mass")
    private Double fatMass;

    @Column(name = "muscle_mass")
    private Double muscleMass;

    @Column
    private Double chest;

    @Column
    private Double waist;

    @Column
    private Double abdomen;

    @Column
    private Double hip;

    @Column(name = "right_arm")
    private Double rightArm;

    @Column(name = "left_arm")
    private Double leftArm;

    @Column(name = "right_thigh")
    private Double rightThigh;

    @Column(name = "left_thigh")
    private Double leftThigh;

    @Enumerated(EnumType.STRING)
    private ActivityLevel activityLevel;

    @Column
    private String notes;

    @Column(name = "front_view_photo_key")
    private String frontViewPhotoKey;

    @Column(name = "front_view_photo_file_id")
    private String frontViewPhotoFileId;

    @Column(name = "back_view_photo_key")
    private String backViewPhotoKey;

    @Column(name = "back_view_photo_file_id")
    private String backViewPhotoFileId;

    @Column(name = "left_view_photo_key")
    private String leftViewPhotoKey;

    @Column(name = "left_view_photo_file_id")
    private String leftViewPhotoFileId;

    @Column(name = "right_view_photo_key")
    private String rightViewPhotoKey;

    @Column(name = "right_view_photo_file_id")
    private String rightViewPhotoFileId;

    public PhysicalAssessment() {}

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Trainee getTrainee() {
        return trainee;
    }

    public void setTrainee(Trainee trainee) {
        this.trainee = trainee;
    }

    public LocalDate getRegisteredAt() {
        return registeredAt;
    }

    public void setRegisteredAt(LocalDate registeredAt) {
        this.registeredAt = registeredAt;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Double getWeight() {
        return weight;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }

    public Double getHeight() {
        return height;
    }

    public void setHeight(Double height) {
        this.height = height;
    }

    public Double getImc() {
        return imc;
    }

    public void setImc(Double imc) {
        this.imc = imc;
    }

    public Double getBodyFat() {
        return bodyFat;
    }

    public void setBodyFat(Double bodyFat) {
        this.bodyFat = bodyFat;
    }

    public Double getFatMass() {
        return fatMass;
    }

    public void setFatMass(Double fatMass) {
        this.fatMass = fatMass;
    }

    public Double getMuscleMass() {
        return muscleMass;
    }

    public void setMuscleMass(Double muscleMass) {
        this.muscleMass = muscleMass;
    }

    public Double getChest() {
        return chest;
    }

    public void setChest(Double chest) {
        this.chest = chest;
    }

    public Double getWaist() {
        return waist;
    }

    public void setWaist(Double waist) {
        this.waist = waist;
    }

    public Double getAbdomen() {
        return abdomen;
    }

    public void setAbdomen(Double abdomen) {
        this.abdomen = abdomen;
    }

    public Double getHip() {
        return hip;
    }

    public void setHip(Double hip) {
        this.hip = hip;
    }

    public Double getRightArm() {
        return rightArm;
    }

    public void setRightArm(Double rightArm) {
        this.rightArm = rightArm;
    }

    public Double getLeftArm() {
        return leftArm;
    }

    public void setLeftArm(Double leftArm) {
        this.leftArm = leftArm;
    }

    public Double getRightThigh() {
        return rightThigh;
    }

    public void setRightThigh(Double rightThigh) {
        this.rightThigh = rightThigh;
    }

    public Double getLeftThigh() {
        return leftThigh;
    }

    public void setLeftThigh(Double leftThigh) {
        this.leftThigh = leftThigh;
    }

    public ActivityLevel getActivityLevel() {
        return activityLevel;
    }

    public void setActivityLevel(ActivityLevel activityLevel) {
        this.activityLevel = activityLevel;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getFrontViewPhotoKey() {
        return frontViewPhotoKey;
    }

    public void setFrontViewPhotoKey(String frontViewPhotoKey) {
        this.frontViewPhotoKey = frontViewPhotoKey;
    }

    public String getFrontViewPhotoFileId() {
        return frontViewPhotoFileId;
    }

    public void setFrontViewPhotoFileId(String frontViewPhotoFileId) {
        this.frontViewPhotoFileId = frontViewPhotoFileId;
    }

    public String getBackViewPhotoKey() {
        return backViewPhotoKey;
    }

    public void setBackViewPhotoKey(String backViewPhotoKey) {
        this.backViewPhotoKey = backViewPhotoKey;
    }

    public String getBackViewPhotoFileId() {
        return backViewPhotoFileId;
    }

    public void setBackViewPhotoFileId(String backViewPhotoFileId) {
        this.backViewPhotoFileId = backViewPhotoFileId;
    }

    public String getLeftViewPhotoKey() {
        return leftViewPhotoKey;
    }

    public void setLeftViewPhotoKey(String leftViewPhotoKey) {
        this.leftViewPhotoKey = leftViewPhotoKey;
    }

    public String getLeftViewPhotoFileId() {
        return leftViewPhotoFileId;
    }

    public void setLeftViewPhotoFileId(String leftViewPhotoFileId) {
        this.leftViewPhotoFileId = leftViewPhotoFileId;
    }

    public String getRightViewPhotoKey() {
        return rightViewPhotoKey;
    }

    public void setRightViewPhotoKey(String rightViewPhotoKey) {
        this.rightViewPhotoKey = rightViewPhotoKey;
    }

    public String getRightViewPhotoFileId() {
        return rightViewPhotoFileId;
    }

    public void setRightViewPhotoFileId(String rightViewPhotoFileId) {
        this.rightViewPhotoFileId = rightViewPhotoFileId;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;

        PhysicalAssessment that = (PhysicalAssessment) o;
        return Objects.equals(getId(), that.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }
}