package jun.invitation.domain.guestbook.domain;

import jakarta.persistence.*;
import jun.invitation.domain.product.domain.Product;
import jun.invitation.global.entity.BaseEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static jakarta.persistence.GenerationType.IDENTITY;

@Entity @Getter
@NoArgsConstructor
public class Guestbook extends BaseEntity {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "guestbook_id")
    private Long id;

    private String name;
    private String password;
    private String message;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;

    @Builder
    public Guestbook(String name, String password, String message) {
        this.name = name;
        this.password = password;
        this.message = message;
    }

    public void registerInvitation(Product product) {
        if (this.product != null) {
            this.product.getGuestbook().remove(this);
        }
        this.product = product;
        product.getGuestbook().add(this);
    }

    @Override
    public String toString() {
        return "Guestbook{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", password='" + password + '\'' +
                ", message='" + message + '\'' +
                '}';
    }
}
