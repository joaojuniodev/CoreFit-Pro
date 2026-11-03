package br.com.joaojuniodev.corefitpro.trainee.dto.request;

import br.com.joaojuniodev.corefitpro.security.dto.AccountCredentialsDTO;

import java.util.UUID;

public record TraineeRequestDTO(
    UUID id,
    String firstName,
    String lastName,
    UUID personalTrainerId,
    AccountCredentialsDTO credentials
) {}