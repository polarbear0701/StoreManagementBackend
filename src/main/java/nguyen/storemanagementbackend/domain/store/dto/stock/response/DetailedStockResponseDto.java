package nguyen.storemanagementbackend.domain.store.dto.stock.response;

import java.time.LocalDateTime;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nguyen.storemanagementbackend.common.dto.StoreResponseBasedDto;
import nguyen.storemanagementbackend.common.dto.UserResponseBasedDto;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DetailedStockResponseDto {

    private UUID stockId;
    private StoreResponseBasedDto store;
    private String stockName;
    private int stockQuantity;
    private String stockDescription;
    private LocalDateTime updatedAt;
    private UserResponseBasedDto updatedBy;
    private boolean isActive;
}
