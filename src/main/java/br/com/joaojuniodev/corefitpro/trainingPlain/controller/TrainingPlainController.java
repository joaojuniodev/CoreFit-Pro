package br.com.joaojuniodev.corefitpro.trainingPlain.controller;

import br.com.joaojuniodev.corefitpro.trainingPlain.dto.request.TrainingPlainRequestDTO;
import br.com.joaojuniodev.corefitpro.trainingPlain.dto.response.TrainingPlainDetailsDTO;
import br.com.joaojuniodev.corefitpro.trainingPlain.dto.response.TrainingPlainResponseDTO;
import br.com.joaojuniodev.corefitpro.trainingPlain.service.TrainingPlainService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.security.Principal;
import br.com.joaojuniodev.corefitpro.trainingPlain.dto.request.TrainingPlanSaveDTO;

@RestController
@RequestMapping("/api/trainingsPlans/v1")
public class TrainingPlainController {

    private final TrainingPlainService trainingPlainService;

    public TrainingPlainController(TrainingPlainService trainingPlainService) {
        this.trainingPlainService = trainingPlainService;
    }

    @GetMapping
    public ResponseEntity<List<TrainingPlainResponseDTO>> getAll() {
        return ResponseEntity.ok().body(trainingPlainService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<TrainingPlainDetailsDTO> getById(@PathVariable UUID id) {
        return ResponseEntity.ok().body(trainingPlainService.getById(id));
    }

    @GetMapping("/trainee/{traineeId}")
    public ResponseEntity<TrainingPlainDetailsDTO> getByTrainee(@PathVariable UUID traineeId) {
        return ResponseEntity.ok().body(trainingPlainService.getByTrainee(traineeId));
    }

    @PostMapping
    public ResponseEntity<TrainingPlainResponseDTO> create(@RequestBody TrainingPlainRequestDTO trainingPlain) {
        return ResponseEntity.ok().body(trainingPlainService.create(trainingPlain));
    }

    @PutMapping
    public ResponseEntity<TrainingPlainResponseDTO> update(@RequestBody TrainingPlainRequestDTO trainingPlain) {
        return ResponseEntity.ok().body(trainingPlainService.update(trainingPlain));
    }

    @PostMapping("/with-items")
    public TrainingPlainDetailsDTO createComplete(@RequestBody TrainingPlanSaveDTO request, Principal principal) {
        if (request.id() != null) throw new org.springframework.web.server.ResponseStatusException(org.springframework.http.HttpStatus.BAD_REQUEST, "Um novo plano não deve ter ID.");
        return trainingPlainService.saveComplete(request, principal.getName());
    }

    @PutMapping("/with-items")
    public TrainingPlainDetailsDTO updateComplete(@RequestBody TrainingPlanSaveDTO request, Principal principal) {
        if (request.id() == null) throw new org.springframework.web.server.ResponseStatusException(org.springframework.http.HttpStatus.BAD_REQUEST, "Informe o ID do plano.");
        return trainingPlainService.saveComplete(request, principal.getName());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable UUID id) {
        trainingPlainService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
