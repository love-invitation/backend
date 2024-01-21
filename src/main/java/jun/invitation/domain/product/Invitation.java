package jun.invitation.domain.product;

import jakarta.persistence.*;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@DiscriminatorValue("Invitation")
@NoArgsConstructor
public class Invitation extends Product{

    @Id @GeneratedValue
    @Column(name = "invitation_id")
    private Long id;

    /*
    private 이미지 main_picture, gallery_picture
     */

    private String title;
    private String contents;
    private LocalDateTime wedding_date;
    private String wedding_info;
    private String parents_info;
    private String account_info;

    public Invitation(String title, String contents, LocalDateTime wedding_date,
                      String wedding_info, String parents_info, String account_info) {

        this.title = title;
        this.contents = contents;
        this.wedding_date = wedding_date;
        this.wedding_info = wedding_info;
        this.parents_info = parents_info;
        this.account_info = account_info;
    }
}
