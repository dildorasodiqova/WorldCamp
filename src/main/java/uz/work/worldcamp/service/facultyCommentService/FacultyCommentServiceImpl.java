package uz.work.worldcamp.service.facultyCommentService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import uz.work.worldcamp.dtos.createDto.FacultyCommentCreateDto;
import uz.work.worldcamp.dtos.createDto.FacultyCreateDTO;
import uz.work.worldcamp.dtos.responceDto.FacultyCommentResponseDto;
import uz.work.worldcamp.dtos.responceDto.FacultyShortInfo;
import uz.work.worldcamp.dtos.responceDto.UserShortInfo;
import uz.work.worldcamp.entities.FacultyCommentEntity;
import uz.work.worldcamp.exception.DataNotFoundException;
import uz.work.worldcamp.repositories.FacultyCommentRepository;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class FacultyCommentServiceImpl implements FacultyCommentService{
    private final FacultyCommentRepository facultyCommentRepository;

    @Override
    public FacultyCommentResponseDto createComment(FacultyCommentCreateDto facultyComment, Locale locale) {
        log.info("Creating comment for facultyId: {} by userId: {}", facultyComment.getFacultyId(), facultyComment.getUserId());
        FacultyCommentEntity save = facultyCommentRepository.save(new FacultyCommentEntity(
                facultyComment.getUserId(),
                facultyComment.getFacultyId(),
                facultyComment.getParentId(),
                facultyComment.getComment()));
        log.info("Comment created successfully with ID: {}", save.getId());
        return mapToResponse(save, locale);
    }

    @Override
    public List<FacultyCommentResponseDto> getAllFirstCommentsByFacultyId(UUID facultyId, Locale locale) {
        log.info("Fetching all first-level comments for facultyId: {}", facultyId);
        List<FacultyCommentEntity> all = facultyCommentRepository.findAllByFacultyIdAndIsActiveTrue(facultyId);
        return mapToResponse(all, locale);
    }

    @Override
    public FacultyCommentResponseDto getCommentById(UUID id, Locale locale) {
        log.info("Fetching comment with ID: {}", id);
        FacultyCommentEntity comment = facultyCommentRepository.findById(id).orElseThrow(() -> new DataNotFoundException("Comment not found."));
        log.info("Comment found with ID: {}", id);
        return mapToResponse(comment, locale);
    }

    @Override
    public String deActivate(UUID commentId, UUID userId) {
        log.info("Deactivating comment with ID: {} by userId: {}", commentId, userId);
        facultyCommentRepository.deactivateComments(commentId, userId);
        facultyCommentRepository.deactivateSubComments(commentId);
        log.info("Comment and its sub-comments deactivated successfully with ID: {}", commentId);
        return "Comment deleted successfully.";
    }

    @Override
    public String deleteByAdmin(UUID commentId) {
        log.info("Admin deleting comment with ID: {}", commentId);
        facultyCommentRepository.deactivateByAdmin(commentId);
        facultyCommentRepository.deactivateSubComments(commentId);
        log.info("Comment and its sub-comments deactivated by admin with ID: {}", commentId);
        return "Successfully deleted";
    }

    @Override
    public FacultyCommentResponseDto updateComment(UUID id, UUID currentUser, String newComment, Locale locale) {
        log.info("Updating comment with ID: {} by userId: {}", id, currentUser);
        if (facultyCommentRepository.updateComment(id, currentUser, newComment) == 0) {
            log.warn("Comment not found with ID: {}", id);
            throw new DataNotFoundException("Comment not found");
        }
        FacultyCommentEntity comment = facultyCommentRepository.findById(id).orElseThrow(() -> new DataNotFoundException("Comment not found"));
        log.info("Comment updated successfully with ID: {}", comment.getId());
        return mapToResponse(comment, locale);
    }

    @Override
    public List<FacultyCommentResponseDto> subComments(UUID parentId, Locale locale) {
        log.info("Fetching sub-comments for parentId: {}", parentId);
        List<FacultyCommentEntity> all = facultyCommentRepository.findAllByParentId(parentId);
        return mapToResponse(all, locale);
    }


    private FacultyCommentResponseDto mapToResponse(FacultyCommentEntity save, Locale locale) {
        String facultyName = switch (locale.getLanguage()) {
            case "uz" -> save.getFacultyEntity().getNameUz();
            case "ru" -> save.getFacultyEntity().getNameRus();
            default -> save.getFacultyEntity().getNameEng();
        };

        FacultyShortInfo shortInfo = new FacultyShortInfo(
                save.getFacultyEntity().getId(),
                facultyName,
                save.getFacultyEntity().getCreatedDate()
        );

        return new FacultyCommentResponseDto(
                save.getId(),
                new UserShortInfo(
                        save.getUser().getId(),
                        save.getUser().getFullName(),
                        save.getCreatedDate()
                ),
                shortInfo,
                save.getParentId(),
                save.getComment(),
                save.getCreatedDate(),
                save.getUpdateDate()
        );

    }

    private List<FacultyCommentResponseDto> mapToResponse(List<FacultyCommentEntity> save, Locale locale) {

        return save.stream()
                .map(saves -> {
                    String facultyName;
                    switch (locale.getLanguage()) {
                        case "uz":
                            facultyName = saves.getFacultyEntity().getNameUz();
                            break;
                        case "ru":
                            facultyName = saves.getFacultyEntity().getNameRus();
                            break;
                        default:
                            facultyName = saves.getFacultyEntity().getNameEng();
                            break;
                    }

                    FacultyShortInfo shortInfo = new FacultyShortInfo(
                            saves.getFacultyEntity().getId(),
                            facultyName,
                            saves.getFacultyEntity().getCreatedDate()
                    );

                    return new FacultyCommentResponseDto(
                            saves.getId(),
                            new UserShortInfo(
                                    saves.getUser().getId(),
                                    saves.getUser().getFullName(),
                                    saves.getCreatedDate()
                            ),
                            shortInfo,
                            saves.getParentId(),
                            saves.getComment(),
                            saves.getCreatedDate(),
                            saves.getUpdateDate()
                    );
                })
                .collect(Collectors.toList());

    }




}
