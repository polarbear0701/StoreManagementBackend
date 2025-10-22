package nguyen.storemanagementbackend.domain.user.dto.response;

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
