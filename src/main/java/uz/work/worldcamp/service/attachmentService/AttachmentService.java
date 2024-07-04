package uz.work.worldcamp.service.attachmentService;

import org.springframework.web.multipart.MultipartFile;
import uz.work.worldcamp.entities.Attachment;

import java.io.IOException;
import java.util.UUID;

public interface AttachmentService {
    UUID uploadImage(MultipartFile file) throws IOException;
    Attachment downloadImage(UUID fileId);
}
