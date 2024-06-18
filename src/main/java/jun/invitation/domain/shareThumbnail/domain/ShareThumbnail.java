package jun.invitation.domain.shareThumbnail.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static jakarta.persistence.GenerationType.*;
import static lombok.AccessLevel.*;

@Entity
@Getter
@NoArgsConstructor(access = PROTECTED)
public class ShareThumbnail {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    private String title;
    private String contents;

    private String imageUrl;
    private String imageOriginName;
    private String imageStoreFileName;

    public ShareThumbnail(String title, String contents, String imageUrl,
                          String imageOriginName, String imageStoreFileName) {
        this.title = title;
        this.contents = contents;
        this.imageUrl = imageUrl;
        this.imageOriginName = imageOriginName;
        this.imageStoreFileName = imageStoreFileName;
    }
}
