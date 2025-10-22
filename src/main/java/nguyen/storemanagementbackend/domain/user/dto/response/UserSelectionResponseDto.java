package nguyen.storemanagementbackend.domain.user.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nguyen.storemanagementbackend.common.enumeration.Role;

/**
 * DTO used only for user selection (e.g., dropdowns). Not used for any other purpose.
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserSelectionResponseDto {
    private String userId;
    private String userName;
    private Role role;
}
