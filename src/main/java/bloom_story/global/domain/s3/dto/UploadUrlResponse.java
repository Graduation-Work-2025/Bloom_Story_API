package bloom_story.global.domain.s3.dto;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import io.swagger.v3.oas.annotations.media.Schema;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record UploadUrlResponse(
    @Schema(description = "파일을 업로드할 수 있는 url",
        example = "https://bucketname.ap-northeast-2.amazonaws.com/upload/domain/2000/00/00/d4cb13df-cf57-...")
    String preSignedUrl,

    @Schema(
        description = "첨부 파일 URL",
        example = "https://bloom-story-s3.s3.ap-northeast-2.amazonaws.com/upload/1-bpokeknjfijdo.png"
    )
    String fileUrl,

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Schema(description = "presigned url 만료 일자", example = "2023-01-01 12:34:56")
    LocalDateTime expirationDate
) {

}

