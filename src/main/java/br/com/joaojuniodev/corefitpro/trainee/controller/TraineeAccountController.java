package br.com.joaojuniodev.corefitpro.trainee.controller;

import br.com.joaojuniodev.corefitpro.trainee.dto.request.TraineeAccountRequestDTO;
import br.com.joaojuniodev.corefitpro.trainee.dto.request.TraineePasswordRequestDTO;
import br.com.joaojuniodev.corefitpro.trainee.dto.response.TraineeAccountResponseDTO;
import br.com.joaojuniodev.corefitpro.trainee.service.TraineeAccountService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.security.Principal;
import java.util.UUID;

@RestController
@RequestMapping("/api/trainees/v1/{traineeId}/account")
public class TraineeAccountController {
    private final TraineeAccountService service;

    public TraineeAccountController(TraineeAccountService service) {
        this.service = service;
    }

    @GetMapping
    public TraineeAccountResponseDTO get(@PathVariable UUID traineeId, Principal principal) {
        return service.get(traineeId, principal.getName());
    }

    @PutMapping
    public TraineeAccountResponseDTO update(@PathVariable UUID traineeId, Principal principal,
                                            @RequestBody TraineeAccountRequestDTO request) {
        return service.update(traineeId, principal.getName(), request);
    }

    @PutMapping("/password")
    public ResponseEntity<Void> resetPassword(@PathVariable UUID traineeId, Principal principal,
                                             @RequestBody TraineePasswordRequestDTO request) {
        service.resetPassword(traineeId, principal.getName(), request);
        return ResponseEntity.noContent().build();
    }
}
