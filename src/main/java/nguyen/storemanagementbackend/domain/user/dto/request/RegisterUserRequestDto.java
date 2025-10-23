package nguyen.storemanagementbackend.domain.user.dto.request;

import lombok.*;
import lombok.experimental.SuperBuilder;
import nguyen.storemanagementbackend.domain.address.dto.NewAddressRequestDto;
@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class RegisterUserRequestDto {
    private String email;
    private String password;
    private String userName;
    private int userAge;

    private NewAddressRequestDto address;
}
