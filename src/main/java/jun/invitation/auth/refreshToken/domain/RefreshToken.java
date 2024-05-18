package jun.invitation.auth.refreshToken.domain;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

@AllArgsConstructor
@NoArgsConstructor
@Getter @RedisHash(value = "refreshToken", timeToLive = 60*60*24*3)
public class RefreshToken {

    @Id
    private String RefreshToken;

    private Long userId;

}
