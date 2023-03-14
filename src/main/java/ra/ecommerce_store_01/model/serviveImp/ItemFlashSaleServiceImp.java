package ra.ecommerce_store_01.model.serviveImp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ra.ecommerce_store_01.model.entity.ItemFlashSale;
import ra.ecommerce_store_01.model.repository.ItemFlashSaleRepository;
import ra.ecommerce_store_01.model.service.ItemFlashSaleService;

@Service
public class ItemFlashSaleServiceImp implements ItemFlashSaleService {
    @Autowired
    private ItemFlashSaleRepository itemFlashSaleRepository;
    @Override
    public ItemFlashSale save(ItemFlashSale itemFlashSale) {
        return itemFlashSaleRepository.save(itemFlashSale);
    }

    @Override
    public void delete(int id) {
        itemFlashSaleRepository.deleteById(id);
    }

    @Override
    public ItemFlashSale findById(int id) {
        return itemFlashSaleRepository.findById(id).get();
    }
}
