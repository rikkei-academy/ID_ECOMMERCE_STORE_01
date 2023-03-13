package ra.ecommerce_store_01.model.serviveImp;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


import org.springframework.stereotype.Service;
import ra.ecommerce_store_01.model.entity.Catalog;
import ra.ecommerce_store_01.model.repository.CatalogRepository;
import ra.ecommerce_store_01.model.service.CatalogService;


import java.util.List;

@Service
public class CatalogServiceImp implements CatalogService {
    @Autowired
    CatalogRepository catalogRepository;
    @Override
    public Catalog saveOrUpdate(Catalog catalog) {
        return catalogRepository.save(catalog);
    }

    @Override
    public Catalog findById(int catalogId) {
        return catalogRepository.findById(catalogId).get();
    }

    @Override
    public List<Catalog> findAll() {
        return catalogRepository.findAll();
    }

    @Override
    public List<Catalog> searchByName(String catalogName) {
        return catalogRepository.searchCatalogByCatalogNameContains(catalogName);
    }

    @Override
    public Page<Catalog> getPaging(Pageable pageable) {
        return catalogRepository.findAll(pageable);
    }

}
