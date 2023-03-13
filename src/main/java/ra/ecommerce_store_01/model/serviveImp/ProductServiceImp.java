package ra.ecommerce_store_01.model.serviveImp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ra.ecommerce_store_01.model.entity.Image;
import ra.ecommerce_store_01.model.entity.Product;
import ra.ecommerce_store_01.model.repository.ProductRepository;
import ra.ecommerce_store_01.model.service.ProductService;
import ra.ecommerce_store_01.payload.request.ProductModel;
import ra.ecommerce_store_01.payload.respone.ProductResponse;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductServiceImp implements ProductService {
    @Autowired
    private ProductRepository productRepository;


    @Override
    public Page<Product> findByName(Pageable pageable, String name) {
        return productRepository.findByProductNameContaining(name,pageable);
    }

    @Override
    public Product saveOrUpdate(Product product) {
        return productRepository.save(product);
    }

    @Override
    public void delete(int id) {
     productRepository.deleteById(id);
    }

    @Override
    public List<Product> findAll() {
        return productRepository.findAll();
    }

    @Override
    public Product findById(int id) {
        return productRepository.findById(id).get();
    }

    @Override
    public Page<Product> getPagging(Pageable pageable) {
        return productRepository.findAll(pageable);
    }

    @Override
    public Page<Product> findByCatalog_CatalogId(int catId,Pageable pageable) {
        return productRepository.findByCatalog_CatalogId(catId,pageable);
    }

    @Override
    public boolean deleteProduct(int proId) {
        Product product = productRepository.findById(proId).get();
        product.setProductStatus(false);
        try {
            productRepository.save(product);
            return true;
        }catch (Exception e){
            return false;
        }
    }

    @Override
    public Page<Product> searchProductByPriceBetween(float starPrice, float endPrice, Pageable pageable) {
        return productRepository.findAllByPriceBetween(starPrice,endPrice,pageable);
    }

    @Override
    public List<ProductResponse> featuredProducts() {
        List<Product> listProduct = productRepository.featuredProducts();
        List<ProductResponse> list = new ArrayList<>();
        for (Product pr :listProduct) {
            ProductResponse productResponse = new ProductResponse();
            productResponse.setProductId(pr.getProductId());
            productResponse.setProductName(pr.getProductName());
            productResponse.setDelivery(pr.isDelivery());
            productResponse.setImageLink(pr.getImageLink());
            productResponse.setPrice(pr.getPrice());
            productResponse.setDescription(pr.getDescription());
            productResponse.setBrandId(pr.getBrand().getBrandId());
            productResponse.setBrandName(pr.getBrand().getBrandName());
            productResponse.setCatalogId(pr.getCatalog().getCatalogId());
            productResponse.setCatalogName(pr.getCatalog().getCatalogName());
            productResponse.setViews(pr.getViews());
            for (Image image :pr.getListImage()) {
                productResponse.getListImage().add(image.getImageLink());
            }
            list.add(productResponse);
        }
        return list;
    }
}
