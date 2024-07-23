package jun.invitation.domain.gallery.dto;

import jun.invitation.domain.gallery.Gallery;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@RequiredArgsConstructor
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
