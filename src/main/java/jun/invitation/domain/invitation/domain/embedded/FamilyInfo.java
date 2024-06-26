package jun.invitation.domain.invitation.domain.embedded;

import jakarta.persistence.Embeddable;
import jakarta.persistence.Embedded;
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
    @Embedded
    private ParentDto father;

    @Embedded
    private ParentDto mother;

    private String relation;
}