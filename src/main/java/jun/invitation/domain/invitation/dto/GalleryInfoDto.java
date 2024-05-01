package jun.invitation.domain.invitation.dto;

import jun.invitation.domain.gallery.Gallery;
import jun.invitation.domain.gallery.dto.GalleryDto;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class GalleryInfoDto {
    private Integer priority;
    private List<GalleryDto> galleries = new ArrayList<>();

    public GalleryInfoDto(List<Gallery> galleries, Integer priority) {
        this.priority = priority;
        toGalleryDto(galleries);
    }

    private void toGalleryDto(List<Gallery> galleries) {
        for (Gallery g : galleries) {
            this.galleries.add(new GalleryDto(g));
        }
    }
}
