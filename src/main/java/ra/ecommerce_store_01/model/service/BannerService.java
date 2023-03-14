package ra.ecommerce_store_01.model.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ra.ecommerce_store_01.model.entity.Banner;

import java.util.List;

public interface BannerService {
    List<Banner> findAll();
    Banner saveOrUpdate(Banner banner);
    void delete(int bannerId);
    Banner findById(int bannerId);
    List<Banner> findByContent(String content, Pageable pageable);
    Page<Banner> getPaging(Pageable pageable);
}
