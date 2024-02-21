package jun.invitation.domain.product.invitation.domain;

import jakarta.persistence.*;
import jun.invitation.domain.product.domain.Product;
import jun.invitation.domain.product.invitation.domain.Gallery.Gallery;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity @Getter
@DiscriminatorValue("Invitation")
@NoArgsConstructor(access = AccessLevel.PROTECTED) @ToString
public class Invitation extends Product {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "invitation_id")
    private Long id;

    private String mainImageUrl;
    private String title;
    private String contents;

    @Embedded
    private Wedding wedding;

    @OneToMany(mappedBy = "invitation" ,cascade = CascadeType.PERSIST)
    private List<Gallery> gallery = new ArrayList<>();

    @OneToMany(mappedBy = "invitation", cascade = CascadeType.PERSIST)
    private List<Transport> transport = new ArrayList<>();

    @Embedded
    private Theme theme;

    @Embedded
    private MrsFamily mrsFamily;

    @Embedded
    private MrFamily mrFamily;

    public void registerMainImage(String mainImageUrl) {
        this.mainImageUrl = mainImageUrl;
    }

    @Builder
    public Invitation(String mainImageUrl, String title, String contents, Wedding wedding
            , Theme theme, MrsFamily mrsFamily, MrFamily mrFamily) {
        this.mainImageUrl = mainImageUrl;
        this.title = title;
        this.contents = contents;
        this.wedding = wedding;
        this.theme = theme;
        this.mrsFamily = mrsFamily;
        this.mrFamily = mrFamily;
    }

}
