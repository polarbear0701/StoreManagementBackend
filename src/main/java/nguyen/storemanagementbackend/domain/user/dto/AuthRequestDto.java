package nguyen.storemanagementbackend.domain.user.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class AuthRequestDto {
    private String email;
    private String password;
}
