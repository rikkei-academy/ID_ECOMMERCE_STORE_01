package ra.ecommerce_store_01.model.service;

import ra.ecommerce_store_01.model.entity.Image;

public interface ImageService {
    Image saveOrUpdate(Image image);
    void delete(int imageId);
}
