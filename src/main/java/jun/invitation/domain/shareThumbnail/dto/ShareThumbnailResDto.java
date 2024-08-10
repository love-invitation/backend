package jun.invitation.domain.shareThumbnail.dto;

import jun.invitation.domain.shareThumbnail.domain.ShareThumbnail;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Optional;

import static lombok.AccessLevel.PROTECTED;

@Data
@NoArgsConstructor(access = PROTECTED)
@AllArgsConstructor
public class ShareThumbnailResDto {
    private String title;
    private String contents;
    private String imageUrl;

    public ShareThumbnailResDto(ShareThumbnail shareThumbnail) {
        Optional.ofNullable(shareThumbnail)
                .ifPresent(s -> {
                            this.title = s.getTitle();
                            this.contents = s.getContents();
                            this.imageUrl = s.getImage().getUrl();
                        }
                );
    }
}
