package ra.ecommerce_store_01.model.service;

import ra.ecommerce_store_01.payload.request.BlogCatalogRequest;
import ra.ecommerce_store_01.payload.respone.BlogCatalogResponse;

import java.util.List;

public interface BlogCatalogService {
    List<BlogCatalogResponse> findAll();
    BlogCatalogResponse findById(int id);
    boolean saveOrUpdate(BlogCatalogRequest blogCat);
    boolean deleteBlogCat(int id);
    List<BlogCatalogResponse> searchByName(String name);
    List<BlogCatalogResponse> softByName(String directer);

    List<BlogCatalogResponse> findByStatus(boolean status);

}
