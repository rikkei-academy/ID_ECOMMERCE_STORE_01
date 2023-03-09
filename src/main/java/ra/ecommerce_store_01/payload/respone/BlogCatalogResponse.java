package ra.ecommerce_store_01.payload.respone;

import lombok.Data;
import org.w3c.dom.stylesheets.LinkStyle;

import java.util.ArrayList;
import java.util.List;

@Data
public class BlogCatalogResponse {
    private int BlogCatalogId;
    private String BlogCatalogName;
    private boolean BlogCatalogStatus;
    List<BlogResponse> listBlog = new ArrayList<>();
}
