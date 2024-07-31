package jun.invitation.domain.shareThumbnail.domain;

import jakarta.persistence.*;
import jun.invitation.image.domain.Image;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static jakarta.persistence.FetchType.*;
import static jakarta.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

@Entity
@Getter
@NoArgsConstructor
public class ShareThumbnail {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    private String title;
    private String contents;

    @OneToOne(fetch = LAZY)
    @JoinColumn(name = "image_id")
    private Image image;

    @Builder
    public ShareThumbnail(String title, String contents, Image image) {
        this.title = title;
        this.contents = contents;
        this.image = image;
    }

    public void updateText(String title, String contents) {
        this.title = title;
        this.contents = contents;
    }

    public void registerImage(Image image) {
        this.image = image;
    }

    @Override
    public String toString() {
        return "ShareThumbnail{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", contents='" + contents + '\'' +
                ", image=" + image +
                '}';
    }
}
