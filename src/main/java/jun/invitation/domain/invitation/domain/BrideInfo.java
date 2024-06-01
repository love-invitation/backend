package jun.invitation.domain.invitation.domain;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Embeddable
@NoArgsConstructor
@Data @AllArgsConstructor
public class BrideInfo {

    private String brideName;
    private String bridePhone;
    private String brideBankName;
    private String brideAccount;

    private String brideFatherName;
    private String brideFatherPhone;
    private String brideFatherBankName;
    private String brideFatherAccount;

    private String brideMotherName;
    private String brideMotherPhone;
    private String brideMotherBankName;
    private String brideMotherAccount;

    private String brideRelation;
}
