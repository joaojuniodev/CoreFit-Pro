package br.com.joaojuniodev.corefitpro.trainee.dto.response;

import java.util.UUID;

public record TraineeAccountResponseDTO(UUID traineeId, String firstName, String lastName,
                                        String username, String email, String recoveryEmail, boolean enabled) {}
