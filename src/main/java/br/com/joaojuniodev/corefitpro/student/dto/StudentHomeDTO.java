package br.com.joaojuniodev.corefitpro.student.dto;

import br.com.joaojuniodev.corefitpro.trainingPlain.dto.response.TrainingPlainDetailsDTO;
import java.time.LocalDate;
import java.util.UUID;

public record StudentHomeDTO(UUID id, String firstName, String lastName, String avatarUrl,
                             String personalTrainerName, LocalDate weekStart, TrainingPlainDetailsDTO plan) {}
