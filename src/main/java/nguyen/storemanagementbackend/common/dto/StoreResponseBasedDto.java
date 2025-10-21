package nguyen.storemanagementbackend.common.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.UUID;

@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class StoreResponseBasedDto {
    private UUID storeId;
    private StoreInfoResponseBasedDto storeInfo;
}
