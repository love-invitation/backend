package jun.invitation.domain.product.invitation.domain;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Embeddable
@NoArgsConstructor
@Data @AllArgsConstructor
public class MrsFamily {

    private String mrsName;
    private String mrsPhone;
    private String mrsAccount;

    private String mrsFaName;
    private String mrsFaPhone;
    private String mrsFaAccount;

    private String mrsMaName;
    private String mrsMaPhone;
    private String mrsMaAccount;

}
