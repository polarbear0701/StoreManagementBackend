package nguyen.storemanagementbackend.common.basedto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AddressResponseBasedDto {
    private String addressNumber;
    private String addressWard;
    private String addressDistrict;
    private String addressCity;
    private String addressCountry;
}
