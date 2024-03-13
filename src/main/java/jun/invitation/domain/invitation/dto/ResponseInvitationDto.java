package jun.invitation.domain.invitation.dto;

import jun.invitation.domain.invitation.domain.*;
import jun.invitation.domain.gallery.Gallery;
import jun.invitation.domain.gallery.dto.GalleryDto;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data @NoArgsConstructor
public class ResponseInvitationDto {

    private Long id;

    private String mainImageUrl;
    private String mainImageOriginName;
    private String mainImageStoreFileName;

    private String title;
    private String contents;

    private Wedding wedding;

    private List<GalleryDto> gallery = new ArrayList<>();

    private List<TransportDto> transport = new ArrayList<>();

    private Theme theme;

    private MrsFamily mrsFamily;

    private MrFamily mrFamily;

    public ResponseInvitationDto(Invitation invitation) {
        this.id = invitation.getId();
        this.mainImageUrl = invitation.getMainImageUrl();
        this.mainImageOriginName = invitation.getMainImageOriginName();
        this.mainImageStoreFileName = invitation.getMainImageStoreFileName();
        this.title = invitation.getTitle();
        this.contents = invitation.getContents();
        this.wedding = invitation.getWedding();

        for (Gallery g : invitation.getGallery()) {
            gallery.add(new GalleryDto(g));
        }

        for (Transport t : invitation.getTransport()) {
            transport.add(new TransportDto(t));
        }

        this.theme = invitation.getTheme();
        this.mrsFamily = invitation.getMrsFamily();
        this.mrFamily = invitation.getMrFamily();
    }
}
