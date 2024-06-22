package jun.invitation.domain.invitation.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ContactInfoDto {

    private String name;

    private String phoneNumber;

    private String relation;

    public ContactInfoDto(String phoneNumber, String name, String relation) {
        this.phoneNumber = phoneNumber;
        this.name = name;
        this.relation = relation;
    }
}
