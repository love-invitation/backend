package jun.invitation.domain.shareThumbnail.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import static lombok.AccessLevel.*;

@Data
@AllArgsConstructor
@NoArgsConstructor(access = PROTECTED)
public class ShareThumbnailDto {
    private String shareTitle;
    private String shareContents;
}
