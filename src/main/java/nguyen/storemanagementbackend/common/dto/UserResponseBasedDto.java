package nguyen.storemanagementbackend.common.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import nguyen.storemanagementbackend.common.enumeration.Role;

import java.time.LocalDateTime;
import java.util.UUID;


@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class UserResponseBasedDto {

    private UUID userId;
    private String email;
    private String userName;
    private Role role;
    private LocalDateTime createdAt;
}
