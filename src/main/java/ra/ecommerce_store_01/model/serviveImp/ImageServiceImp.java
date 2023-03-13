package ra.ecommerce_store_01.model.serviveImp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ra.ecommerce_store_01.model.entity.Image;
import ra.ecommerce_store_01.model.repository.ImageRepository;
import ra.ecommerce_store_01.model.service.ImageService;
@Service
public class ImageServiceImp implements ImageService {
    @Autowired
    ImageRepository imageRepository;
    @Override
    public Image saveOrUpdate(Image image) {
        return imageRepository.save(image);
    }

    @Override
    public void delete(int imageId) {
        imageRepository.deleteById(imageId);
    }
}
