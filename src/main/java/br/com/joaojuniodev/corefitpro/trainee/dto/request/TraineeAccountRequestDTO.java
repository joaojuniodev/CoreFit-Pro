package br.com.joaojuniodev.corefitpro.trainee.dto.request;

public record TraineeAccountRequestDTO(String firstName, String lastName, String username,
                                       String email, String recoveryEmail, Boolean enabled) {}
