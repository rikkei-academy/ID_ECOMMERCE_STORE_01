package ra.ecommerce_store_01.model.repository;

import lombok.Data;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ra.ecommerce_store_01.model.entity.FlashSale;

import java.util.Date;

@Repository
public interface FlashSaleRepository extends JpaRepository<FlashSale,Integer> {
    FlashSale findFlashSaleByStarTime(Date starTime);
}
