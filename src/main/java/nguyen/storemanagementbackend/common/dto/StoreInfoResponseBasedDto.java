package nguyen.storemanagementbackend.common.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class StoreInfoResponseBasedDto {
    private String storeName;
    private String storeDescription;
    private AddressResponseBasedDto storeAddress;
}
