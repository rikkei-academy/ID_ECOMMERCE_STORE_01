package ra.ecommerce_store_01.model.service;

import ra.ecommerce_store_01.model.entity.ProductLocation;

import java.util.List;

public interface ProductLocationService {
    ProductLocation saveOrUpdate(ProductLocation productLocation);
    void deleteProductFromLocation(int productLocationId);
    ProductLocation findById(int productLocationId);
    List<ProductLocation> filterProductByLocation(int locationId);

}
