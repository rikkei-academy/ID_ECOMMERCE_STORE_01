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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
            BlogResponse blogResponse = new BlogResponse();
            blogResponse.setBlogId(blog.getBlogId());
            blogResponse.setBlogContent(blog.getBlogContent());
            blogResponse.setBlogStatus(blog.isBlogStatus());
            blogResponse.setTag(blog.getTag());
            blogResponse.setBlogName(blog.getBlogName());
            blogResponse.setBlogCatalogName(blog.getBlogCatalog().getBlogCatalogName());
            blogResponse.setBlogCatalogId(blog.getBlogCatalog().getBlogCatalogId());
            for (Comment cmt :blog.getListComment()) {
                CommentResponse cmtResponse = new CommentResponse();
                cmtResponse.setCommentId(cmtResponse.getCommentId());
                cmtResponse.setContent(cmt.getComment());
                cmtResponse.setCreatedDate(cmt.getCreatedDate());
                cmtResponse.setUserName(cmt.getUser().getUserName());
                blogResponse.getListComment().add(cmtResponse);
            }
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
            BlogResponse blogResponse = new BlogResponse();
            blogResponse.setBlogId(blog.getBlogId());
            blogResponse.setBlogContent(blog.getBlogContent());
            blogResponse.setBlogStatus(blog.isBlogStatus());
            blogResponse.setTag(blog.getTag());
            blogResponse.setBlogName(blog.getBlogName());
            blogResponse.setBlogCatalogName(blog.getBlogCatalog().getBlogCatalogName());
            blogResponse.setBlogCatalogId(blog.getBlogCatalog().getBlogCatalogId());
            for (Comment cmt :blog.getListComment()) {
                CommentResponse cmtResponse = new CommentResponse();
                cmtResponse.setCommentId(cmtResponse.getCommentId());
                cmtResponse.setContent(cmt.getComment());
                cmtResponse.setCreatedDate(cmt.getCreatedDate());
                cmtResponse.setUserName(cmt.getUser().getUserName());
                blogResponse.getListComment().add(cmtResponse);
            }
            list.add(blogResponse);
        }
        return list;
    }

    @Override
    public BlogResponse findById(int id) {

        Blog blog = blogRepository.findById(id).get();
        BlogResponse blogResponse = new BlogResponse();
        blogResponse.setBlogId(blog.getBlogId());
        blogResponse.setBlogContent(blog.getBlogContent());
        blogResponse.setBlogStatus(blog.isBlogStatus());
        blogResponse.setTag(blog.getTag());
        blogResponse.setBlogName(blog.getBlogName());
        blogResponse.setBlogCatalogName(blog.getBlogCatalog().getBlogCatalogName());
        blogResponse.setBlogCatalogId(blog.getBlogCatalog().getBlogCatalogId());
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

    @Override
    public boolean saveOrUpdate(BlogRequest blogRequest) {
        Blog blog = new Blog();
        blog.setBlogId(blogRequest.getBlogId());
        blog.setBlogName(blogRequest.getBlogName());
        blog.setBlogContent(blogRequest.getBlogContent());
        blog.setTag(blogRequest.getTag());
        blog.setCreatedDate(blogRequest.getCreateDate());
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
    public List<BlogResponse> searchByName(String name) {
        List<Blog> listBlog = blogRepository.searchByBlogNameContainingIgnoreCase(name);
        List<BlogResponse> list = new ArrayList<>();
        for (Blog blog :listBlog) {
            BlogResponse blogResponse = new BlogResponse();
            blogResponse.setBlogId(blog.getBlogId());
            blogResponse.setBlogContent(blog.getBlogContent());
            blogResponse.setBlogStatus(blog.isBlogStatus());
            blogResponse.setTag(blog.getTag());
            blogResponse.setBlogName(blog.getBlogName());
            blogResponse.setBlogCatalogName(blog.getBlogCatalog().getBlogCatalogName());
            blogResponse.setBlogCatalogId(blog.getBlogCatalog().getBlogCatalogId());
            for (Comment cmt :blog.getListComment()) {
                CommentResponse cmtResponse = new CommentResponse();
                cmtResponse.setCommentId(cmtResponse.getCommentId());
                cmtResponse.setContent(cmt.getComment());
                cmtResponse.setCreatedDate(cmt.getCreatedDate());
                cmtResponse.setUserName(cmt.getUser().getUserName());
                blogResponse.getListComment().add(cmtResponse);
            }
            list.add(blogResponse);
        }
        return list;
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
            BlogResponse blogResponse = new BlogResponse();
            blogResponse.setBlogId(blog.getBlogId());
            blogResponse.setBlogContent(blog.getBlogContent());
            blogResponse.setBlogStatus(blog.isBlogStatus());
            blogResponse.setTag(blog.getTag());
            blogResponse.setBlogName(blog.getBlogName());
            blogResponse.setBlogCatalogName(blog.getBlogCatalog().getBlogCatalogName());
            blogResponse.setBlogCatalogId(blog.getBlogCatalog().getBlogCatalogId());
            for (Comment cmt :blog.getListComment()) {
                CommentResponse cmtResponse = new CommentResponse();
                cmtResponse.setCommentId(cmtResponse.getCommentId());
                cmtResponse.setContent(cmt.getComment());
                cmtResponse.setCreatedDate(cmt.getCreatedDate());
                cmtResponse.setUserName(cmt.getUser().getUserName());
                blogResponse.getListComment().add(cmtResponse);
            }
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
            BlogResponse blogResponse = new BlogResponse();
            blogResponse.setBlogId(blog.getBlogId());
            blogResponse.setBlogContent(blog.getBlogContent());
            blogResponse.setBlogStatus(blog.isBlogStatus());
            blogResponse.setTag(blog.getTag());
            blogResponse.setBlogName(blog.getBlogName());
            blogResponse.setBlogCatalogName(blog.getBlogCatalog().getBlogCatalogName());
            blogResponse.setBlogCatalogId(blog.getBlogCatalog().getBlogCatalogId());
            for (Comment cmt :blog.getListComment()) {
                CommentResponse cmtResponse = new CommentResponse();
                cmtResponse.setCommentId(cmtResponse.getCommentId());
                cmtResponse.setContent(cmt.getComment());
                cmtResponse.setCreatedDate(cmt.getCreatedDate());
                cmtResponse.setUserName(cmt.getUser().getUserName());
                blogResponse.getListComment().add(cmtResponse);
            }
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
            BlogResponse blogResponse = new BlogResponse();
            blogResponse.setBlogId(blog.getBlogId());
            blogResponse.setBlogContent(blog.getBlogContent());
            blogResponse.setBlogStatus(blog.isBlogStatus());
            blogResponse.setTag(blog.getTag());
            blogResponse.setBlogName(blog.getBlogName());
            blogResponse.setBlogCatalogName(blog.getBlogCatalog().getBlogCatalogName());
            blogResponse.setBlogCatalogId(blog.getBlogCatalog().getBlogCatalogId());
            for (Comment cmt :blog.getListComment()) {
                CommentResponse cmtResponse = new CommentResponse();
                cmtResponse.setCommentId(cmtResponse.getCommentId());
                cmtResponse.setContent(cmt.getComment());
                cmtResponse.setCreatedDate(cmt.getCreatedDate());
                cmtResponse.setUserName(cmt.getUser().getUserName());
                blogResponse.getListComment().add(cmtResponse);
            }
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
    public List<BlogResponse> findByStatus(boolean status) {
        List<Blog> listBlog = blogRepository.findByBlogStatus(status);
        List<BlogResponse> list = new ArrayList<>();
        for (Blog blog :listBlog) {
            BlogResponse blogResponse = new BlogResponse();
            blogResponse.setBlogId(blog.getBlogId());
            blogResponse.setBlogContent(blog.getBlogContent());
            blogResponse.setBlogStatus(blog.isBlogStatus());
            blogResponse.setTag(blog.getTag());
            blogResponse.setBlogName(blog.getBlogName());
            blogResponse.setBlogCatalogName(blog.getBlogCatalog().getBlogCatalogName());
            blogResponse.setBlogCatalogId(blog.getBlogCatalog().getBlogCatalogId());
            for (Comment cmt :blog.getListComment()) {
                CommentResponse cmtResponse = new CommentResponse();
                cmtResponse.setCommentId(cmtResponse.getCommentId());
                cmtResponse.setContent(cmt.getComment());
                cmtResponse.setCreatedDate(cmt.getCreatedDate());
                cmtResponse.setUserName(cmt.getUser().getUserName());
                blogResponse.getListComment().add(cmtResponse);
            }
            list.add(blogResponse);
        }
        return list;
    }

    @Override
    public List<BlogResponse> findByCatalogId(int iD) {
        List<Blog> listBlog = blogRepository.findByBlogCatalog_BlogCatalogId(iD);
        List<BlogResponse> list = new ArrayList<>();
        for (Blog blog :listBlog) {
            BlogResponse blogResponse = new BlogResponse();
            blogResponse.setBlogId(blog.getBlogId());
            blogResponse.setBlogContent(blog.getBlogContent());
            blogResponse.setBlogStatus(blog.isBlogStatus());
            blogResponse.setTag(blog.getTag());
            blogResponse.setBlogName(blog.getBlogName());
            blogResponse.setBlogCatalogName(blog.getBlogCatalog().getBlogCatalogName());
            blogResponse.setBlogCatalogId(blog.getBlogCatalog().getBlogCatalogId());
            for (Comment cmt :blog.getListComment()) {
                CommentResponse cmtResponse = new CommentResponse();
                cmtResponse.setCommentId(cmtResponse.getCommentId());
                cmtResponse.setContent(cmt.getComment());
                cmtResponse.setCreatedDate(cmt.getCreatedDate());
                cmtResponse.setUserName(cmt.getUser().getUserName());
                blogResponse.getListComment().add(cmtResponse);
            }
            list.add(blogResponse);
        }
        return list;
    }
}
