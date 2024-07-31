package jun.invitation.domain.shareThumbnail.dto;

import jun.invitation.domain.shareThumbnail.domain.ShareThumbnail;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import static lombok.AccessLevel.PROTECTED;

@Data
@NoArgsConstructor(access = PROTECTED)
@AllArgsConstructor
public class ShareThumbnailResDto {
    private String title;
    private String contents;
    private String imageUrl;

    public ShareThumbnailResDto(ShareThumbnail shareThumbnail) {
        this.title = shareThumbnail.getTitle();
        this.contents = shareThumbnail.getContents();
        this.imageUrl = shareThumbnail.getImage().getUrl();
    }
}
