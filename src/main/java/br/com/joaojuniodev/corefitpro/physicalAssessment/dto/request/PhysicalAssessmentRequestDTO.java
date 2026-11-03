package br.com.joaojuniodev.corefitpro.physicalAssessment.dto.request;

import br.com.joaojuniodev.corefitpro.physicalAssessment.enums.ActivityLevel;

import java.time.LocalDate;
import java.util.UUID;

public record PhysicalAssessmentRequestDTO(
    UUID id,
    UUID traineeId,
    LocalDate registeredAt,
    Integer age,
    Double weight,
    Double height,
    Double imc,
    Double bodyFat,
    Double fatMass,
    Double muscleMass,
    Double chest,
    Double waist,
    Double abdomen,
    Double hip,
    Double rightArm,
    Double leftArm,
    Double rightThigh,
    Double leftThigh,
    ActivityLevel activityLevel,
    String notes
) {}