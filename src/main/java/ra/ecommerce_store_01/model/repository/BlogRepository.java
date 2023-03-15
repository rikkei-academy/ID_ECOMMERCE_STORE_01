package ra.ecommerce_store_01.model.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ra.ecommerce_store_01.model.entity.Blog;

import java.util.List;

@Repository
public interface BlogRepository extends JpaRepository<Blog,Integer> {
    Page<Blog> searchByBlogNameContainingIgnoreCase(String name, Pageable pageable);
    Page<Blog> findByBlogStatus(boolean status,Pageable pageable);
    Page<Blog> findByBlogCatalog_BlogCatalogId(int id,Pageable pageable);
}
