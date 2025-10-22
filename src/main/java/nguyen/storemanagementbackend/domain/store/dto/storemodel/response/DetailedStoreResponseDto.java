package nguyen.storemanagementbackend.domain.store.dto.storemodel.response;

import lombok.*;
import lombok.experimental.SuperBuilder;
import nguyen.storemanagementbackend.common.dto.StoreResponseBasedDto;
import nguyen.storemanagementbackend.domain.store.model.StockModel;
import nguyen.storemanagementbackend.domain.store.model.StoreSlotModel;
import nguyen.storemanagementbackend.domain.user.dto.response.DetailedUserResponseDto;
import nguyen.storemanagementbackend.domain.user.dto.response.UserSelectionResponseDto;

import java.util.List;
import java.util.Set;

@EqualsAndHashCode(callSuper = true)
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class DetailedStoreResponseDto extends StoreResponseBasedDto {

    private DetailedUserResponseDto storeOwner;
    private Set<UserSelectionResponseDto> storeStaff;
    private Set<UserSelectionResponseDto> storeAdmins;
    private List<StockModel> storeStocks;
    private List<StoreSlotModel> slots;
}
