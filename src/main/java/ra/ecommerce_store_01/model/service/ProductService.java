package ra.ecommerce_store_01.model.service;

import org.springframework.data.domain.Page;
import ra.ecommerce_store_01.model.entity.Product;

import org.springframework.data.domain.Pageable;
import ra.ecommerce_store_01.payload.request.ProductModel;
import ra.ecommerce_store_01.payload.respone.ProductResponse;

import java.util.List;

public interface ProductService {
    Page<Product> findByName(Pageable pageable,String name);

    Product saveOrUpdate(Product product);

    void delete(int id);

    List<Product> findAll();

    Product findById(int id);

    Page<Product> getPagging(Pageable pageable);

    Page<Product> findByCatalog_CatalogId(int catId,Pageable pageable);

    boolean deleteProduct(int proId);

    Page<Product> searchProductByPriceBetween(float starPrice,float endPrice,Pageable pageable);

    List<ProductResponse> featuredProducts();

}
