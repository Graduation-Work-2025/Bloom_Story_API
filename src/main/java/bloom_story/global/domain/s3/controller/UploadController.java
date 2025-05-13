package bloom_story.global.domain.s3.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import bloom_story.global.domain.s3.dto.UploadUrlRequest;
import bloom_story.global.domain.s3.dto.UploadUrlResponse;
import bloom_story.global.domain.s3.service.UploadService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/s3")
@RequiredArgsConstructor
public class UploadController implements UploadApi {

    private final UploadService uploadService;

    @PostMapping("/upload/url")
    public ResponseEntity<UploadUrlResponse> getPresignedUrl(
        @RequestBody @Valid UploadUrlRequest request
    ) {
        var response = uploadService.getPresignedUrl(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping(value = "/upload/file", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public void uploadFile(
        @RequestPart(value = "story_img", required = false)
        MultipartFile multipartFile
    ) {
        uploadService.uploadFile(multipartFile);
    }
}
