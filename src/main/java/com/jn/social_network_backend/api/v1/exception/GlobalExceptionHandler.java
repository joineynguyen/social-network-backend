package com.jn.social_network_backend.api.v1.exception;

import com.jn.social_network_backend.domain.exception.FriendshipAlreadyExistsException;
import com.jn.social_network_backend.domain.exception.InvalidFriendshipStateException;
import com.jn.social_network_backend.domain.exception.NoActiveFriendshipsFoundException;
import com.jn.social_network_backend.domain.exception.ResourceNotFoundException;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<Map<String, String>> handleNotFound(ResourceNotFoundException ex) {
        Map<String, String> body = Map.of(
                "error", ex.getMessage(),
                "code", "RESOURCE_NOT_FOUND"
        );
        
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(body);
    }

    @ExceptionHandler(InvalidFriendshipStateException.class)
    public ResponseEntity<Map<String, String>> handleInvalidState(InvalidFriendshipStateException ex) {
        Map<String, String> body = Map.of(
                "error", ex.getMessage(),
                "code", "INVALID_FRIENDSHIP_STATE"
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body);
    }

    @ExceptionHandler(FriendshipAlreadyExistsException.class)
    public ResponseEntity<Map<String, String>> handleFriendshipAlreadyExists(FriendshipAlreadyExistsException ex) {
        Map<String, String> body = Map.of(
            "error", ex.getMessage(),
            "code", "FRIENDSHIP_ALREADY_EXISTS"
        );
        
        return ResponseEntity.status(HttpStatus.CONFLICT).body(body);
    }

    @ExceptionHandler(NoActiveFriendshipsFoundException.class)
    public ResponseEntity<Map<String, String>> handleNoActiveFriendshipsFound(NoActiveFriendshipsFoundException ex) {
        Map<String, String> body = Map.of(
            "error", ex.getMessage(),
            "code", "NO_ACTIVE_FRIENDSHIPS_FOUND"
        );

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(body);
    }

    // Optional: Catch-all
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, String>> handleAll(Exception ex) {
        Map<String, String> body = Map.of(
                "error", ex.getMessage(),
                "code", "INTERNAL_ERROR"
        );
        
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(body);
    }
}
