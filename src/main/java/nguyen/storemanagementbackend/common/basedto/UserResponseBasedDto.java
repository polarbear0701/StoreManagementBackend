package nguyen.storemanagementbackend.common.basedto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;



@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class UserResponseBasedDto {

    private String userId;
    private String userEmail;
    private String userName;
    private String role;
}
