package ra.ecommerce_store_01.payload.request;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class ConfirmOrder {
    List<Integer> listOrder = new ArrayList<>();
}
