package nguyen.storemanagementbackend.common.dto;

import lombok.*;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class AddressResponseBasedDto {
    private String addressNumber;
    private String addressWard;
    private String addressDistrict;
    private String addressCity;
    private String addressCountry;
}
