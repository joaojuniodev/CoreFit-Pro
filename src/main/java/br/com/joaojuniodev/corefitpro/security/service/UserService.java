package br.com.joaojuniodev.corefitpro.security.service;

import br.com.joaojuniodev.corefitpro.exceptions.NotFoundException;
import br.com.joaojuniodev.corefitpro.mapper.user.UserMapper;
import br.com.joaojuniodev.corefitpro.security.dto.AccountCredentialsDTO;
import br.com.joaojuniodev.corefitpro.security.dto.UserResponseDTO;
import br.com.joaojuniodev.corefitpro.security.model.Role;
import br.com.joaojuniodev.corefitpro.security.repository.RoleRepository;
import br.com.joaojuniodev.corefitpro.security.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserService implements UserDetailsService {

    private static final Logger logger = LoggerFactory.getLogger(UserService.class.getName());

    @Autowired
    private UserRepository repository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private UserMapper mapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        var user = repository.findByUserName(username);
        if (user == null) {
            throw new UsernameNotFoundException("Username: " + username + " not found");
        }
        return user;
    }

    @Transactional
    public UserResponseDTO registerByTrainee(AccountCredentialsDTO credentials) {
        logger.info("Creating a new User");

        if (credentials == null || credentials.getUsername() == null || credentials.getUsername().isBlank() ||
            credentials.getPassword() == null || credentials.getPassword().isBlank()) {
            throw new IllegalArgumentException("Informe usuário e senha para cadastrar o aluno.");
        }
        if (repository.findByUserName(credentials.getUsername()) != null) {
            throw new org.springframework.web.server.ResponseStatusException(org.springframework.http.HttpStatus.CONFLICT, "Este usuário já está cadastrado. Escolha outro usuário.");
        }
        var entity = mapper.toEntity(credentials);

        Role role = roleRepository.findByName("TRAINEE")
            .orElseThrow(() -> new NotFoundException("Not found Role by name: TRAINEE"));

        entity.setRoles(List.of(role));

        return mapper.toResponse(repository.save(entity));
    }
}
