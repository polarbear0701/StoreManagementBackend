package nguyen.storemanagementbackend.domain.user.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;


/**
 * <p>DTO request for changing password</p>
 * <ul>
 *     <li>String newPassword - New Password (raw String)</li>
 *     <li>UUID currentUserId - Current user ID, preferably fetched from AuthenticationPrincipal</li>
 * </ul>
 *
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChangePasswordRequestDto {
    private String newPassword;
    private UUID currentUserId;
}
