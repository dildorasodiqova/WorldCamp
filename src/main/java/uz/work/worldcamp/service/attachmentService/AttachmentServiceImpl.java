package uz.work.worldcamp.service.attachmentService;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import uz.work.worldcamp.entities.Attachment;
import uz.work.worldcamp.exception.DataNotFoundException;
import uz.work.worldcamp.repositories.AttachmentRepository;

import java.io.IOException;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AttachmentServiceImpl implements AttachmentService {

    private final AttachmentRepository attachmentRepository;

    @Override
    public UUID uploadImage(MultipartFile file) throws IOException {
        Attachment attachment = attachmentRepository.save(Attachment.builder()
                .name(file.getOriginalFilename())
                .contentType(file.getContentType())
                .size(file.getSize())
                .bytes(file.getBytes()).build());

        return attachment.getId();
    }

    @Override
    public Attachment downloadImage(UUID fileId) throws RuntimeException {
        return attachmentRepository.findById(fileId).orElseThrow(()->new DataNotFoundException("Attachment not found"));
    }
}
