package ra.ecommerce_store_01.model.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.PathVariable;
import ra.ecommerce_store_01.model.entity.Product;
import ra.ecommerce_store_01.payload.request.ProductModel;


import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product,Integer> {
    Page<Product> findByProductNameContaining(String productName,Pageable pageable);
    Page<Product> findByCatalog_CatalogId(int catId,Pageable pageable);
//    @Query(value = "select p.ProductID,p.ProductName,p.Price,p.imageLink,p.description,p.Delivery,p.ProductStatus\n" +
//            "from product p join productlocation p2 on p.ProductID = p2.ProductID join location l on p2.LocationID = l.locationId\n" +
//            "where l.locationId = :loId\n" +
//            "order by p.ProductID limit :size offset :page",nativeQuery = true)
//    List<Product> findByLocationName(@Param("page") int page, @Param("size") int size, @Param("loId") int locationId);
    Page<Product> findAllByPriceBetween(float starPrice,float endPrice,Pageable pageable);

    @Query(nativeQuery = true,value = "select p.productId, p.delivery, p.description, p.imageLink, p.price, p.productName, p.productStatus, p.brand_brandId, p.CatalogID, p.views\n" +
            "from product p\n" +
            "order by views desc limit 15;")
    List<Product> featuredProducts();
}
