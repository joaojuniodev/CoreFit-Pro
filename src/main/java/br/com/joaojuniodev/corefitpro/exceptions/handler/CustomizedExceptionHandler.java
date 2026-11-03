package br.com.joaojuniodev.corefitpro.exceptions.handler;

import br.com.joaojuniodev.corefitpro.exceptions.*;
import br.com.joaojuniodev.corefitpro.exceptions.dto.ExceptionResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.util.Date;

@RestController
@RestControllerAdvice
public class CustomizedExceptionHandler {

    @ExceptionHandler(org.springframework.web.server.ResponseStatusException.class)
    public ResponseEntity<ExceptionResponse> handleStatus(org.springframework.web.server.ResponseStatusException ex, WebRequest request) {
        return ResponseEntity.status(ex.getStatusCode()).body(new ExceptionResponse(ex.getReason(), request.getDescription(false), new Date()));
    }

    @ExceptionHandler(org.springframework.security.core.AuthenticationException.class)
    public ResponseEntity<ExceptionResponse> handleAuthentication(Exception ex, WebRequest request) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ExceptionResponse("Usuário ou senha inválidos.", request.getDescription(false), new Date()));
    }

    @ExceptionHandler(org.springframework.security.access.AccessDeniedException.class)
    public ResponseEntity<ExceptionResponse> handleAccessDenied(Exception ex, WebRequest request) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new ExceptionResponse("Você não possui acesso a este recurso.", request.getDescription(false), new Date()));
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ExceptionResponse> handleInvalidInput(Exception ex, WebRequest request) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ExceptionResponse(ex.getMessage(), request.getDescription(false), new Date()));
    }

    @ExceptionHandler(org.springframework.dao.DataIntegrityViolationException.class)
    public ResponseEntity<ExceptionResponse> handleConflict(Exception ex, WebRequest request) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(new ExceptionResponse("Já existe um cadastro com estes dados ou este registro está em uso.", request.getDescription(false), new Date()));
    }

    @ExceptionHandler(Exception.class)
    public final ResponseEntity<ExceptionResponse> handleAllExceptions(Exception ex, WebRequest request) {
        ExceptionResponse response = new ExceptionResponse(
            ex.getMessage(),
            request.getDescription(true),
            new Date()
        );
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(NotFoundException.class)
    public final ResponseEntity<ExceptionResponse> handleNotFoundException(Exception ex, WebRequest request) {
        ExceptionResponse response = new ExceptionResponse(
            ex.getMessage(),
            request.getDescription(true),
            new Date()
        );
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(IsEmptyObjectException.class)
    public final ResponseEntity<ExceptionResponse> handleIsEmptyObjectException(Exception ex, WebRequest request) {
        ExceptionResponse response = new ExceptionResponse(
            ex.getMessage(),
            request.getDescription(true),
            new Date()
        );
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ObjectIsNullException.class)
    public final ResponseEntity<ExceptionResponse> handleObjectIsNullException(Exception ex, WebRequest request) {
        ExceptionResponse response = new ExceptionResponse(
            ex.getMessage(),
            request.getDescription(true),
            new Date()
        );
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(FileStorageException.class)
    public final ResponseEntity<ExceptionResponse> handleFileStorageException(Exception ex, WebRequest request) {
        ExceptionResponse response = new ExceptionResponse(
            ex.getMessage(),
            request.getDescription(true),
            new Date()
        );
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
}
