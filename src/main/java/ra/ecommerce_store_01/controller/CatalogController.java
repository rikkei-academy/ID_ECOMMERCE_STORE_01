package ra.ecommerce_store_01.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    CatalogService catalogService;

    @GetMapping
    public List<Catalog> getAllCatalog() {
        return catalogService.findAll();
    }
    @GetMapping("{catalogID}")
    public Catalog findById(@PathVariable("catalogID") int catalogId) {
        return catalogService.findById(catalogId);
    }
    @PostMapping
    public ResponseEntity<?> create(@RequestBody Catalog catalog){
        catalog.setCatalogStatus(true);
        catalogService.saveOrUpdate(catalog);
        return ResponseEntity.ok(new MessageResponse("Add new catalog successfully!"));
    }
    @PutMapping("{catalogID}")
    public ResponseEntity<?> updateCatalog(@PathVariable("catalogID") int catalogId, @RequestBody Catalog catalog) {
        Catalog catalogUpdate = catalogService.findById(catalogId);
        catalogUpdate.setCatalogName(catalog.getCatalogName());
        catalogService.saveOrUpdate(catalogUpdate);
        return ResponseEntity.ok(new MessageResponse("Update catalog successfully"));
    }
    @GetMapping("lockCatalog/{catalogID}")
    public ResponseEntity<?> lockCatalog(@PathVariable("catalogID") int catalogId) {
        Catalog catalog = catalogService.findById(catalogId);
        catalog.setCatalogStatus(false);
        catalogService.saveOrUpdate(catalog);
        return ResponseEntity.ok(new MessageResponse("Lock catalog successfully"));
    }
    @GetMapping("unlockCatalog/{catalogID}")
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
            @RequestParam(defaultValue = "3") int size){
        Pageable pageable = PageRequest.of(page,size);
        Page<Catalog> catalogs = catalogService.getPaging(pageable);
        Map<String,Object> data = new HashMap<>();
        data.put("catalog",catalogs.getContent());
        data.put("totalPages",catalogs.getTotalPages());
        return  new ResponseEntity<>(data, HttpStatus.OK);
    }
    @GetMapping("sortByNameDesc")
    public ResponseEntity<?> getPagingAndSortByNameDesc(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "3") int size){
        Sort.Order order;
        order=new Sort.Order(Sort.Direction.DESC,"catalogName");
        Pageable pageable = PageRequest.of(page,size,Sort.by(order));
        Page<Catalog> catalogs = catalogService.getPaging(pageable);
        Map<String,Object> data = new HashMap<>();
        data.put("catalog",catalogs.getContent());
        data.put("totalItems",catalogs.getTotalElements());
        data.put("totalPages",catalogs.getTotalPages());
        return  new ResponseEntity<>(data, HttpStatus.OK);
    }
    @GetMapping("sortByNameAsc")
    public ResponseEntity<?> getPagingAndSortByNameAsc(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "3") int size){
        Sort.Order order;
        order=new Sort.Order(Sort.DEFAULT_DIRECTION,"catalogName");
        Pageable pageable = PageRequest.of(page,size,Sort.by(order));
        Page<Catalog> catalogs = catalogService.getPaging(pageable);
        Map<String,Object> data = new HashMap<>();
        data.put("catalog",catalogs.getContent());
        data.put("total",catalogs.getSize());
        data.put("totalItems",catalogs.getTotalElements());
        data.put("totalPages",catalogs.getTotalPages());
        return  new ResponseEntity<>(data, HttpStatus.OK);
    }
    @GetMapping("sortByIdDesc")
    public ResponseEntity<?> getPagingAndSortByIdDesc(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "3") int size){
        Sort.Order order;
        order=new Sort.Order(Sort.Direction.DESC,"catalogId");
        Pageable pageable = PageRequest.of(page,size,Sort.by(order));
        Page<Catalog> catalogs = catalogService.getPaging(pageable);
        Map<String,Object> data = new HashMap<>();
        data.put("catalog",catalogs.getContent());
        data.put("totalItems",catalogs.getTotalElements());
        data.put("totalPages",catalogs.getTotalPages());
        return  new ResponseEntity<>(data, HttpStatus.OK);
    }
    @GetMapping("sortByIdAsc")
    public ResponseEntity<?> getPagingAndSortByIdAsc(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "3") int size){
        Sort.Order order;
        order=new Sort.Order(Sort.DEFAULT_DIRECTION,"catalogId");
        Pageable pageable = PageRequest.of(page,size,Sort.by(order));
        Page<Catalog> catalogs = catalogService.getPaging(pageable);
        Map<String,Object> data = new HashMap<>();
        data.put("catalog",catalogs.getContent());
        data.put("total",catalogs.getSize());
        data.put("totalItems",catalogs.getTotalElements());
        data.put("totalPages",catalogs.getTotalPages());
        return  new ResponseEntity<>(data, HttpStatus.OK);
    }
}
