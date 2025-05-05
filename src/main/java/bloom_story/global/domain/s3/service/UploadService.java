package bloom_story.global.domain.s3.service;

import java.io.IOException;
import java.time.Clock;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.StringJoiner;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import bloom_story.global.domain.s3.client.S3Client;
import bloom_story.global.domain.s3.dto.UploadUrlRequest;
import bloom_story.global.domain.s3.dto.UploadUrlResponse;
import lombok.RequiredArgsConstructor;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UploadService {

    private final S3Client s3Client;
    private final Clock clock;

    public UploadUrlResponse getPresignedUrl(UploadUrlRequest request) {
        var filePath = generateFilePath(request.fileName());
        return s3Client.getUploadUrl(filePath);
    }

    private String generateFilePath(String fileNameExt) {
        var now = LocalDateTime.now(clock);
        StringJoiner uploadPrefix = new StringJoiner("/");
        String[] parts = fileNameExt.split("\\.");
        String fileExt = parts[parts.length - 1];
        String fileName = String.join("", Arrays.copyOf(parts, parts.length - 1));
        uploadPrefix.add("upload")
            .add(fileName + "-" + UUID.randomUUID());
        return uploadPrefix + "." + fileExt;
    }

    public void uploadFile(MultipartFile file) {
        String fileName;
        if (file != null) {
            try {
                fileName = s3Client.upload(file, "images"); // S3 버킷의 images 디렉토리 안에 저장됨
                System.out.println("fileName = " + fileName);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
