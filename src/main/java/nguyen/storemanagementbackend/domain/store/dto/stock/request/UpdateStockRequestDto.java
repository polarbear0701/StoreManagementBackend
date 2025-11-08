package nguyen.storemanagementbackend.domain.store.dto.stock.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateStockRequestDto {

    private String stockName;
    private Integer stockQuantity;
    private String stockDescription;
    private Boolean isActive;
}
