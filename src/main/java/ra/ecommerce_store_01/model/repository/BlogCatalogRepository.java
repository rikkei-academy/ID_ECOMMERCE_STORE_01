package ra.ecommerce_store_01.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ra.ecommerce_store_01.model.entity.BlogCatalog;

import java.util.List;

@Repository
public interface BlogCatalogRepository extends JpaRepository<BlogCatalog,Integer> {
    List<BlogCatalog> searchByBlogCatalogNameContainingIgnoreCase(String name);
    List<BlogCatalog> findByBlogCatalogStatus(boolean status);
}
