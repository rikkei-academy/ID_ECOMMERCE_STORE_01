package ra.ecommerce_store_01.model.serviveImp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ra.ecommerce_store_01.model.entity.Blog;
import ra.ecommerce_store_01.model.entity.Comment;
import ra.ecommerce_store_01.model.repository.BlogCatalogRepository;
import ra.ecommerce_store_01.model.repository.BlogRepository;
import ra.ecommerce_store_01.model.service.BlogService;
import ra.ecommerce_store_01.payload.request.BlogRequest;
import ra.ecommerce_store_01.payload.respone.BlogResponse;
import ra.ecommerce_store_01.payload.respone.CommentResponse;

import java.util.*;

@Service
public class BlogServiceImp implements BlogService {
    @Autowired
    private BlogRepository blogRepository;
    @Autowired
    private BlogCatalogRepository blogCatalogRepository;


    @Override
    public Map<String, Object> findAllPagging(int size, int page) {
        Pageable pageable = PageRequest.of(page,size);
        Page<Blog> listBlog = blogRepository.findAll(pageable);
        List<BlogResponse> list = new ArrayList<>();
        for (Blog blog :listBlog) {
            BlogResponse blogResponse = changeData(blog);
            list.add(blogResponse);
        }
        Map<String,Object> listResponseBlog = new HashMap<>();
        listResponseBlog.put("listBlog",list);
        listResponseBlog.put("total",listBlog.getSize());
        listResponseBlog.put("totalItems",listBlog.getTotalElements());
        listResponseBlog.put("totalPage",listBlog.getTotalPages());
        return listResponseBlog;
    }

    @Override
    public List<BlogResponse> findAll() {
        List<Blog> listBlog = blogRepository.findAll();
        List<BlogResponse> list = new ArrayList<>();
        for (Blog blog :listBlog) {
            BlogResponse blogResponse = changeData(blog);
            list.add(blogResponse);
        }
        return list;
    }

    @Override
    public BlogResponse findById(int id) {

        Blog blog = blogRepository.findById(id).get();
        return changeData(blog);
    }

    @Override
    public boolean saveOrUpdate(BlogRequest blogRequest) {
        Blog blog = new Blog();
        blog.setBlogId(blogRequest.getBlogId());
        blog.setBlogName(blogRequest.getBlogName());
        blog.setBlogContent(blogRequest.getBlogContent());
        blog.setTag(blogRequest.getTag());
        blog.setCreatedDate(new Date());
        blog.setBlogStatus(blogRequest.isBlogStatus());
        blog.setBlogCatalog(blogCatalogRepository.findById(blogRequest.getBlogCatalogId()).get());
        try {
            blogRepository.save(blog);
            return true;
        }catch (Exception e){
            return false;
        }
    }

    @Override
    public boolean update(BlogRequest blogRequest) {
        Blog blog = blogRepository.findById(blogRequest.getBlogId()).get();
        blog.setBlogName(blogRequest.getBlogName());
        blog.setBlogContent(blogRequest.getBlogContent());
        blog.setTag(blogRequest.getTag());
        blog.setBlogStatus(blogRequest.isBlogStatus());
        blog.setBlogCatalog(blogCatalogRepository.findById(blogRequest.getBlogCatalogId()).get());
        try {
            blogRepository.save(blog);
            return true;
        }catch (Exception e){
            return false;
        }
    }

    @Override
    public boolean deleteBlog(int id) {
        Blog blog = blogRepository.findById(id).get();
        blog.setBlogStatus(false);
        try {
            blogRepository.save(blog);
            return true;
        }catch (Exception e){
            return false;
        }
    }

    @Override
    public Map<String, Object> searchByName(String name, Pageable pageable) {
        Page<Blog> listBlog = blogRepository.searchByBlogNameContainingIgnoreCase(name,pageable);
        List<BlogResponse> list = new ArrayList<>();
        for (Blog blog :listBlog) {
            BlogResponse blogResponse = changeData(blog);
            list.add(blogResponse);
        }
        Map<String,Object> listResponseBlog = new HashMap<>();
        listResponseBlog.put("listBlog",list);
        listResponseBlog.put("total",listBlog.getSize());
        listResponseBlog.put("totalItems",listBlog.getTotalElements());
        listResponseBlog.put("totalPage",listBlog.getTotalPages());
        return listResponseBlog;
    }

    @Override
    public Map<String, Object> softByName(String directer, int size, int page) {
        Sort.Order order;
        if (directer.equals("asc")){
            order=new Sort.Order(Sort.Direction.ASC,"BlogName");
        }else{
            order=new Sort.Order(Sort.Direction.DESC,"BlogName");
        }
        Pageable pageable = PageRequest.of(page,size,Sort.by(order));
        Page<Blog> listBlog = blogRepository.findAll(pageable);
        List<BlogResponse> list = new ArrayList<>();
        for (Blog blog :listBlog) {
            BlogResponse blogResponse = changeData(blog);
            list.add(blogResponse);
        }
        Map<String,Object> listResponseBlog = new HashMap<>();
        listResponseBlog.put("listBlog",list);
        listResponseBlog.put("total",listBlog.getSize());
        listResponseBlog.put("totalItems",listBlog.getTotalElements());
        listResponseBlog.put("totalPage",listBlog.getTotalPages());
        return listResponseBlog;
    }

    @Override
    public Map<String, Object> softByCreateDate(String directer, int size, int page) {
        Sort.Order order;
        if (directer.equals("asc")){
            order=new Sort.Order(Sort.Direction.ASC,"createdDate");
        }else{
            order=new Sort.Order(Sort.Direction.DESC,"createdDate");
        }
        Pageable pageable = PageRequest.of(page,size,Sort.by(order));
        Page<Blog> listBlog = blogRepository.findAll(pageable);
        List<BlogResponse> list = new ArrayList<>();
        for (Blog blog :listBlog) {
            BlogResponse blogResponse = changeData(blog);
            list.add(blogResponse);
        }
        Map<String,Object> listResponseBlog = new HashMap<>();
        listResponseBlog.put("listBlog",list);
        listResponseBlog.put("total",listBlog.getSize());
        listResponseBlog.put("totalItems",listBlog.getTotalElements());
        listResponseBlog.put("totalPage",listBlog.getTotalPages());
        return listResponseBlog;
    }

    @Override
    public Map<String, Object> softByNameAndCreated(String directerName, String directerCreated, int size, int page) {
        Pageable pageable;
        if (directerName.equalsIgnoreCase("asc")){
            if (directerCreated.equalsIgnoreCase("asc")){
                pageable = PageRequest.of(page,size,Sort.by("BlogName").ascending().and(Sort.by("createdDate").ascending()));
            }else {
                pageable = PageRequest.of(page,size,Sort.by("BlogName").ascending().and(Sort.by("createdDate").descending()));
            }
        }else{
            if (directerCreated.equalsIgnoreCase("asc")){
                pageable = PageRequest.of(page,size,Sort.by("BlogName").descending().and(Sort.by("createdDate").ascending()));
            }else {
                pageable = PageRequest.of(page,size,Sort.by("BlogName").descending().and(Sort.by("createdDate").descending()));
            }
        }
        Page<Blog> listBlog = blogRepository.findAll(pageable);
        List<BlogResponse> list = new ArrayList<>();
        for (Blog blog :listBlog) {
            BlogResponse blogResponse = changeData(blog);
            list.add(blogResponse);
        }
        Map<String,Object> listResponseBlog = new HashMap<>();
        listResponseBlog.put("listBlog",list);
        listResponseBlog.put("total",listBlog.getSize());
        listResponseBlog.put("totalItems",listBlog.getTotalElements());
        listResponseBlog.put("totalPage",listBlog.getTotalPages());
        return listResponseBlog;
    }

    @Override
    public Map<String, Object> findByStatus(boolean status,Pageable pageable) {
        Page<Blog> listBlog = blogRepository.findByBlogStatus(status,pageable);
        List<BlogResponse> list = new ArrayList<>();
        for (Blog blog :listBlog) {
            BlogResponse blogResponse = changeData(blog);
            list.add(blogResponse);
        }
        Map<String,Object> listResponseBlog = new HashMap<>();
        listResponseBlog.put("listBlog",list);
        listResponseBlog.put("total",listBlog.getSize());
        listResponseBlog.put("totalItems",listBlog.getTotalElements());
        listResponseBlog.put("totalPage",listBlog.getTotalPages());
        return listResponseBlog;
    }

    @Override
    public Map<String, Object> findByCatalogId(int iD,Pageable pageable) {
        Page<Blog> listBlog = blogRepository.findByBlogCatalog_BlogCatalogId(iD,pageable);
        List<BlogResponse> list = new ArrayList<>();
        for (Blog blog :listBlog) {
            BlogResponse blogResponse = changeData(blog);
            list.add(blogResponse);
        }
        Map<String,Object> listResponseBlog = new HashMap<>();
        listResponseBlog.put("listBlog",list);
        listResponseBlog.put("total",listBlog.getSize());
        listResponseBlog.put("totalItems",listBlog.getTotalElements());
        listResponseBlog.put("totalPage",listBlog.getTotalPages());
        return listResponseBlog;
    }

    @Override
    public Blog getById(int blogId) {
        return blogRepository.findById(blogId).get();
    }
    private static BlogResponse changeData(Blog blog){
        BlogResponse blogResponse = new BlogResponse();
        blogResponse.setBlogId(blog.getBlogId());
        blogResponse.setBlogContent(blog.getBlogContent());
        blogResponse.setBlogStatus(blog.isBlogStatus());
        blogResponse.setTag(blog.getTag());
        blogResponse.setBlogName(blog.getBlogName());
        blogResponse.setBlogCatalogName(blog.getBlogCatalog().getBlogCatalogName());
        blogResponse.setBlogCatalogId(blog.getBlogCatalog().getBlogCatalogId());
        blogResponse.setCreateDate(blog.getCreatedDate());
        for (Comment cmt :blog.getListComment()) {
            CommentResponse cmtResponse = new CommentResponse();
            cmtResponse.setCommentId(cmtResponse.getCommentId());
            cmtResponse.setContent(cmt.getComment());
            cmtResponse.setCreatedDate(cmt.getCreatedDate());
            cmtResponse.setUserName(cmt.getUser().getUserName());
            blogResponse.getListComment().add(cmtResponse);
        }
        return blogResponse;
    }
}
