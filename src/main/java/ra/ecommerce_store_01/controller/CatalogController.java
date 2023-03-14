package ra.ecommerce_store_01.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ra.ecommerce_store_01.model.entity.Catalog;
import ra.ecommerce_store_01.model.service.CatalogService;
import ra.ecommerce_store_01.payload.respone.MessageResponse;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "http://localhost:8080")
@RestController
@RequestMapping("/api/v1/catalog")
public class CatalogController {
    @Autowired
    private CatalogService catalogService;

    @GetMapping
    public List<Catalog> getAllCatalog() {
        return catalogService.findAll();
    }
    @GetMapping("{catalogID}")
    public Catalog findById(@PathVariable("catalogID") int catalogId) {
        return catalogService.findById(catalogId);
    }
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> create(@RequestBody Catalog catalog){
        try {
            catalog.setCatalogStatus(true);
            catalogService.saveOrUpdate(catalog);
            return ResponseEntity.ok(new MessageResponse("Add new catalog successfully!"));
        }catch (Exception e) {
            return ResponseEntity.badRequest().body("Create Failed!");
        }
    }
    @PutMapping("{catalogID}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> updateCatalog(@PathVariable("catalogID") int catalogId, @RequestBody Catalog catalog) {
        try {
            Catalog catalogUpdate = catalogService.findById(catalogId);
            catalogUpdate.setCatalogName(catalog.getCatalogName());
            catalogService.saveOrUpdate(catalogUpdate);
            return ResponseEntity.ok(new MessageResponse("Update catalog successfully"));
        }catch (Exception e) {
            return ResponseEntity.badRequest().body("Update Failed!");
        }
    }
    @GetMapping("lockCatalog/{catalogID}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> lockCatalog(@PathVariable("catalogID") int catalogId) {
        Catalog catalog = catalogService.findById(catalogId);
        catalog.setCatalogStatus(false);
        catalogService.saveOrUpdate(catalog);
        return ResponseEntity.ok(new MessageResponse("Lock catalog successfully"));
    }
    @GetMapping("unlockCatalog/{catalogID}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> unlockCatalog(@PathVariable("catalogID") int catalogId) {
        Catalog catalog = catalogService.findById(catalogId);
        catalog.setCatalogStatus(true);
        catalogService.saveOrUpdate(catalog);
        return ResponseEntity.ok(new MessageResponse("Unlock catalog successfully"));
    }
    @GetMapping("searchCatalog")
    public List<Catalog> searchCatalog(@RequestParam("catalogName") String catalogName) {
        return catalogService.searchByName(catalogName);
    }
    @GetMapping("paging")
    public ResponseEntity<?> getPaging(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size){
        Pageable pageable = PageRequest.of(page,size);
        Page<Catalog> catalogs = catalogService.getPaging(pageable);
        Map<String,Object> data = new HashMap<>();
        data.put("catalog",catalogs.getContent());
        data.put("totalPages",catalogs.getTotalPages());
        return  new ResponseEntity<>(data, HttpStatus.OK);
    }
    @GetMapping("sortByName")
    public ResponseEntity<?> getPagingAndSortByName(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam String direction){
        Sort.Order order;
        if(direction.equals("asc")){
            order = new Sort.Order(Sort.Direction.ASC,"catalogName");
        }else {
            order = new Sort.Order(Sort.Direction.DESC,"catalogName");
        }
        Pageable pageable = PageRequest.of(page, size,Sort.by(order));
        Page<Catalog> catalogs = catalogService.getPaging(pageable);
        Map<String,Object> data = new HashMap<>();
        data.put("catalog",catalogs.getContent());
        data.put("totalItems",catalogs.getTotalElements());
        data.put("totalPages",catalogs.getTotalPages());
        return new ResponseEntity<>(data,HttpStatus.OK);
    }

    @GetMapping("sortById")
    public ResponseEntity<?> getPagingAndSortById(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam String direction){
        Sort.Order order;
        if(direction.equals("asc")){
            order = new Sort.Order(Sort.Direction.ASC,"catalogId");
        }else {
            order = new Sort.Order(Sort.Direction.DESC,"catalogId");
        }
        Pageable pageable = PageRequest.of(page, size,Sort.by(order));
        Page<Catalog> catalogs = catalogService.getPaging(pageable);
        Map<String,Object> data = new HashMap<>();
        data.put("catalog",catalogs.getContent());
        data.put("totalItems",catalogs.getTotalElements());
        data.put("totalPages",catalogs.getTotalPages());
        return new ResponseEntity<>(data,HttpStatus.OK);
    }
}
