package uz.work.worldcamp.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.annotation.security.PermitAll;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import uz.work.worldcamp.dtos.createDto.FacultyCommentCreateDto;
import uz.work.worldcamp.dtos.responceDto.FacultyCommentResponseDto;
import uz.work.worldcamp.service.facultyCommentService.FacultyCommentService;

import java.util.List;
import java.util.Locale;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/facultyComment")
public class FacultyCommentController {
    private final FacultyCommentService facultyCommentService;

    @Operation(
            description = "Create a new comment",
            method = "POST method is supported",
            security = @SecurityRequirement(name = "pre authorize", scopes = {"ADMIN"})
    )
    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/")
    public ResponseEntity<FacultyCommentResponseDto> createComment(@Valid @RequestBody FacultyCommentCreateDto facultyComment, @RequestParam Locale locale) {
        FacultyCommentResponseDto createdComment = facultyCommentService.createComment(facultyComment, locale);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdComment);
    }

    @Operation(
            description = "Get all first-level comments by faculty ID",
            method = "GET method is supported",
            security = @SecurityRequirement(name = "pre authorize", scopes = {"USER"})
    )
    @PermitAll
    @GetMapping("/faculty/{facultyId}/comments")
    public ResponseEntity<List<FacultyCommentResponseDto>> getAllFirstCommentsByFacultyId(
            @PathVariable UUID facultyId, @RequestParam Locale locale) {
        List<FacultyCommentResponseDto> comments = facultyCommentService.getAllFirstCommentsByFacultyId(facultyId, locale);
        return ResponseEntity.ok(comments);
    }

    @Operation(
            description = "Get comment by ID",
            method = "GET method is supported",
            security = @SecurityRequirement(name = "pre authorize", scopes = {"USER"})
    )
    @PermitAll
    @GetMapping("/{id}")
    public ResponseEntity<FacultyCommentResponseDto> getCommentById(@PathVariable UUID id, @RequestParam Locale locale) {
        FacultyCommentResponseDto comment = facultyCommentService.getCommentById(id, locale);
        return ResponseEntity.ok(comment);
    }

    @Operation(
            description = "Update comment",
            method = "PUT method is supported",
            security = @SecurityRequirement(name = "pre authorize", scopes = {"USER"})
    )
    @PreAuthorize("hasAuthority('USER')")
    @PutMapping("/{id}")
    public ResponseEntity<FacultyCommentResponseDto> updateComment(
            @PathVariable UUID id, @RequestParam UUID userId, @RequestParam String newComment, @RequestParam Locale locale) {
        FacultyCommentResponseDto updatedComment = facultyCommentService.updateComment(id, userId, newComment, locale);
        return ResponseEntity.ok(updatedComment);
    }

    @Operation(
            description = "Get sub-comments by parent ID",
            method = "GET method is supported",
            security = @SecurityRequirement(name = "pre authorize", scopes = {"USER"})
    )
    @PermitAll
    @GetMapping("/sub-comments/{parentId}")
    public ResponseEntity<List<FacultyCommentResponseDto>> subComments(@PathVariable UUID parentId, @RequestParam Locale locale) {
        List<FacultyCommentResponseDto> subComments = facultyCommentService.subComments(parentId, locale);
        return ResponseEntity.ok(subComments);
    }

    @Operation(
            description = "Deactivate comment",
            method = "DELETE method is supported",
            security = @SecurityRequirement(name = "pre authorize", scopes = {"USER"})
    )
    @PreAuthorize("hasAuthority('USER')")
    @DeleteMapping("/{commentId}/deactivate")
    public ResponseEntity<String> deActivate(@PathVariable UUID commentId, @RequestParam UUID userId) {
        String response = facultyCommentService.deActivate(commentId, userId);
        return ResponseEntity.ok(response);
    }

    @Operation(
            description = "Delete comment by admin",
            method = "DELETE method is supported",
            security = @SecurityRequirement(name = "pre authorize", scopes = {"ADMIN"})
    )
    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping("/{commentId}")
    public ResponseEntity<String> deleteByAdmin(@PathVariable UUID commentId) {
        String response = facultyCommentService.deleteByAdmin(commentId);
        return ResponseEntity.ok(response);
    }
}
