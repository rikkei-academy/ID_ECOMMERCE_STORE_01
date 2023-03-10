package ra.ecommerce_store_01.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ra.ecommerce_store_01.model.entity.Blog;

import java.util.List;

@Repository
public interface BlogRepository extends JpaRepository<Blog,Integer> {
    List<Blog> searchByBlogNameContainingIgnoreCase(String name);
    List<Blog> findByBlogStatus(boolean status);
    List<Blog> findByBlogCatalog_BlogCatalogId(int id);
}
