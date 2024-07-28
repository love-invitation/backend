package jun.invitation.domain.contact.domain;

import jakarta.persistence.*;
import jun.invitation.domain.invitation.domain.Invitation;
import jun.invitation.domain.invitation.domain.embedded.WeddingSide;
import jun.invitation.domain.product.domain.Product;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static jakarta.persistence.EnumType.STRING;
import static jakarta.persistence.FetchType.LAZY;
import static jakarta.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

@Entity
@NoArgsConstructor(access = PROTECTED)
@Getter
public class Contact {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "product_id")
    private Product product;

    private String name;
    private String phoneNumber;
    private String relation;

    @Enumerated(STRING)
    private WeddingSide weddingSide;

    @Builder
    public Contact(String name, String phoneNumber, String relation, WeddingSide weddingSide) {
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.relation = relation;
        this.weddingSide = weddingSide;
    }

    public void register(Product product) {

        if (this.product != null) {
            this.product.getContacts().remove(this);
        }

        this.product = product;
        product.getContacts().add(this);
    }

    @Override
    public String toString() {
        return "Contact{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", relation='" + relation + '\'' +
                ", weddingSide=" + weddingSide +
                '}';
    }
}
