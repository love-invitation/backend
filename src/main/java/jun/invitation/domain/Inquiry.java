package jun.invitation.domain;

import jakarta.persistence.*;
import jun.invitation.domain.product.ProductCategory;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;

@Entity
public class Inquiry {

    @Id @GeneratedValue
    @Column(name = "inquiry_id")
    Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "productCategory_id")
    private ProductCategory productCategory;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "admin_id")
    private Admin admin;

    private String title;
    private String category;
    private String contents;

    @CreatedDate
    private LocalDateTime createdDate;
}
