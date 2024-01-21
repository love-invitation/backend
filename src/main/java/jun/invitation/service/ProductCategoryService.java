package jun.invitation.service;

import jun.invitation.domain.product.ProductCategory;
import jun.invitation.repository.ProductCategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductCategoryService {

    private final ProductCategoryRepository productCategoryRepository;

    public void save(ProductCategory productCategory) {
        productCategoryRepository.save(productCategory);
        return;
    }

    public List<ProductCategory> allProductCategory() {
        return productCategoryRepository.findAll();
    }

    public ProductCategory findOne(String name) {

        return productCategoryRepository.findByName(name);
    }
}
