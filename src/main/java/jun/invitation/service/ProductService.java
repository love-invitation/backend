package jun.invitation.service;

import jun.invitation.domain.product.Product;
import jun.invitation.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    public List<Product> allProducts() {
        return productRepository.findAll();
    }

    public void saveProduct(Product product) {
        productRepository.save(product);
    }

    public Product findOne(Long id) {

        return productRepository.findById(id).orElseGet(null);
    }
}
