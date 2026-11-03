package br.com.joaojuniodev.corefitpro.trainingPlain.dto.request;

import java.util.UUID;

public record TrainingPlainRequestDTO(
    UUID id,
    String description,
    String objective,
    UUID personalTrainerId,
    UUID traineeId,
    String goal
) {}
