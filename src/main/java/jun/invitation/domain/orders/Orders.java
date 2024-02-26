package jun.invitation.domain.orders;

import jakarta.persistence.*;
import jun.invitation.domain.user.domain.User;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;

@Entity(name = "Orders")
public class Orders {

    @Id @GeneratedValue
    @Column(name = "orders_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn
    private User user;

    @CreatedDate
    private LocalDateTime orderDate;
    private String productURL;

}
