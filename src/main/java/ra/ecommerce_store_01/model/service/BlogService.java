package ra.ecommerce_store_01.model.service;

import ra.ecommerce_store_01.model.entity.Blog;
import ra.ecommerce_store_01.payload.request.BlogRequest;
import ra.ecommerce_store_01.payload.respone.BlogResponse;

import java.util.List;
import java.util.Map;

public interface BlogService {
    Map<String, Object> findAllPagging(int size, int page);
    List<BlogResponse> findAll();
    BlogResponse findById(int id);
    boolean saveOrUpdate(BlogRequest blog);
    boolean deleteBlog(int id);
    List<BlogResponse> searchByName(String name);
    Map<String, Object> softByName(String directer, int size, int page);
    Map<String, Object> softByCreateDate(String directer, int size, int page);
    Map<String, Object> softByNameAndCreated(String directerName, String directerCreated, int size, int page);
    List<BlogResponse> findByStatus(boolean status);
    List<BlogResponse> findByCatalogId(int iD);
    Blog getById(int blogId);

}
