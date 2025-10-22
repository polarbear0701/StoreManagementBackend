package nguyen.storemanagementbackend.domain.store.dto.storeinfo.request;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import nguyen.storemanagementbackend.domain.address.dto.NewAddressRequestDto;

@Data
@Setter
@Getter
public class NewStoreInfoRequestDto {
    private String storeName;
    private String storeDescription;
    private NewAddressRequestDto storeAddress;
}
