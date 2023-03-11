package ra.ecommerce_store_01.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ra.ecommerce_store_01.model.entity.Blog;
import ra.ecommerce_store_01.model.entity.Catalog;
import ra.ecommerce_store_01.model.entity.Product;
import ra.ecommerce_store_01.model.entity.Voucher;
import ra.ecommerce_store_01.model.service.ProductService;
import ra.ecommerce_store_01.model.service.VoucherService;
import ra.ecommerce_store_01.payload.request.VoucherRequest;
import ra.ecommerce_store_01.payload.respone.MessageResponse;
import ra.ecommerce_store_01.payload.respone.VoucherResponse;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "http://localhost:8080")
@RestController
@RequestMapping("/api/v1/voucher")
public class VoucherController {
    @Autowired
    VoucherService voucherService;
    @Autowired
    ProductService productService;

    @GetMapping
    public List<VoucherResponse> findAllVoucher() {
        List<VoucherResponse> listVoucher = new ArrayList<>();
        List<Voucher> vouchers = voucherService.findAllVoucher();
        for (Voucher voucher: vouchers) {
            VoucherResponse voucherResponse = new VoucherResponse();
            voucherResponse.setDiscount(voucher.getDiscount());
            voucherResponse.setQuantity(voucher.getQuantity());
            voucherResponse.setProductName(voucher.getProduct().getProductName());
            voucherResponse.setVoucherId(voucher.getVoucherId());
            listVoucher.add(voucherResponse);
        }
        return listVoucher;
    }

    @GetMapping("{voucherId}")
    public VoucherResponse findById(@PathVariable("voucherId") int voucherId) {
        Voucher voucher = voucherService.findById(voucherId);
        VoucherResponse voucherResponse = new VoucherResponse();
        voucherResponse.setVoucherId(voucher.getVoucherId());
        voucherResponse.setQuantity(voucher.getQuantity());
        voucherResponse.setProductName(voucher.getProduct().getProductName());
        voucherResponse.setDiscount(voucher.getDiscount());
        return voucherResponse;
    }

    @PostMapping("createVoucher")
    public ResponseEntity<?> createVoucher(@RequestBody VoucherRequest voucherRequest) {
        try {
            Product product = productService.findById(voucherRequest.getProductId());
            Voucher voucher = new Voucher();
            voucher.setDiscount(voucherRequest.getDiscount());
            voucher.setQuantity(voucherRequest.getQuantity());
            voucher.setProduct(product);
            voucherService.saveOrUpdate(voucher);
            return ResponseEntity.ok(new MessageResponse("Create voucher successfully!"));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new MessageResponse("Create voucher failed!"));
        }
    }

    @PutMapping("updateVoucher/{voucherId}")
    public ResponseEntity<?> updateVoucher(@PathVariable("voucherId") int voucherId, @RequestBody VoucherRequest voucherRequest) {
        try {
            Product product = productService.findById(voucherRequest.getProductId());
            Voucher voucherUpdate = voucherService.findById(voucherId);
            voucherUpdate.setDiscount(voucherRequest.getDiscount());
            voucherUpdate.setQuantity(voucherRequest.getQuantity());
            voucherUpdate.setProduct(product);
            voucherService.saveOrUpdate(voucherUpdate);
            return ResponseEntity.ok(new MessageResponse("Update voucher successfully!"));
        }catch (Exception e) {
            return ResponseEntity.badRequest().body(new MessageResponse("Update voucher failed!"));
        }
    }

    @DeleteMapping("{voucherId}")
    public ResponseEntity<?> deleteVoucher(@PathVariable("voucherId") int voucherId) {
        try {
            voucherService.deleteVoucher(voucherId);
            return ResponseEntity.ok(new MessageResponse("Delete voucher successfully!"));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new MessageResponse("Delete voucher failed!"));
        }
    }

    @GetMapping("paging")
    public ResponseEntity<?> getPaging(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "3") int size){
        Pageable pageable = PageRequest.of(page,size);
        Page<Voucher> vouchers = voucherService.getPaging(pageable);
        List<VoucherResponse> listVoucher = new ArrayList<>();
        for (Voucher voucher: vouchers) {
            VoucherResponse voucherResponse = new VoucherResponse();
            voucherResponse.setDiscount(voucher.getDiscount());
            voucherResponse.setQuantity(voucher.getQuantity());
            voucherResponse.setProductName(voucher.getProduct().getProductName());
            voucherResponse.setVoucherId(voucher.getVoucherId());
            listVoucher.add(voucherResponse);
        }
        Map<String,Object> data = new HashMap<>();
        data.put("listVoucher",listVoucher);
        data.put("totalPages",vouchers.getTotalPages());
        return new ResponseEntity<>(data, HttpStatus.OK);
    }

    @GetMapping("filterByDiscount")
    public List<VoucherResponse> filterByDiscount(@RequestParam("from") float from, @RequestParam("to") float to) {
        List<VoucherResponse> listVoucher = new ArrayList<>();
        for (Voucher voucher: voucherService.findAllVoucher()) {
            if (voucher.getDiscount()>=from && voucher.getDiscount()<=to) {
                VoucherResponse voucherResponse = new VoucherResponse();
                voucherResponse.setVoucherId(voucher.getVoucherId());
                voucherResponse.setDiscount(voucher.getDiscount());
                voucherResponse.setQuantity(voucher.getQuantity());
                voucherResponse.setProductName(voucher.getProduct().getProductName());
                listVoucher.add(voucherResponse);
            }
        }
        return listVoucher;
    }
}
