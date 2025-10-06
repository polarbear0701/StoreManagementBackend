package nguyen.storemanagementbackend.domain.user.dto;

import lombok.*;

@Data
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class AuthResponseDto {
    private String token;
    private long expiresIn;
}
