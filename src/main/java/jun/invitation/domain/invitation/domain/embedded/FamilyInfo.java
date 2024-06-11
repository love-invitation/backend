package jun.invitation.domain.invitation.domain.embedded;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Embeddable
@NoArgsConstructor
@Data
@AllArgsConstructor
public class FamilyInfo {


    private String name;
    private String phone;
    private String bankName;
    private String account;

    private String fatherName;
    private String fatherBankName;
    private String fatherPhone;
    private String fatherAccount;

    private String motherName;
    private String motherBankName;
    private String motherPhone;
    private String motherAccount;

    private String relation;
}
