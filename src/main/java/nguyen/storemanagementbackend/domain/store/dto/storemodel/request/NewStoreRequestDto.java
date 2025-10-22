package nguyen.storemanagementbackend.domain.store.dto.storemodel.request;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import nguyen.storemanagementbackend.domain.store.dto.storeinfo.request.NewStoreInfoRequestDto;

import java.util.UUID;


@Data
@Setter
@Getter
public class NewStoreRequestDto {

	private UUID storeOwnerId;
	private NewStoreInfoRequestDto storeInfo;

}