package jun.invitation.domain.product.service;

import jun.invitation.domain.invitation.domain.Invitation;
import jun.invitation.domain.product.dao.ProductRepository;
import jun.invitation.domain.product.domain.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    public Product findOne(Long id) {

        return productRepository.findById(id).orElseGet(null);
    }

    public void deleteByInvitation(Invitation invitation) {
        productRepository.deleteByInvitation(invitation);
    }
}
