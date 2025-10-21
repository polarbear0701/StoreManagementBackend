package nguyen.storemanagementbackend.domain.user.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import nguyen.storemanagementbackend.common.dto.AddressResponseBasedDto;
import nguyen.storemanagementbackend.common.dto.UserResponseBasedDto;

@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
public class DetailedUserDto extends UserResponseBasedDto{

    private AddressResponseBasedDto address;
    private int userAge;
}
