package nguyen.storemanagementbackend.domain.user.dto.request;

import lombok.Data;
import lombok.experimental.SuperBuilder;

@Data
public class UpdateUserRequestDto {
    private String userName;
    private int age;
}
