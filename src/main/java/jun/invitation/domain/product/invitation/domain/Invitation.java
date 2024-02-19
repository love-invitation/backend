package jun.invitation.domain.product.invitation.domain;

import jakarta.persistence.*;
import jun.invitation.domain.product.domain.Product;
import lombok.*;

import java.util.List;

@Entity
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

    @OneToMany(mappedBy = "invitation")
    private List<Gallery> gallery;

    @OneToMany(mappedBy = "invitation")
    private List<Transport> transport;

    @Embedded
    private Theme theme;

    @Embedded
    private MrsFamily mrsFamily;

    @Embedded
    private MrFamily mrFamily;

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
