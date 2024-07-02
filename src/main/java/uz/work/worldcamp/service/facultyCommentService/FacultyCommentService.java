package uz.work.worldcamp.service.facultyCommentService;

import uz.work.worldcamp.dtos.createDto.FacultyCommentCreateDto;
import uz.work.worldcamp.dtos.responceDto.FacultyCommentResponseDto;
import uz.work.worldcamp.entities.FacultyCommentEntity;

import java.util.List;
import java.util.Locale;
import java.util.UUID;

public interface FacultyCommentService {

    FacultyCommentResponseDto createComment(FacultyCommentCreateDto facultyComment, Locale locale);
    List<FacultyCommentResponseDto> getAllFirstCommentsByFacultyId(UUID facultyId, Locale locale);
    FacultyCommentResponseDto getCommentById(UUID id, Locale locale);
    FacultyCommentResponseDto updateComment(UUID id,UUID userId, String newComment, Locale locale);
    List<FacultyCommentResponseDto> subComments(UUID parentId, Locale locale);
    String deActivate(UUID commentId, UUID userId);
    String deleteByAdmin(UUID commentId);

}
