package jun.invitation.domain.account.domain;

import jakarta.persistence.*;
import jun.invitation.domain.invitation.domain.WeddingSide;
import jun.invitation.domain.product.domain.Product;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static jakarta.persistence.EnumType.STRING;
import static jakarta.persistence.FetchType.LAZY;
import static jakarta.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

@Entity
@Getter
@NoArgsConstructor(access = PROTECTED)
public class Account {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "product_id")
    private Product product;

    private String name;
    private String bankName;
    private String accountNumber;

    @Enumerated(STRING)
    private WeddingSide weddingSide;

    public Account(String name, String bankName, String accountNumber, WeddingSide type) {
        this.name = name;
        this.bankName = bankName;
        this.accountNumber = accountNumber;
        this.weddingSide = type;
    }

    public void register(Product product) {
        if (this.product != null) {
            this.product.getAccounts().remove(this);
        }

        this.product = product;
        product.getAccounts().add(this);
    }

    @Override
    public String toString() {
        return "Account{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", bankName='" + bankName + '\'' +
                ", accountNumber='" + accountNumber + '\'' +
                ", weddingSide=" + weddingSide +
                '}';
    }
}
