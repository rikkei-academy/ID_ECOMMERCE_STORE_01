package ra.ecommerce_store_01.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ra.ecommerce_store_01.model.service.BlogService;
import ra.ecommerce_store_01.payload.request.BlogRequest;
import ra.ecommerce_store_01.payload.respone.BlogResponse;

import java.util.Date;
import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/v1/auth/blog")
public class BlogController {
    @Autowired
    private BlogService blogService;

    // ------------------------ Find All Blog --------------------------------
    @GetMapping("findAll")
    public ResponseEntity<?> findAll() {
        List<BlogResponse> list = blogService.findAll();
        return ResponseEntity.ok(list);
    }

    // ------------------------ Find All Pagging --------------------------------
    @GetMapping("findAllPagging")
    public ResponseEntity<?> findAllPagging(@RequestParam(defaultValue = "0") int page,
                                            @RequestParam(defaultValue = "6") int size) {
        Map<String, Object> list = blogService.findAllPagging(size, page);
        return ResponseEntity.ok(list);
    }

    // ------------------------ SOFT BY NAME --------------------------------
    @GetMapping("softByName")
    public ResponseEntity<?> softByName(@RequestParam("diretion") String direction,
                                        @RequestParam(defaultValue = "0") int page,
                                        @RequestParam(defaultValue = "6") int size) {
        Map<String, Object> list = blogService.softByName(direction, size, page);
        return ResponseEntity.ok(list);
    }

    // ------------------------ SOFT BY CreateDate --------------------------------

    @GetMapping("softByCreateDate")
    public ResponseEntity<?> softByCreateDate(@RequestParam("diretion") String direction,
                                              @RequestParam(defaultValue = "0") int page,
                                              @RequestParam(defaultValue = "6") int size) {
        Map<String, Object> list = blogService.softByCreateDate(direction, size, page);
        return ResponseEntity.ok(list);
    }

    // ------------------------ SOFT BY CreateDate --------------------------------

    @GetMapping("softByNameAndCreated")
    public ResponseEntity<?> softByNameAndCreated(@RequestParam("directionName") String directionName,
                                                  @RequestParam("directionDate") String directionDate,
                                                  @RequestParam(defaultValue = "0") int page,
                                                  @RequestParam(defaultValue = "6") int size) {
        Map<String, Object> list = blogService.softByNameAndCreated(directionName, directionDate, size, page);
        return ResponseEntity.ok(list);
    }

    // ------------------------ Find By Id --------------------------------

    @GetMapping("findById/{blogId}")
    public ResponseEntity<?> findById(@PathVariable("blogId")int id) {
        BlogResponse blogResponse = blogService.findById(id);
        return ResponseEntity.ok(blogResponse);
    }

    // ------------------------ Create Blog --------------------------------

    @PostMapping("createBlog")
    public ResponseEntity<?> createBlog(@RequestBody BlogRequest blogRequest) {
        blogRequest.setBlogStatus(true);
        blogRequest.setCreateDate(new Date());
        boolean check = blogService.saveOrUpdate(blogRequest);
        if (check){
            return ResponseEntity.ok("Thêm mới blog  thành công!");
        }else {
            return ResponseEntity.ok("Thêm mới blog  thất bại!");
        }
    }

    // ------------------------ Update Blog --------------------------------

    @PutMapping("updateBlog/{blogId}")
    public ResponseEntity<?> updateBlog(@RequestBody BlogRequest blogRequest,@PathVariable("blogId")int blogId) {
        blogRequest.setBlogId(blogId);
        boolean check = blogService.saveOrUpdate(blogRequest);
        if (check){
            return ResponseEntity.ok("Update  blog  thành công!");
        }else {
            return ResponseEntity.ok("Update blog  thất bại!");
        }
    }

    // ------------------------ Xóa Blog --------------------------------

    @PutMapping("deleteBlog/{blogId}")
    public ResponseEntity<?> deleteBlog(@PathVariable("blogId")int blogId) {
        boolean check = blogService.deleteBlog(blogId);
        if (check){
            return ResponseEntity.ok("Xóa  blog  thành công!");
        }else {
            return ResponseEntity.ok("Xóa blog  thất bại!");
        }
    }


    // ------------------------ Search By Name --------------------------------

    @GetMapping("searchByName")
    public ResponseEntity<?> searchByName(@RequestParam("searchName")String name) {
        List<BlogResponse> list = blogService.searchByName(name);
        return ResponseEntity.ok(list);
    }

    // ------------------------ Find By Status --------------------------------

    @GetMapping("findByStatus")
    public ResponseEntity<?> findByStatus(@RequestParam("status")boolean status) {
        List<BlogResponse> list = blogService.findByStatus(status);
        return ResponseEntity.ok(list);
    }


    // ------------------------ Find By BlogCatalogId --------------------------------

    @GetMapping("findByCatalogId/{blogCatalogId}")
    public ResponseEntity<?> findByCatalogId(@PathVariable("blogCatalogId")int id) {
        List<BlogResponse> list = blogService.findByCatalogId(id);
        return ResponseEntity.ok(list);
    }




}
