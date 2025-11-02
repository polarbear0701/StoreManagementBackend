package nguyen.storemanagementbackend.domain.user.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateUserRequestDto {
    private String userName;
    private Integer userAge;
}
