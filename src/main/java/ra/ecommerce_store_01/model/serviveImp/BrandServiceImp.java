package ra.ecommerce_store_01.model.serviveImp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ra.ecommerce_store_01.model.entity.Brand;
import ra.ecommerce_store_01.model.repository.BrandRepository;
import ra.ecommerce_store_01.model.service.BrandService;

import java.util.List;

@Service
public class BrandServiceImp implements BrandService {
    @Autowired
    private BrandRepository brandRepository;
    @Override
    public Brand saveOrUpdate(Brand brand) {
        return brandRepository.save(brand);
    }

    @Override
    public void deleteBrand(int brandId) {
        brandRepository.deleteById(brandId);
    }

    @Override
    public Brand findById(int brandId) {
        return brandRepository.findById(brandId).get();
    }

    @Override
    public List<Brand> findByBrandName(String content, Pageable pageable) {
        return brandRepository.findByBrandNameContaining(content,pageable);
    }

    @Override
    public Page<Brand> getPaging(Pageable pageable) {
        return brandRepository.findAll(pageable);
    }
}
