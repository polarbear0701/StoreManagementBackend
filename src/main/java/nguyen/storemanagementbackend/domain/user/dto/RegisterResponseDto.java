package nguyen.storemanagementbackend.domain.user.dto;

import lombok.Builder;
import lombok.Data;
import nguyen.storemanagementbackend.common.enumeration.Role;

@Data
@Builder
public class RegisterResponseDto {

	private String userId;
	private String email;
	private String userName;
	private Role role;
}