package nguyen.storemanagementbackend.common.generic;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GenericExceptionResponseDto {
    private int status;
    private String message;
    private long timestamp;
}
