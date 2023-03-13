package ra.ecommerce_store_01.model.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ra.ecommerce_store_01.model.entity.Brand;

import java.util.List;

public interface BrandService {
    Brand saveOrUpdate(Brand brand);
    void deleteBrand(int brandId);
    Brand findById(int brandId);
    List<Brand> findByBrandName(String content, Pageable pageable);
    Page<Brand> getPaging(Pageable pageable);
}
