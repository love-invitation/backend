package jun.invitation.domain.invitation.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jun.invitation.domain.invitation.domain.Invitation;
import jun.invitation.domain.invitation.domain.embedded.FamilyInfo;
import jun.invitation.domain.invitation.domain.embedded.Wedding;
import jun.invitation.image.domain.Image;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Data
@RequiredArgsConstructor
public class CoverDto {

    private Integer priority;

    private Long templateId;

    private String imageUrl;
    private String imageOriginName;
    private String imageStoreFileName;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime weddingDate;

    private String detail;

    private String groomName;
    private String brideName;

    private String coverContents;

    public CoverDto(Invitation invitation) {

        this.priority = 0;
        this.templateId = invitation.getProductInfo().getId();

        Image mainImage = invitation.getMainImage();
        if (mainImage != null){
            this.imageUrl = mainImage.getUrl();
            this.imageOriginName = mainImage.getOriginName();
            this.imageStoreFileName = mainImage.getStoreFileName();
        }


        Wedding wedding = invitation.getWedding();
        if (wedding != null) {
            this.weddingDate = wedding.getDate();
            this.detail = wedding.getDetail();
        }

        FamilyInfo groomInfo = invitation.getGroomInfo();
        if (groomInfo != null) {
            this.groomName = groomInfo.getName();
        }

        FamilyInfo brideInfo = invitation.getBrideInfo();
        if ( brideInfo != null) {
            this.brideName = brideInfo.getName();
        }

        this.coverContents = invitation.getCoverContents();

    }
}
