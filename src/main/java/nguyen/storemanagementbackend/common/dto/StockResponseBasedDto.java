package nguyen.storemanagementbackend.common.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StockResponseBasedDto {

	private String stockName;
	private int stockQuantity;
	private String stockDescription;
	private boolean isActive;
}