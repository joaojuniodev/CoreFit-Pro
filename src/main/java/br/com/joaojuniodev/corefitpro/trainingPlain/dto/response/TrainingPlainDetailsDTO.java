package br.com.joaojuniodev.corefitpro.trainingPlain.dto.response;

import br.com.joaojuniodev.corefitpro.personalTrainer.dto.response.PersonalTrainerSummaryDTO;
import br.com.joaojuniodev.corefitpro.trainee.dto.response.TraineeResponseDTO;
import br.com.joaojuniodev.corefitpro.trainingItem.dto.response.TrainingItemResponseDTO;

import java.util.List;
import java.util.UUID;

public record TrainingPlainDetailsDTO(
    UUID id,
    PersonalTrainerSummaryDTO personalTrainer,
    TraineeResponseDTO trainee,
    String description,
    String objective,
    String goal,
    List<TrainingItemResponseDTO> trainings
) {}
