package br.com.joaojuniodev.corefitpro.security.dto;

public record UserResponseDTO(
    String username,
    Boolean enabled
) {
}