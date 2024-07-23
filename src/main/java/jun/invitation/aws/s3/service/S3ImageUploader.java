package jun.invitation.aws.s3.service;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import jun.invitation.aws.s3.ImageUploadKey;
import jun.invitation.aws.s3.ImageUploader;
import jun.invitation.global.service.port.UuidHolder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

import static jun.invitation.aws.s3.ImageUploadKey.*;

@RequiredArgsConstructor
@Slf4j
@Profile("default")
@Service
public class S3ImageUploader implements ImageUploader {

    private final AmazonS3 amazonS3;
    private final UuidHolder uuidHolder;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    @Transactional
    @Override
    public Map<ImageUploadKey,String> upload(MultipartFile multipartFile) {

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
    @Override
    public void delete(String fileName) {
        try {
            amazonS3.deleteObject(bucket, fileName);
        } catch (AmazonServiceException e ) {
            log.error(e.toString());
        }
    }

    @Async("imageUploadExecutor")
    @Transactional
    @Override
    public CompletableFuture<Map<ImageUploadKey,String>> uploadAsync(MultipartFile multipartFile) {

        CompletableFuture<Map<ImageUploadKey, String>> future = new CompletableFuture<>();

        future.complete(this.upload(multipartFile));

        return future;
    }

    private String createFileName(String fileName) {
        return uuidHolder.random().concat(getFileExtension(fileName));
    }

    private String getFileExtension(String fileName) {
        try {
            return fileName.substring(fileName.lastIndexOf("."));
        } catch (StringIndexOutOfBoundsException se) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "잘못된 형식의 파일(" + fileName + ") 입니다.");
        }
    }
}

