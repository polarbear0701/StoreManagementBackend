package nguyen.storemanagementbackend.domain.user.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class RegisterRequestDto {
    private String email;
    private String password;
    private String userName;
    private int userAge;
}
