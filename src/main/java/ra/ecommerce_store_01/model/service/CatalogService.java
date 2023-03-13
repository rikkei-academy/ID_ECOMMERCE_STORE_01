package ra.ecommerce_store_01.model.service;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ra.ecommerce_store_01.model.entity.Catalog;

import java.util.List;

public interface CatalogService {
    Catalog saveOrUpdate(Catalog catalog);
    Catalog findById(int catalogId);
    List<Catalog> findAll();
    List<Catalog> searchByName(String catalogName);
    Page<Catalog> getPaging(Pageable pageable);

}

