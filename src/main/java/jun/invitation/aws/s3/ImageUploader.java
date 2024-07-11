package jun.invitation.aws.s3;

import org.springframework.web.multipart.MultipartFile;

import java.util.Map;
import java.util.concurrent.CompletableFuture;

public interface ImageUploader {
    Map<ImageUploadKey, String> upload(MultipartFile multipartFile);

    void delete(String fileName);

    CompletableFuture<Map<ImageUploadKey, String>> uploadAsync(MultipartFile multipartFile);
}
