package jun.invitation.domain.invitation.domain;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Embeddable
@NoArgsConstructor
@Data @AllArgsConstructor
public class GroomInfo {

    private String groomName;
    private String groomPhone;
    private String groomBankName;
    private String groomAccount;

    private String groomFatherName;
    private String groomFatherBankName;
    private String groomFatherPhone;
    private String groomFatherAccount;

    private String groomMotherName;
    private String groomMotherBankName;
    private String groomMotherPhone;
    private String groomMotherAccount;

    private String groomRelation;

}
