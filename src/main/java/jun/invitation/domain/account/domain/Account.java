package jun.invitation.domain.account.domain;

import jakarta.persistence.*;
import jun.invitation.domain.invitation.domain.Invitation;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static jakarta.persistence.FetchType.*;
import static jakarta.persistence.GenerationType.*;
import static lombok.AccessLevel.*;

@Entity
@Getter
@NoArgsConstructor(access = PROTECTED)
public class Account {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "product_id")
    private Invitation invitation;

    private String name;
    private String bankName;
    private String accountNumber;
    private String weddingSide;

    public Account(String name, String bankName, String accountNumber, String type) {
        this.name = name;
        this.bankName = bankName;
        this.accountNumber = accountNumber;
        this.weddingSide = type;
    }

    public void register(Invitation invitation) {
        if (this.invitation != null) {
            this.invitation.getAccounts().remove(this);
        }

        this.invitation = invitation;
        invitation.getAccounts().add(this);
    }
}
