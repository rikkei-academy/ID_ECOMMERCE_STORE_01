package ra.ecommerce_store_01.model.service;

import ra.ecommerce_store_01.model.entity.ItemFlashSale;

public interface ItemFlashSaleService {
    ItemFlashSale save(ItemFlashSale itemFlashSale);
    void delete(int id);
    ItemFlashSale findById(int id);
}
