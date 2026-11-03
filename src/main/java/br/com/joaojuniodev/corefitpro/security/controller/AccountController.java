package br.com.joaojuniodev.corefitpro.security.controller;

import br.com.joaojuniodev.corefitpro.security.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import java.security.Principal;

@RestController
@RequestMapping("/api/account/v1")
public class AccountController {
    private final UserRepository users;
    private final PasswordEncoder encoder;
    public AccountController(UserRepository users, PasswordEncoder encoder) { this.users = users; this.encoder = encoder; }

    @GetMapping
    public AccountDTO me(Principal principal) {
        var user = users.findByUserName(principal.getName());
        return new AccountDTO(user.getUsername(), user.getFullName(), user.getEmail(), user.getRecoveryEmail());
    }

    @PutMapping
    @Transactional
    public AccountDTO update(Principal principal, @RequestBody AccountDTO request) {
        if (request.fullName() == null || request.fullName().isBlank() || request.email() == null ||
            !request.email().matches("^[^\\s@]+@[^\\s@]+\\.[^\\s@]+$") ||
            (request.recoveryEmail() != null && !request.recoveryEmail().isBlank() &&
             !request.recoveryEmail().matches("^[^\\s@]+@[^\\s@]+\\.[^\\s@]+$"))) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Informe nome e e-mails válidos.");
        }
        var user = users.findByUserName(principal.getName());
        user.setFullName(request.fullName().trim());
        user.setEmail(request.email().trim());
        user.setRecoveryEmail(request.recoveryEmail() == null || request.recoveryEmail().isBlank()
            ? request.email().trim() : request.recoveryEmail().trim());
        users.save(user);
        return me(principal);
    }

    @PutMapping("/password")
    @Transactional
    public void password(Principal principal, @RequestBody PasswordRequest request) {
        var user = users.findByUserName(principal.getName());
        if (request.currentPassword() == null || !encoder.matches(request.currentPassword(), user.getPassword())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "A senha atual está incorreta.");
        }
        if (request.newPassword() == null || request.newPassword().length() < 6) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "A nova senha deve ter pelo menos 6 caracteres.");
        }
        user.setPassword(encoder.encode(request.newPassword()));
        users.save(user);
    }

    public record AccountDTO(String username, String fullName, String email, String recoveryEmail) {}
    public record PasswordRequest(String currentPassword, String newPassword) {}
}
