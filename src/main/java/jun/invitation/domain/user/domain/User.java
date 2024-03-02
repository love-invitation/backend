package jun.invitation.domain.user.domain;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.annotation.CreatedDate;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class User {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    private String username;
    private String password;
    private String email;
    private String role; // USER, Admin, Manger
    private String provider;
    private String providerId;

    @CreatedDate
    private LocalDateTime createDate;

    @Builder
    public User(String username, String password, String email, String role, String provider, String providerId, LocalDateTime createDate) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.role = role;
        this.provider = provider;
        this.providerId = providerId;
        this.createDate = createDate;
    }

    public void setUserIdForTest() {
        this.id = 2L;
    }

}
