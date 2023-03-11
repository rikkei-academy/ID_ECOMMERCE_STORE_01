package ra.ecommerce_store_01.model.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ra.ecommerce_store_01.model.entity.Voucher;

import java.util.List;

public interface VoucherService {
    List<Voucher> findAllVoucher();
    Voucher saveOrUpdate(Voucher voucher);
    Voucher findById(int voucherId);
    void deleteVoucher(int voucherId);
    Page<Voucher> getPaging(Pageable pageable);
}
