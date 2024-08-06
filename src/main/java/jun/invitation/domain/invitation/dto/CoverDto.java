package jun.invitation.domain.invitation.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jun.invitation.domain.invitation.domain.Invitation;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.util.Optional;

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

        Optional.ofNullable(invitation.getMainImage())
                .ifPresent(mainImage -> {
                    this.imageUrl = mainImage.getUrl();
                    this.imageOriginName = mainImage.getOriginName();
                    this.imageStoreFileName = mainImage.getStoreFileName();
                });

        Optional.ofNullable(invitation.getReservation())
                .ifPresent(reservation -> {
                    this.weddingDate = reservation.getBooking().getDate();
                    this.detail = reservation.getPlace().getDetail();
                });

        Optional.ofNullable(invitation.getGroomInfo())
                .ifPresent(groomInfo -> this.groomName = groomInfo.getName());

        Optional.ofNullable(invitation.getBrideInfo())
                .ifPresent(brideInfo -> this.brideName = brideInfo.getName());

        this.coverContents = invitation.getCoverContents();

    }
}
