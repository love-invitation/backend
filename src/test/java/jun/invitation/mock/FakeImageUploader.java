package jun.invitation.mock;

import jun.invitation.aws.s3.ImageUploadKey;
import jun.invitation.aws.s3.ImageUploader;
import jun.invitation.global.service.port.UuidHolder;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

import static jun.invitation.aws.s3.ImageUploadKey.*;

@RequiredArgsConstructor
@Builder
public class FakeImageUploader implements ImageUploader {

    private final UuidHolder uuidHolder;

    @Override
    public Map<ImageUploadKey, String> upload(MultipartFile multipartFile) {

        String fileName = createFileName(multipartFile.getOriginalFilename());

        HashMap<ImageUploadKey, String> map = new HashMap<>();


        map.put(STORE_FILE_NAME, fileName);
        map.put(ORIGIN_FILE_NAME, multipartFile.getOriginalFilename());
        map.put(IMAGE_URL, "https://test/img.png");

        return map;
    }

    @Override
    public void delete(String fileName) {

    }

    @Override
    public CompletableFuture<Map<ImageUploadKey, String>> uploadAsync(MultipartFile multipartFile) {
        return null;
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
