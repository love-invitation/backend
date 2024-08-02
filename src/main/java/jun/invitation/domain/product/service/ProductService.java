package jun.invitation.domain.product.service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jun.invitation.domain.invitation.exception.ProductNotFoundException;
import jun.invitation.domain.product.dao.ProductRepository;
import jun.invitation.domain.product.domain.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    @PersistenceContext
    private final EntityManager em;

    @Transactional(readOnly = true)
    public Product findOne(Long id) {
        return productRepository.findById(id)
                .orElseThrow(ProductNotFoundException::new);
    }

    @Transactional
    public void delete(Long id) {
        productRepository.deleteById(id);
        em.flush();
        em.clear();
    }
}
