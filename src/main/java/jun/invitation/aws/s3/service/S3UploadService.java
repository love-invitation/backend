package jun.invitation.aws.s3.service;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import jun.invitation.aws.s3.ImageUploadKey;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

import static jun.invitation.aws.s3.ImageUploadKey.*;

@RequiredArgsConstructor
@Service @Slf4j
public class S3UploadService {

    private final AmazonS3 amazonS3;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    @Async("imageUploadExecutor")
    @Transactional
    public CompletableFuture<Map<ImageUploadKey,String>> saveFileAsync(MultipartFile multipartFile) {

        CompletableFuture<Map<ImageUploadKey, String>> future = new CompletableFuture<>();

        future.complete(this.saveFile(multipartFile));

        return future;
    }

    @Transactional
    public Map<ImageUploadKey,String> saveFile(MultipartFile multipartFile) {

        String fileName = createFileName(multipartFile.getOriginalFilename());

        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentLength(multipartFile.getSize());
        metadata.setContentType(multipartFile.getContentType());

        try {
            amazonS3.putObject(bucket, fileName, multipartFile.getInputStream(), metadata);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        HashMap<ImageUploadKey, String> map = new HashMap<>();

        map.put(STORE_FILE_NAME, fileName);
        map.put(ORIGIN_FILE_NAME, multipartFile.getOriginalFilename());
        map.put(IMAGE_URL, amazonS3.getUrl(bucket, fileName).toString());

        return map;
    }

    @Transactional
    public void delete(String fileName) {
        try {
            amazonS3.deleteObject(bucket, fileName);
        } catch (AmazonServiceException e ) {
            log.error(e.toString());
        }
    }

    private String createFileName(String fileName) {
        return UUID.randomUUID().toString().concat(getFileExtension(fileName));
    }

    private String getFileExtension(String fileName) {
        try {
            return fileName.substring(fileName.lastIndexOf("."));
        } catch (StringIndexOutOfBoundsException se) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "잘못된 형식의 파일(" + fileName + ") 입니다.");
        }
    }
}
