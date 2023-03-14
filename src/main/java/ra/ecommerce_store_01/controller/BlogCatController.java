package ra.ecommerce_store_01.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ra.ecommerce_store_01.model.service.BlogCatalogService;
import ra.ecommerce_store_01.payload.request.BlogCatalogRequest;
import ra.ecommerce_store_01.payload.respone.BlogCatalogResponse;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/v1/auth/blogCatalog")
public class BlogCatController {
    @Autowired
    private BlogCatalogService blogCatalogService;

//  ---------------  Find All Blog Catalogies ---------------------

    @GetMapping("findAll")
    public ResponseEntity<?> findAll(){
        List<BlogCatalogResponse> list = blogCatalogService.findAll();
        return ResponseEntity.ok(list);
    }

    //  ---------------  Find  Blog Catalogies By Id ---------------------

    @GetMapping("findById/{blogCatalogId}")
    public ResponseEntity<?> findById(@PathVariable("blogCatalogId")int id){
        BlogCatalogResponse blogCat = blogCatalogService.findById(id);
        return ResponseEntity.ok(blogCat);
    }

    //  ---------------  Create  Blog Catalogies  ---------------------

    @PostMapping("createBlogCatalog")
    public ResponseEntity<?> createBlogCatalog(@RequestBody BlogCatalogRequest blog){
        blog.setBlogcatStatus(true);
        boolean check = blogCatalogService.saveOrUpdate(blog);
        if (check){
            return ResponseEntity.ok("Thêm mới blog catelogies thành công!");
        }else {
            return ResponseEntity.ok("Thêm mới blog catelogies thất bại!");
        }
    }


    //  ---------------  Update  Blog Catalogies  ---------------------

    @PutMapping("updateBlogCatalog/{id}")
    public ResponseEntity<?> updateBlogCalogies(@RequestBody BlogCatalogRequest blog,@PathVariable("id")int blogCatId){
        blog.setBlogCatId(blogCatId);
        boolean check = blogCatalogService.saveOrUpdate(blog);
        if (check){
            return ResponseEntity.ok("Sửa blog catelogies thành công!");
        }else {
            return ResponseEntity.ok("Sửa blog catelogies thất bại!");
        }
    }

    //  ---------------  Delete  Blog Catalogies  ---------------------

    @PatchMapping("deleteBlogCatalog/{blogCatalogId}")
    public ResponseEntity<?> deleteBlogCatalogies(@PathVariable("blogCatalogId")int blogCatId){
        boolean check = blogCatalogService.deleteBlogCat(blogCatId);
        if (check){
            return ResponseEntity.ok("Xóa blog catelogies thành công!");
        }else {
            return ResponseEntity.ok("Xóa blog catelogies thất bại!");
        }
    }

    //  ---------------  Search  Blog Catalogies By Name  ---------------------

    @GetMapping("searchByName")
    public ResponseEntity<?> searchByName(@RequestParam("searchName")String name){
        List<BlogCatalogResponse> list = blogCatalogService.searchByName(name);
        return ResponseEntity.ok(list);
    }

    //  ---------------  Search  Blog Catalogies By Name  ---------------------

    @GetMapping("softByName")
    public ResponseEntity<?> softByName(@RequestParam("directer")String directer){
        List<BlogCatalogResponse> list = blogCatalogService.softByName(directer);
        return ResponseEntity.ok(list);
    }

    //  ---------------  Search  Blog Catalogies By Name  ---------------------

    @GetMapping("findByStatus")
    public ResponseEntity<?> findByStatus(@RequestParam("status")boolean status){
        List<BlogCatalogResponse> list = blogCatalogService.findByStatus(status);
        return ResponseEntity.ok(list);
    }



}
