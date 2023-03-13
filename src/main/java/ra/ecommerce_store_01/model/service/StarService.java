package ra.ecommerce_store_01.model.service;

import ra.ecommerce_store_01.model.entity.Star;

import java.util.List;

public interface StarService {
    Star save(Star star);
    List<Star> countStar(int productId);
}
