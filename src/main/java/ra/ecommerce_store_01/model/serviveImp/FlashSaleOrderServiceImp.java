package ra.ecommerce_store_01.model.serviveImp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ra.ecommerce_store_01.model.entity.FlashSaleOrder;
import ra.ecommerce_store_01.model.repository.FlashSaleOrderRepository;
import ra.ecommerce_store_01.model.service.FlashSaleOrderService;
@Service
public class FlashSaleOrderServiceImp implements FlashSaleOrderService {
    @Autowired
    private FlashSaleOrderRepository flashSaleOrderRepository;
    @Override
    public FlashSaleOrder save(FlashSaleOrder flashSaleOrder) {
        return flashSaleOrderRepository.save(flashSaleOrder);
    }
}
