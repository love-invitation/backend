package jun.invitation.domain.shareThumbnail.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import static lombok.AccessLevel.*;

@Data
@NoArgsConstructor(access = PROTECTED)
@AllArgsConstructor
public class ShareThumbnailResDto {
    private String shareTitle;
    private String shareContents;
    private String imageUrl;
}
