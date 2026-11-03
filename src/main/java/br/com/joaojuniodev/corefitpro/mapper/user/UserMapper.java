package br.com.joaojuniodev.corefitpro.mapper.user;

import br.com.joaojuniodev.corefitpro.mapper.ObjectMapper;
import br.com.joaojuniodev.corefitpro.security.dto.AccountCredentialsDTO;
import br.com.joaojuniodev.corefitpro.security.dto.UserResponseDTO;
import br.com.joaojuniodev.corefitpro.security.model.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper implements ObjectMapper<User, UserResponseDTO, AccountCredentialsDTO> {

    @Override
    public User toEntity(AccountCredentialsDTO request) {
        User user = new User();
        user.setUsername(request.getUsername());
        user.setPassword(request.getPassword());
        user.setFullName(request.getFullName());
        user.setEmail(request.getEmail());
        user.setRecoveryEmail(request.getRecoveryEmail());
        user.setEnabled(true);
        user.setAccountNonExpired(true);
        user.setAccountNonLocked(true);
        user.setCredentialsNonExpired(true);
        return user;
    }

    @Override
    public UserResponseDTO toResponse(User entity) {
        return new UserResponseDTO(
            entity.getUsername(),
            entity.getEnabled()
        );
    }
}