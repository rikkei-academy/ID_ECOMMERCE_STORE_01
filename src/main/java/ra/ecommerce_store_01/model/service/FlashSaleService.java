package ra.ecommerce_store_01.model.service;

import ra.ecommerce_store_01.model.entity.FlashSale;

import java.util.Date;

public interface FlashSaleService {
    FlashSale save(FlashSale flashSale);
    FlashSale findById(int id);
   FlashSale findFlashSaleByStarTime(Date starTime);
}
