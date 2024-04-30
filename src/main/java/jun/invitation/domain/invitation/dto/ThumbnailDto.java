package jun.invitation.domain.invitation.dto;

import jun.invitation.domain.invitation.domain.Invitation;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ThumbnailDto {

    private Integer priority;
    private String imageUrl;
    private String imageOriginName;
    private String imageStoreFileName;

    private LocalDateTime weddingDate;
    private String detail;

    private String groomName;
    private String brideName;

    public ThumbnailDto(Invitation invitation) {
        this.priority = 0;
        this.imageUrl = invitation.getMainImageUrl();
        this.imageOriginName = invitation.getMainImageOriginName();
        this.imageStoreFileName = invitation.getMainImageStoreFileName();

        this.weddingDate = invitation.getWedding().getDate();
        this.detail = invitation.getWedding().getDetail();

        this.groomName = invitation.getGroomInfo().getGroomName();
        this.brideName = invitation.getBrideInfo().getBrideName();

    }
}
