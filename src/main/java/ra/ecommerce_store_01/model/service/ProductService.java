package ra.ecommerce_store_01.model.service;

import org.springframework.data.domain.Page;
import ra.ecommerce_store_01.model.entity.Product;

import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ProductService {
//    Page<Product> findByName(Pageable pageable,String name);

    Product saveOrUpdate(Product product);

    void delete(int id);

    List<Product> findAll();

    Product findById(int id);

    Page<Product> getPagging(Pageable pageable);

    List<Product> findByCatalog_CatalogId(int catId);
}
