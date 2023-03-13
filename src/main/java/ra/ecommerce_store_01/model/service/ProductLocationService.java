package ra.ecommerce_store_01.model.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ra.ecommerce_store_01.model.entity.ProductLocation;

import java.util.List;

public interface ProductLocationService {
    ProductLocation saveOrUpdate(ProductLocation productLocation);
    void deleteProductFromLocation(int productLocationId);
    ProductLocation findById(int productLocationId);
    List<ProductLocation> filterProductByLocation(int locationId, Pageable pageable);
    Page<ProductLocation> getPaging(Pageable pageable);

}
