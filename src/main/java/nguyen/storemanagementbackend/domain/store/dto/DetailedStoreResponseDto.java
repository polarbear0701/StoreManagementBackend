package nguyen.storemanagementbackend.domain.store.dto;

import lombok.*;
import lombok.experimental.SuperBuilder;
import nguyen.storemanagementbackend.common.dto.StoreResponseBasedDto;
import nguyen.storemanagementbackend.domain.store.model.StockModel;
import nguyen.storemanagementbackend.domain.store.model.StoreSlotModel;
import nguyen.storemanagementbackend.domain.user.model.Users;

import java.util.List;
import java.util.Set;

@EqualsAndHashCode(callSuper = true)
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class DetailedStoreResponseDto extends StoreResponseBasedDto {

    private Users storeOwner;
    private Set<Users> storeStaff;
    private Set<Users> storeAdmins;
    private List<StockModel> storeStocks;
    private List<StoreSlotModel> slots;
}
