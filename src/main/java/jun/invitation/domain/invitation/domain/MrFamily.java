package jun.invitation.domain.invitation.domain;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Embeddable
@NoArgsConstructor
@Data @AllArgsConstructor
public class MrFamily {

    private String mrName;
    private String mrPhone;
    private String mrAccount;

    private String mrFaName;
    private String mrFaPhone;
    private String mrFaAccount;

    private String mrMaName;
    private String mrMaPhone;
    private String mrMaAccount;

}
