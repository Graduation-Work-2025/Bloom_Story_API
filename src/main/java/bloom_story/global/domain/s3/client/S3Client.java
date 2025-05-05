package bloom_story.global.domain.s3.client;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.Clock;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.PutObjectRequest;

import bloom_story.global.domain.s3.dto.UploadUrlResponse;
import lombok.extern.slf4j.Slf4j;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;
import software.amazon.awssdk.services.s3.presigner.model.PresignedPutObjectRequest;
import software.amazon.awssdk.services.s3.presigner.model.PutObjectPresignRequest;

@Slf4j
@Service
public class S3Client {

    private static final int URL_EXPIRATION_MINUTE = 10;

    private final AmazonS3 amazonS3;
    private final S3Presigner.Builder presignerBuilder;
    private final String bucketName;
    private final Clock clock;

    public S3Client(
        @Value("${cloud.aws.s3.bucket}") String bucketName,
        S3Presigner.Builder presignerBuilder,
        AmazonS3 amazonS3,
        Clock clock
    ) {
        this.bucketName = bucketName;
        this.presignerBuilder = presignerBuilder;
        this.amazonS3 = amazonS3;
        this.clock = clock;
    }

    // MultipartFile을 전달받아 File로 전환한 후 S3에 업로드
    public String upload(MultipartFile multipartFile, String dirName) throws IOException {
        // dirName의 디렉토리가 S3 Bucket 내부에 생성됨
        File uploadFile = convert(multipartFile)
            .orElseThrow(() -> new IllegalArgumentException("MultipartFile -> File 전환 실패"));
        return upload(uploadFile, dirName);
    }

    private String upload(File uploadFile, String dirName) {
        String fileName = dirName + "/" + uploadFile.getName();
        String uploadImageUrl = putS3(uploadFile, fileName);

        removeNewFile(uploadFile);  // convert()함수로 인해서 로컬에 생성된 File 삭제 (MultipartFile -> File 전환 하며 로컬에 파일 생성됨)

        return uploadImageUrl;      // 업로드된 파일의 S3 URL 주소 반환
    }

    public UploadUrlResponse getUploadUrl(String uploadFilePath) {

        try (S3Presigner presigner = presignerBuilder.build()) {
            PutObjectPresignRequest presignRequest = PutObjectPresignRequest.builder()
                .signatureDuration(Duration.ofMinutes(URL_EXPIRATION_MINUTE))
                .putObjectRequest(builder -> builder
                    .bucket(bucketName)
                    .key(uploadFilePath)
                    .build()
                ).build();

            PresignedPutObjectRequest presignedRequest = presigner.presignPutObject(presignRequest);
            return new UploadUrlResponse(
                presignedRequest.url().toExternalForm(),
                uploadFilePath,
                LocalDateTime.now(clock).plusMinutes(URL_EXPIRATION_MINUTE)
            );
        }
    }

    private String putS3(File uploadFile, String fileName) {
        amazonS3.putObject(
            new PutObjectRequest(bucketName, fileName, uploadFile)
        );
        return amazonS3.getUrl(bucketName, fileName).toString();
    }

    private void removeNewFile(File targetFile) {
        if (targetFile.delete()) {
            log.info("파일이 삭제되었습니다.");
        } else {
            log.info("파일이 삭제되지 못했습니다.");
        }
    }

    private Optional<File> convert(MultipartFile file) throws IOException {
        String fileName = UUID.randomUUID() + "_" + file.getOriginalFilename();
        File tempFile = File.createTempFile("upload-", fileName);
        try (FileOutputStream fos = new FileOutputStream(tempFile)) {
            fos.write(file.getBytes());
        }
        return Optional.of(tempFile);
    }

    // private Optional<File> convert(MultipartFile file) throws IOException {
    //     File convertFile = new File(file.getOriginalFilename()); // 업로드한 파일의 이름
    //     if (convertFile.createNewFile()) {
    //         try (FileOutputStream fos = new FileOutputStream(convertFile)) {
    //             fos.write(file.getBytes());
    //         }
    //         return Optional.of(convertFile);
    //     }
    //     return Optional.empty();
    // }
}
