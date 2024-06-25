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

    // todo : person : name, deceased 로 묶기
    private String fatherName;
    private Boolean fatherIsCondolences;

    private String motherName;
    private Boolean motherIsCondolences;

    private String relation;
}