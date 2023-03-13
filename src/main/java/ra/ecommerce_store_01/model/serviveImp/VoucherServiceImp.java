package ra.ecommerce_store_01.model.serviveImp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ra.ecommerce_store_01.model.entity.Voucher;
import ra.ecommerce_store_01.model.repository.VoucherRepository;
import ra.ecommerce_store_01.model.service.VoucherService;

import java.util.List;
@Service
public class VoucherServiceImp implements VoucherService {
    @Autowired
    VoucherRepository voucherRepository;
    @Override
    public List<Voucher> findAllVoucher() {
        return voucherRepository.findAll();
    }

    @Override
    public Voucher saveOrUpdate(Voucher voucher) {
        return voucherRepository.save(voucher);
    }

    @Override
    public Voucher findById(int voucherId) {
        return voucherRepository.findById(voucherId).get();
    }

    @Override
    public void deleteVoucher(int voucherId) {
        voucherRepository.deleteById(voucherId);
    }

    @Override
    public Page<Voucher> getPaging(Pageable pageable) {
        return voucherRepository.findAll(pageable);
    }
}
