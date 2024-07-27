package jun.invitation.domain.contact.dto;

import jun.invitation.domain.contact.domain.Contact;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

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

    public ContactInfoDto(Contact contact) {
        this.phoneNumber = contact.getPhoneNumber();
        this.name = contact.getName();
        this.relation = contact.getRelation();
    }

}
