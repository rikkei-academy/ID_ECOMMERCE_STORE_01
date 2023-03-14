package ra.ecommerce_store_01.model.serviveImp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ra.ecommerce_store_01.model.entity.FlashSale;
import ra.ecommerce_store_01.model.repository.FlashSaleRepository;
import ra.ecommerce_store_01.model.service.FlashSaleService;

import java.util.Date;

@Service
public class FlashSaleServiceImp implements FlashSaleService {
    @Autowired
    private FlashSaleRepository flashSaleRepository;
    @Override
    public FlashSale save(FlashSale flashSale) {
        return flashSaleRepository.save(flashSale);
    }

    @Override
    public FlashSale findById(int id) {
        return flashSaleRepository.findById(id).get();
    }

    @Override
    public FlashSale findFlashSaleByStarTime(Date starTime) {
        return flashSaleRepository.findFlashSaleByStarTime(starTime);
    }
}
