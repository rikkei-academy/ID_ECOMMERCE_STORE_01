package ra.ecommerce_store_01.model.serviveImp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ra.ecommerce_store_01.model.entity.Blog;
import ra.ecommerce_store_01.model.entity.BlogCatalog;
import ra.ecommerce_store_01.model.repository.BlogCatalogRepository;
import ra.ecommerce_store_01.model.service.BlogCatalogService;
import ra.ecommerce_store_01.payload.request.BlogCatalogRequest;
import ra.ecommerce_store_01.payload.respone.BlogCatalogResponse;
import ra.ecommerce_store_01.payload.respone.BlogResponse;

import java.util.ArrayList;
import java.util.List;

@Service
public class BlogCatServiceImp implements BlogCatalogService {

    @Autowired
    private BlogCatalogRepository blogCatalogRepository;
    @Override
    public List<BlogCatalogResponse> findAll() {

        List<BlogCatalog> listBlogCat = blogCatalogRepository.findAll();
        List<BlogCatalogResponse> list = new ArrayList<>();
        for (BlogCatalog blogCat :listBlogCat) {
            BlogCatalogResponse blogCatResponse = new BlogCatalogResponse();
            blogCatResponse.setBlogCatalogId(blogCat.getBlogCatalogId());
            blogCatResponse.setBlogCatalogName(blogCat.getBlogCatalogName());
            blogCatResponse.setBlogCatalogStatus(blogCat.isBlogCatalogStatus());
            for (Blog blog:blogCat.getListBlog()) {
                BlogResponse blogResponse = new BlogResponse();
                blogResponse.setBlogId(blog.getBlogId());
                blogResponse.setBlogName(blog.getBlogName());
                blogResponse.setBlogContent(blog.getBlogContent());
                blogResponse.setBlogStatus(blog.isBlogStatus());
                blogResponse.setBlogContent(blog.getBlogContent());
                blogResponse.setCreateDate(blog.getCreatedDate());
                blogResponse.setTag(blog.getTag());
                blogResponse.setBlogCatalogName(blog.getBlogCatalog().getBlogCatalogName());
                blogResponse.setBlogCatalogId(blog.getBlogCatalog().getBlogCatalogId());
                blogCatResponse.getListBlog().add(blogResponse);
            }
            list.add(blogCatResponse);
        }
        return list;
    }

    @Override
    public BlogCatalogResponse findById(int id) {
        BlogCatalog blogCatalog = blogCatalogRepository.findById(id).get();
        BlogCatalogResponse blogCatResponse = new BlogCatalogResponse();
        blogCatResponse.setBlogCatalogId(blogCatalog.getBlogCatalogId());
        blogCatResponse.setBlogCatalogName(blogCatalog.getBlogCatalogName());
        blogCatResponse.setBlogCatalogStatus(blogCatalog.isBlogCatalogStatus());
        for (Blog blog: blogCatalog.getListBlog()) {
            BlogResponse blogResponse = new BlogResponse();
            blogResponse.setBlogId(blog.getBlogId());
            blogResponse.setBlogName(blog.getBlogName());
            blogResponse.setBlogContent(blog.getBlogContent());
            blogResponse.setBlogStatus(blog.isBlogStatus());
            blogResponse.setBlogContent(blog.getBlogContent());
            blogResponse.setCreateDate(blog.getCreatedDate());
            blogResponse.setTag(blog.getTag());
            blogResponse.setBlogCatalogName(blog.getBlogCatalog().getBlogCatalogName());
            blogResponse.setBlogCatalogId(blog.getBlogCatalog().getBlogCatalogId());
            blogCatResponse.getListBlog().add(blogResponse);
        }
        return blogCatResponse;
    }

    @Override
    public boolean saveOrUpdate(BlogCatalogRequest blogCat) {
        BlogCatalog blogCatalog = new BlogCatalog();
        blogCatalog.setBlogCatalogId(blogCat.getBlogCatId());
        blogCatalog.setBlogCatalogName(blogCat.getBlogCatName());
        blogCatalog.setBlogCatalogStatus(blogCat.isBlogcatStatus());
        try {
            blogCatalogRepository.save(blogCatalog);
            return true;
        }catch (Exception e){
            return false;
        }
    }

    @Override
    public boolean deleteBlogCat(int id) {
        BlogCatalog blogCatalog = blogCatalogRepository.findById(id).get();
        blogCatalog.setBlogCatalogStatus(false);
        try {
            blogCatalogRepository.save(blogCatalog);
            return true;
        }catch (Exception e){
            return false;
        }
    }

    @Override
    public List<BlogCatalogResponse> searchByName(String name) {
        List<BlogCatalogResponse> list = new ArrayList<>();
        List<BlogCatalog> listBlogCat = blogCatalogRepository.searchByBlogCatalogNameContainingIgnoreCase(name);
        for (BlogCatalog blogCat :listBlogCat) {
            BlogCatalogResponse blogCatResponse = new BlogCatalogResponse();
            blogCatResponse.setBlogCatalogId(blogCat.getBlogCatalogId());
            blogCatResponse.setBlogCatalogName(blogCat.getBlogCatalogName());
            blogCatResponse.setBlogCatalogStatus(blogCat.isBlogCatalogStatus());
            for (Blog blog:blogCat.getListBlog()) {
                BlogResponse blogResponse = new BlogResponse();
                blogResponse.setBlogId(blog.getBlogId());
                blogResponse.setBlogName(blog.getBlogName());
                blogResponse.setBlogContent(blog.getBlogContent());
                blogResponse.setBlogStatus(blog.isBlogStatus());
                blogResponse.setBlogContent(blog.getBlogContent());
                blogResponse.setCreateDate(blog.getCreatedDate());
                blogResponse.setTag(blog.getTag());
                blogResponse.setBlogCatalogName(blog.getBlogCatalog().getBlogCatalogName());
                blogResponse.setBlogCatalogId(blog.getBlogCatalog().getBlogCatalogId());
                blogCatResponse.getListBlog().add(blogResponse);
            }
            list.add(blogCatResponse);
        }
        return list;
    }

    @Override
    public List<BlogCatalogResponse> softByName(String directer) {
        List<BlogCatalog> listBlogCat= new ArrayList<>();
        if (directer.equalsIgnoreCase("asc")){
            listBlogCat = blogCatalogRepository.findAll(Sort.by("BlogCatalogName").ascending());
        }else {
            listBlogCat = blogCatalogRepository.findAll(Sort.by("BlogCatalogName").descending());
        }
        List<BlogCatalogResponse> list = new ArrayList<>();
        for (BlogCatalog blogCat :listBlogCat) {
            BlogCatalogResponse blogCatResponse = new BlogCatalogResponse();
            blogCatResponse.setBlogCatalogId(blogCat.getBlogCatalogId());
            blogCatResponse.setBlogCatalogName(blogCat.getBlogCatalogName());
            blogCatResponse.setBlogCatalogStatus(blogCat.isBlogCatalogStatus());
            for (Blog blog:blogCat.getListBlog()) {
                BlogResponse blogResponse = new BlogResponse();
                blogResponse.setBlogId(blog.getBlogId());
                blogResponse.setBlogName(blog.getBlogName());
                blogResponse.setBlogContent(blog.getBlogContent());
                blogResponse.setBlogStatus(blog.isBlogStatus());
                blogResponse.setBlogContent(blog.getBlogContent());
                blogResponse.setCreateDate(blog.getCreatedDate());
                blogResponse.setTag(blog.getTag());
                blogResponse.setBlogCatalogName(blog.getBlogCatalog().getBlogCatalogName());
                blogResponse.setBlogCatalogId(blog.getBlogCatalog().getBlogCatalogId());
                blogCatResponse.getListBlog().add(blogResponse);
            }
            list.add(blogCatResponse);
        }
        return list;
    }

    @Override
    public List<BlogCatalogResponse> findByStatus(boolean status) {
        List<BlogCatalog> listBlogCat = blogCatalogRepository.findByBlogCatalogStatus(status);
        List<BlogCatalogResponse> list = new ArrayList<>();
        for (BlogCatalog blogCat :listBlogCat) {
            BlogCatalogResponse blogCatResponse = new BlogCatalogResponse();
            blogCatResponse.setBlogCatalogId(blogCat.getBlogCatalogId());
            blogCatResponse.setBlogCatalogName(blogCat.getBlogCatalogName());
            blogCatResponse.setBlogCatalogStatus(blogCat.isBlogCatalogStatus());
            for (Blog blog:blogCat.getListBlog()) {
                BlogResponse blogResponse = new BlogResponse();
                blogResponse.setBlogId(blog.getBlogId());
                blogResponse.setBlogName(blog.getBlogName());
                blogResponse.setBlogContent(blog.getBlogContent());
                blogResponse.setBlogStatus(blog.isBlogStatus());
                blogResponse.setBlogContent(blog.getBlogContent());
                blogResponse.setCreateDate(blog.getCreatedDate());
                blogResponse.setTag(blog.getTag());
                blogResponse.setBlogCatalogName(blog.getBlogCatalog().getBlogCatalogName());
                blogResponse.setBlogCatalogId(blog.getBlogCatalog().getBlogCatalogId());
                blogCatResponse.getListBlog().add(blogResponse);
            }
            list.add(blogCatResponse);
        }
        return list;
    }



}
