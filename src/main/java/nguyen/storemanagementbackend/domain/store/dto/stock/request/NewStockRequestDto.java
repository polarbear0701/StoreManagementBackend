package nguyen.storemanagementbackend.domain.store.dto.stock.request;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NewStockRequestDto {

	private UUID storeId;
	private String stockName;
	private int stockQuantity;
	private String stockDescription;
}