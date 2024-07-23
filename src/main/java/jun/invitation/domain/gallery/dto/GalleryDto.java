package jun.invitation.domain.gallery.dto;

import jun.invitation.domain.gallery.Gallery;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class GalleryDto {

    private Long priority;

    private String originFileName;

    private String imageUrl;

    public GalleryDto(Gallery gallery) {
        this.priority = gallery.getPriority();
        this.originFileName = gallery.getImage().getOriginName();
        this.imageUrl = gallery.getImage().getUrl();
    }
}
