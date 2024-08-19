package jun.invitation.domain.shareThumbnail.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import static lombok.AccessLevel.PROTECTED;

@Data
@AllArgsConstructor
@NoArgsConstructor(access = PROTECTED)
@JsonIgnoreProperties(ignoreUnknown = true)
public class ShareThumbnailDto {
    private String title;
    private String contents;
}
