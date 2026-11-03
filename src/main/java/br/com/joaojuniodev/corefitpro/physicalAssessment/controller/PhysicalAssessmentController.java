package br.com.joaojuniodev.corefitpro.physicalAssessment.controller;

import br.com.joaojuniodev.corefitpro.physicalAssessment.dto.request.PhysicalAssessmentRequestDTO;
import br.com.joaojuniodev.corefitpro.physicalAssessment.dto.response.PhysicalAssessmentResponseDTO;
import br.com.joaojuniodev.corefitpro.physicalAssessment.service.PhysicalAssessmentService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/physicalAssessments/v1")
public class PhysicalAssessmentController {

    private final PhysicalAssessmentService physicalAssessmentService;
    private final ObjectMapper objectMapper;

    public PhysicalAssessmentController(PhysicalAssessmentService physicalAssessmentService, ObjectMapper objectMapper) {
        this.physicalAssessmentService = physicalAssessmentService;
        this.objectMapper = objectMapper;
    }

    @GetMapping
    public ResponseEntity<List<PhysicalAssessmentResponseDTO>> getAll(
        @RequestParam(name = "traineeId", required = false) UUID traineeId
    ) {
        return ResponseEntity.ok().body(physicalAssessmentService.getAll(traineeId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<PhysicalAssessmentResponseDTO> getById(@PathVariable UUID id) {
        return ResponseEntity.ok().body(physicalAssessmentService.getById(id));
    }

    @PostMapping
    public ResponseEntity<?> create(
        @RequestPart(name = "frontPhoto", required = false) MultipartFile frontPhoto,
        @RequestPart(name = "backPhoto", required = false) MultipartFile backPhoto,
        @RequestPart(name = "leftPhoto", required = false) MultipartFile leftPhoto,
        @RequestPart(name = "rightPhoto", required = false) MultipartFile rightPhoto,
        @RequestPart(name = "physicalAssessment") String physicalAssessmentJson
    ) {
        PhysicalAssessmentRequestDTO physicalAssessment = null;
        try {
            physicalAssessment = objectMapper.readValue(physicalAssessmentJson, PhysicalAssessmentRequestDTO.class);
        }
        catch (JsonProcessingException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error in parsing for object Physical Assessment");
        }
        return ResponseEntity.ok().body(physicalAssessmentService.create(physicalAssessment, frontPhoto, backPhoto, leftPhoto, rightPhoto));
    }

    @PutMapping
    public ResponseEntity<PhysicalAssessmentResponseDTO> update(@RequestBody PhysicalAssessmentRequestDTO physicalAssessment) {
        return ResponseEntity.ok().body(physicalAssessmentService.update(physicalAssessment));
    }

    @PutMapping(value = "/{id}/photos", consumes = "multipart/form-data")
    public ResponseEntity<PhysicalAssessmentResponseDTO> updatePhotos(
        @PathVariable UUID id,
        @RequestPart(name = "frontPhoto", required = false) MultipartFile frontPhoto,
        @RequestPart(name = "backPhoto", required = false) MultipartFile backPhoto,
        @RequestPart(name = "leftPhoto", required = false) MultipartFile leftPhoto,
        @RequestPart(name = "rightPhoto", required = false) MultipartFile rightPhoto
    ) {
        return ResponseEntity.ok().body(
            physicalAssessmentService.updatePhotos(id, frontPhoto, backPhoto, leftPhoto, rightPhoto)
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable UUID id) {
        physicalAssessmentService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
