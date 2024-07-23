package jun.invitation.mock;

import jun.invitation.aws.s3.ImageUploadKey;
import jun.invitation.aws.s3.ImageUploader;
import jun.invitation.global.service.port.UuidHolder;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

import static jun.invitation.aws.s3.ImageUploadKey.*;

@RequiredArgsConstructor
@Builder
@Slf4j
public class FakeImageUploader implements ImageUploader {

    private final TestUuidHolder uuidHolder;
    private final Set<String> store = new HashSet<>();

    @Override
    public Map<ImageUploadKey, String> upload(MultipartFile multipartFile) {

        String fileName = createFileName(multipartFile.getOriginalFilename());

        HashMap<ImageUploadKey, String> map = new HashMap<>();

        log.info(fileName);
        log.info(multipartFile.getOriginalFilename());
        log.info("https://test.com/"+fileName);

        map.put(STORE_FILE_NAME, fileName);
        map.put(ORIGIN_FILE_NAME, multipartFile.getOriginalFilename());
        map.put(IMAGE_URL, "https://test.com/"+fileName);

        store.add(fileName);

        return map;
    }

    @Override
    public void delete(String fileName) {
        store.remove(fileName);
    }

    @Override
    public CompletableFuture<Map<ImageUploadKey, String>> uploadAsync(MultipartFile multipartFile) {
        CompletableFuture<Map<ImageUploadKey, String>> future = new CompletableFuture<>();

        future.complete(this.upload(multipartFile));

        return future;
    }

    public boolean hasImg(String fileName) {
        return store.contains(fileName);
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

    public void changeUuid(String updateUuid) {
        uuidHolder.changeUuid(updateUuid);
    }
}
