package br.com.joaojuniodev.corefitpro.student.controller;

import br.com.joaojuniodev.corefitpro.student.dto.StudentHomeDTO;
import br.com.joaojuniodev.corefitpro.student.service.StudentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.security.Principal;
import java.util.UUID;

@RestController
@RequestMapping("/api/student/v1")
public class StudentController {
    private final StudentService service;
    public StudentController(StudentService service) { this.service = service; }

    @GetMapping
    public StudentHomeDTO home(Principal principal) { return service.getHome(principal.getName()); }

    @PatchMapping("/trainings/{itemId}/completion")
    public ResponseEntity<Void> complete(Principal principal, @PathVariable UUID itemId,
                                        @RequestBody CompletionRequest request) {
        service.complete(principal.getName(), itemId, request.completed());
        return ResponseEntity.noContent().build();
    }

    public record CompletionRequest(boolean completed) {}
}
