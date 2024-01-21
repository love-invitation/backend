//package jun.invitation.domain;
//
//import jakarta.persistence.*;
//import org.springframework.data.annotation.CreatedDate;
//
//import java.time.LocalDateTime;
//
//@Entity(name = "Orders")
//public class Orders {
//
//    @Id @GeneratedValue
//    @Column(name = "orders_id")
//    private Long id;
//
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "user_id")
//    private User user;
//
//    @OneToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "payment_id")
//    private Payment payment;
//
//    @CreatedDate
//    private LocalDateTime orderDate;
//    private String productURL;
//
//}
