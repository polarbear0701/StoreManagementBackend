package nguyen.storemanagementbackend.domain.store.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import nguyen.storemanagementbackend.domain.address.dto.NewAddressDto;

@Data
@Setter
@Getter
public class NewStoreInfoDto {
    private String storeName;
    private String storeDescription;
    private NewAddressDto storeAddress;
}
