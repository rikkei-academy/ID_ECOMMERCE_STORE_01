package ra.ecommerce_store_01.model.serviveImp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ra.ecommerce_store_01.model.entity.Banner;
import ra.ecommerce_store_01.model.repository.BannerRepository;
import ra.ecommerce_store_01.model.service.BannerService;

import java.util.List;

@Service
public class BannerServiceImp implements BannerService {
    @Autowired
    private BannerRepository bannerRepository;

    @Override
    public List<Banner> findAll() {
        return bannerRepository.findAll();
    }

    @Override
    public Banner saveOrUpdate(Banner banner) {
        return bannerRepository.save(banner);
    }

    @Override
    public void delete(int bannerId) {
        bannerRepository.deleteById(bannerId);
    }

    @Override
    public Banner findById(int bannerId) {
        return bannerRepository.findById(bannerId).get();
    }

    @Override
    public List<Banner> findByContent(String content, Pageable pageable) {
        return bannerRepository.findByContentContaining(content, pageable);
    }

    @Override
    public Page<Banner> getPaging(Pageable pageable) {
        return bannerRepository.findAll(pageable);
    }
}
