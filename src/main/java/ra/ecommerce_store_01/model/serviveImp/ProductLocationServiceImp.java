package ra.ecommerce_store_01.model.serviveImp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ra.ecommerce_store_01.model.entity.ProductLocation;
import ra.ecommerce_store_01.model.repository.ProductLocationRepository;
import ra.ecommerce_store_01.model.service.ProductLocationService;

import java.util.List;

@Service
public class ProductLocationServiceImp implements ProductLocationService {
    @Autowired
    ProductLocationRepository productLocationRepository;
    @Override
    public ProductLocation saveOrUpdate(ProductLocation productLocation) {
        return productLocationRepository.save(productLocation);
    }

    @Override
    public void deleteProductFromLocation(int productLocationId) {
        productLocationRepository.deleteById(productLocationId);
    }

    @Override
    public ProductLocation findById(int productLocationId) {
        return productLocationRepository.findById(productLocationId).get();
    }

    @Override
    public List<ProductLocation> filterProductByLocation(int locationId, Pageable pageable) {
        return productLocationRepository.findAllByLocation_LocationId(locationId, pageable);
    }

    @Override
    public Page<ProductLocation> getPaging(Pageable pageable) {
        return productLocationRepository.findAll(pageable);
    }
}
