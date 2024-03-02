package jun.invitation.domain.product.invitation.domain.Gallery.dto;

import jun.invitation.domain.product.invitation.domain.Gallery.Gallery;
import lombok.Data;

@Data
public class GalleryDto {

    private Long priority;

    private String originFileName;

    private String imageUrl;

    public GalleryDto(Gallery gallery) {
        this.priority = gallery.getPriority();
        this.originFileName = gallery.getOriginFileName();
        this.imageUrl = gallery.getImageUrl();
    }
}