package br.com.joaojuniodev.corefitpro.exercise.controller;

import br.com.joaojuniodev.corefitpro.exercise.dto.request.ExerciseRequestDTO;
import br.com.joaojuniodev.corefitpro.exercise.dto.response.ExerciseResponseDTO;
import br.com.joaojuniodev.corefitpro.exercise.service.ExerciseService;
import br.com.joaojuniodev.corefitpro.infrastructure.backblaze.dto.StoredFileResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/exercises/v1")
public class ExerciseController {

    private final ExerciseService exerciseService;
    private final ObjectMapper objectMapper;

    public ExerciseController(ExerciseService exerciseService, ObjectMapper objectMapper) {
        this.exerciseService = exerciseService;
        this.objectMapper = objectMapper;
    }

    @GetMapping
    public ResponseEntity<List<ExerciseResponseDTO>> getAll(
        @RequestParam(required = false) UUID personalTrainerId
    ) {
        return ResponseEntity.ok().body(exerciseService.getAll(personalTrainerId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ExerciseResponseDTO> getById(@PathVariable UUID id) {
        return ResponseEntity.ok().body(exerciseService.getById(id));
    }

    @PostMapping("/addFile/{id}")
    public ResponseEntity<StoredFileResponse> addFile(
        @PathVariable UUID id,
        @RequestParam(name = "file") MultipartFile file,
        @RequestParam(name = "folder", defaultValue = "/exercises/images") String folder
    ) {
        return ResponseEntity.ok().body(exerciseService.addFile(id, file, folder));
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ExerciseResponseDTO> create(
        @RequestPart(name = "exercise") String exerciseJson,
        @RequestPart(value = "file", required = false) MultipartFile file
    ) throws JsonProcessingException {
        ExerciseRequestDTO exercise = objectMapper.readValue(exerciseJson, ExerciseRequestDTO.class);
        return ResponseEntity.ok().body(exerciseService.create(exercise, file));
    }

    @PutMapping
    public ResponseEntity<ExerciseResponseDTO> update(
        @RequestPart(name = "exercise") String exerciseJson,
        @RequestPart(value = "file", required = false) MultipartFile file
    ) throws JsonProcessingException {
        ExerciseRequestDTO exercise = objectMapper.readValue(exerciseJson, ExerciseRequestDTO.class);
        return ResponseEntity.ok().body(exerciseService.update(exercise, file));
    }

    @PatchMapping("/toggleFavorite/{id}")
    public ResponseEntity<ExerciseResponseDTO> toggleFavorite(@PathVariable UUID id, @RequestBody Boolean favorite) {
        return ResponseEntity.ok().body(exerciseService.toggleFavorite(id, favorite));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable UUID id) {
        exerciseService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
