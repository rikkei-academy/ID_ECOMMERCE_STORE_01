package ra.ecommerce_store_01.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> createBlog(@RequestBody BlogRequest blogRequest) {
        blogRequest.setBlogStatus(true);
        blogRequest.setCreateDate(new Date());
        boolean check = blogService.saveOrUpdate(blogRequest);
        if (check){
            return ResponseEntity.ok("Th??m m???i blog  th??nh c??ng!");
        }else {
            return ResponseEntity.ok("Th??m m???i blog  th???t b???i!");
        }
    }

    // ------------------------ Update Blog --------------------------------

    @PutMapping("updateBlog/{blogId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> updateBlog(@RequestBody BlogRequest blogRequest,@PathVariable("blogId")int blogId) {
        blogRequest.setBlogId(blogId);
        boolean check = blogService.update(blogRequest);
        if (check){
            return ResponseEntity.ok("Update  blog  th??nh c??ng!");
        }else {
            return ResponseEntity.ok("Update blog  th???t b???i!");
        }
    }

    // ------------------------ X??a Blog --------------------------------

    @PatchMapping("deleteBlog/{blogId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> deleteBlog(@PathVariable("blogId")int blogId) {
        boolean check = blogService.deleteBlog(blogId);
        if (check){
            return ResponseEntity.ok("X??a  blog  th??nh c??ng!");
        }else {
            return ResponseEntity.ok("X??a blog  th???t b???i!");
        }
    }


    // ------------------------ Search By Name --------------------------------

    @GetMapping("searchByName")
    public ResponseEntity<?> searchByName(@RequestParam("searchName")String name,
                                          @RequestParam(defaultValue = "0") int page,
                                          @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Map<String,Object> list = blogService.searchByName(name,pageable);
        return ResponseEntity.ok(list);
    }

    // ------------------------ Find By Status --------------------------------

    @GetMapping("findByStatus")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> findByStatus(@RequestParam("status")boolean status,
                                          @RequestParam(defaultValue = "0") int page,
                                          @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Map<String,Object> list = blogService.findByStatus(status,pageable);
        return ResponseEntity.ok(list);
    }


    // ------------------------ Find By BlogCatalogId --------------------------------

    @GetMapping("findByCatalogId/{blogCatalogId}")
    public ResponseEntity<?> findByCatalogId(@PathVariable("blogCatalogId")int id,
                                             @RequestParam(defaultValue = "0") int page,
                                             @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Map<String,Object> list = blogService.findByCatalogId(id,pageable);
        return ResponseEntity.ok(list);
    }




}
