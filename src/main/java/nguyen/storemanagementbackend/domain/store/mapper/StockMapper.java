package nguyen.storemanagementbackend.domain.store.mapper;

import nguyen.storemanagementbackend.common.dto.StockResponseBasedDto;
import nguyen.storemanagementbackend.common.mapper.UuidMapper;
import nguyen.storemanagementbackend.domain.store.dto.stock.request.NewStockRequestDto;
import nguyen.storemanagementbackend.domain.store.model.StockModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = { UuidMapper.class })
public interface StockMapper {
    StockResponseBasedDto toStockResponseBasedDto(StockModel stockModel);

    @Mapping(target = "stockId", ignore = true)
    @Mapping(target = "store", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "updatedBy", ignore = true)
    @Mapping(target = "isActive", constant = "true")
    StockModel toStockModel(NewStockRequestDto stockRequest);
}
