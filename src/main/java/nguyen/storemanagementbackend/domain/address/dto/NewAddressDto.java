package nguyen.storemanagementbackend.domain.address.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class NewAddressDto {
    private String addressNumber;
    private String addressWard;
    private String addressDistrict;
    private String addressCity;
    private String addressCountry = "Vietnam";
}
