package ra.ecommerce_store_01.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ra.ecommerce_store_01.model.entity.Voucher;

@Repository
public interface VoucherRepository extends JpaRepository<Voucher,Integer> {
}
