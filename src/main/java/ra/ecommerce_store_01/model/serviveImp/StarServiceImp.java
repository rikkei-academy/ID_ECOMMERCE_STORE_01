package ra.ecommerce_store_01.model.serviveImp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ra.ecommerce_store_01.model.entity.Star;
import ra.ecommerce_store_01.model.repository.StarRepository;
import ra.ecommerce_store_01.model.service.StarService;

import java.util.List;
@Service
public class StarServiceImp implements StarService {
    @Autowired
    StarRepository starRepository;
    @Override
    public Star save(Star star) {
        return starRepository.save(star);
    }

    @Override
    public List<Star> countStar(int productId) {
        return starRepository.findAllByProduct_ProductId(productId);
    }
}
