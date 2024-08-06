package jun.invitation.domain.image.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static jakarta.persistence.GenerationType.*;
import static lombok.AccessLevel.*;

@Entity
@Getter
@NoArgsConstructor(access = PROTECTED)
public class Image {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "image_id")
    private Long id;

    private String originName;
    private String storeFileName;
    private String url;

    @Builder
    public Image(String originName, String storeFileName, String url) {
        this.originName = originName;
        this.storeFileName = storeFileName;
        this.url = url;
    }

    @Override
    public String toString() {
        return "Image{" +
                "id=" + id +
                ", originName='" + originName + '\'' +
                ", storeFileName='" + storeFileName + '\'' +
                ", url='" + url + '\'' +
                '}';
    }
}
