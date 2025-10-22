package nguyen.storemanagementbackend.domain.store.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;


@Data
@Setter
@Getter
public class NewStoreRequestDto {

	private UUID storeOwnerId;
	private NewStoreInfoDto storeInfo;

}