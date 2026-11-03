package br.com.joaojuniodev.corefitpro.trainee.controller;

import br.com.joaojuniodev.corefitpro.exercise.dto.request.ExerciseRequestDTO;
import br.com.joaojuniodev.corefitpro.trainee.dto.request.TraineeRequestDTO;
import br.com.joaojuniodev.corefitpro.trainee.dto.response.TraineeDetailsDTO;
import br.com.joaojuniodev.corefitpro.trainee.dto.response.TraineeResponseDTO;
import br.com.joaojuniodev.corefitpro.trainee.dto.response.TraineeSummaryDTO;
import br.com.joaojuniodev.corefitpro.trainee.service.TraineeService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/trainees/v1")
public class TraineeController {

    private final TraineeService traineeService;
    private final ObjectMapper objectMapper;

    public TraineeController(TraineeService traineeService, ObjectMapper objectMapper) {
        this.traineeService = traineeService;
        this.objectMapper = objectMapper;
    }

    @GetMapping
    public ResponseEntity<List<TraineeResponseDTO>> getAll(
        @RequestParam(name = "personalTrainerId", required = false) UUID personalTrainerId
    ) {
        return ResponseEntity.ok().body(traineeService.getAll(personalTrainerId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<TraineeResponseDTO> getById(@PathVariable UUID id) {
        return ResponseEntity.ok().body(traineeService.getById(id));
    }

    @GetMapping("/details/{id}")
    public ResponseEntity<TraineeDetailsDTO> getDetailsById(@PathVariable UUID id) {
        return ResponseEntity.ok().body(traineeService.getDetails(id));
    }

    @PostMapping
    public ResponseEntity<?> create(
        @RequestPart(name = "trainee") String traineeJson,
        @RequestPart(name = "avatar", required = false) MultipartFile avatar
    ) {
        TraineeRequestDTO trainee = null;
        try {
            trainee = objectMapper.readValue(traineeJson, TraineeRequestDTO.class);
        }
        catch (JsonProcessingException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error in parsing for object Trainee");
        }
        return ResponseEntity.ok().body(traineeService.create(trainee, avatar));
    }

    @PutMapping
    public ResponseEntity<TraineeResponseDTO> update(@RequestBody TraineeRequestDTO trainee) {
        return ResponseEntity.ok().body(traineeService.update(trainee));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable UUID id) {
        traineeService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
