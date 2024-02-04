package jun.invitation.dto;

import lombok.Data;

@Data
public class LoginRequestDto {
    private String email;
    private String provider;
    private String providerId;
}

