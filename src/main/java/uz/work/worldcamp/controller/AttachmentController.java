package uz.work.worldcamp.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import uz.work.worldcamp.entities.Attachment;
import uz.work.worldcamp.service.attachmentService.AttachmentService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/image")
@RequiredArgsConstructor
public class AttachmentController {
    private final AttachmentService attachmentService;

    @PostMapping("/multiple-upload")
    public ResponseEntity<List<UUID>> multipleUpload(@RequestParam("files") MultipartFile[] files) throws IOException {
        List<UUID> fileIdList = new ArrayList<>(files.length);
        for (MultipartFile file : files) {
            UUID uuid = attachmentService.uploadImage(file);
            fileIdList.add(uuid);
        }
        return ResponseEntity.ok(fileIdList);

    }

    @PostMapping("/single-upload")
    public ResponseEntity<UUID> uploadImage(@RequestParam("image") MultipartFile file) throws IOException {
        return ResponseEntity.ok(attachmentService.uploadImage(file));
    }

    @GetMapping("/download/{fileId}")
    public ResponseEntity<byte[]> downloadImage(@PathVariable UUID fileId) {
        Attachment attachment = attachmentService.downloadImage(fileId);

        return ResponseEntity.ok(attachment.getBytes());
    }
}