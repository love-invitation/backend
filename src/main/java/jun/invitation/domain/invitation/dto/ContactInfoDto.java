package jun.invitation.domain.invitation.dto;

import lombok.Data;

@Data
public class ContactInfoDto {
    private String phone;

    private String fatherName;
    private String fatherPhone;

    private String motherName;
    private String motherPhone;

    public ContactInfoDto(String phone, String fatherName, String fatherPhone, String motherName, String motherPhone) {
        this.phone = phone;
        this.fatherName = fatherName;
        this.fatherPhone = fatherPhone;
        this.motherName = motherName;
        this.motherPhone = motherPhone;
    }
}
