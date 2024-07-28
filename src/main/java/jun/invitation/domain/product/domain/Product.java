package jun.invitation.domain.product.domain;

import jakarta.persistence.*;
import jun.invitation.domain.account.domain.Account;
import jun.invitation.domain.contact.domain.Contact;
import jun.invitation.domain.gallery.Gallery;
import jun.invitation.domain.guestbook.domain.Guestbook;
import jun.invitation.domain.priority.domain.Priority;
import jun.invitation.domain.productInfo.domain.ProductInfo;
import jun.invitation.domain.reservation.domain.Reservation;
import jun.invitation.domain.shareThumbnail.domain.ShareThumbnail;
import jun.invitation.domain.transport.domain.Transport;
import jun.invitation.domain.user.domain.User;
import jun.invitation.global.entity.BaseEntity;
import jun.invitation.image.domain.Image;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.CascadeType.PERSIST;
import static jakarta.persistence.CascadeType.REMOVE;
import static jakarta.persistence.FetchType.LAZY;
import static jakarta.persistence.GenerationType.*;

@Getter
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "Product_Type")
@NoArgsConstructor
public abstract class Product extends BaseEntity {

    @Id @GeneratedValue(strategy = IDENTITY)
    @Column(name = "product_id")
    private Long id;

    @Column(nullable = false, unique = true)
    private Long tsid;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "productInfo_id")
    private ProductInfo productInfo;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToOne(fetch = LAZY, cascade = {PERSIST, REMOVE})
    @JoinColumn(name = "share_thumbnail_id")
    private ShareThumbnail shareThumbnail;

    @OneToOne(fetch = LAZY)
    @JoinColumn(name = "main_image_id")
    private Image mainImage;

    @OneToOne(fetch = LAZY, cascade = {PERSIST, REMOVE})
    @JoinColumn(name = "reservation_id")
    private Reservation reservation;

    @OneToMany(mappedBy = "product", cascade = PERSIST)
    private List<Gallery> gallery = new ArrayList<>();

    @OneToMany(mappedBy = "product", cascade = PERSIST)
    private List<Transport> transport = new ArrayList<>();

    @OneToMany(mappedBy = "product", cascade = PERSIST)
    @OrderBy("priority")
    private List<Priority> priority = new ArrayList<>();

    @OneToMany(mappedBy = "product", cascade = PERSIST)
    private List<Guestbook> guestbook = new ArrayList<>();

    @OneToMany(mappedBy = "product", cascade = PERSIST)
    private List<Contact> contacts = new ArrayList<>();

    @OneToMany(mappedBy = "product", cascade = PERSIST)
    private List<Account> accounts = new ArrayList<>();

    private Boolean guestbookCheck;

    public Product(Boolean guestbookCheck) {
        this.guestbookCheck = guestbookCheck;
    }

    public void update(Boolean guestbookCheck){
        this.guestbookCheck = guestbookCheck;
    }

    public void register(User user, Long identifier, ProductInfo productInfo, ShareThumbnail createdThumbnail, Reservation createdReservation) {
        this.user = user;
        this.tsid = identifier;
        this.productInfo = productInfo;
        this.shareThumbnail = createdThumbnail;
        this.reservation = createdReservation;
    }

    public void registerMainImage(Image image) {
        this.mainImage = image;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", tsid=" + tsid +
                ", productInfo=" + productInfo.toString() +
                ", user=" + user.toString() +
                ", shareThumbnail=" + shareThumbnail.toString() +
                ", mainImage=" + mainImage.toString() +
                ", reservation=" + reservation.toString() +
                ", gallery=" + gallery.toString() +
                ", transport=" + transport.toString() +
                ", priority=" + priority.toString() +
                ", guestbook=" + guestbook.toString() +
                ", contacts=" + contacts.toString() +
                ", accounts=" + accounts.toString() +
                ", guestbookCheck=" + guestbookCheck +
                '}';
    }
}
