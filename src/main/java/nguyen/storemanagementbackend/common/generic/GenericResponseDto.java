package nguyen.storemanagementbackend.common.generic;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class GenericResponseDto<T> {

    private int status;
    private String message;
    private T data;
}
