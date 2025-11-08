package nguyen.storemanagementbackend.domain.store.mapper;

import java.util.List;
import nguyen.storemanagementbackend.common.dto.StockResponseBasedDto;
import nguyen.storemanagementbackend.common.mapper.UuidMapper;
import nguyen.storemanagementbackend.domain.store.dto.stock.request.NewStockRequestDto;
import nguyen.storemanagementbackend.domain.store.dto.stock.request.UpdateStockRequestDto;
import nguyen.storemanagementbackend.domain.store.dto.stock.response.DetailedStockResponseDto;
import nguyen.storemanagementbackend.domain.store.model.StockModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring", uses = { UuidMapper.class })
public interface StockMapper {
    StockResponseBasedDto toStockResponseBasedDto(StockModel stockModel);

    DetailedStockResponseDto toDetailedStockResponseDto(StockModel stockModel);

    List<StockResponseBasedDto> toStockResponseBasedDtoList(
        List<StockModel> stockModels
    );

    List<DetailedStockResponseDto> toDetailedStockResponseDtoList(
        List<StockModel> stockModels
    );

    @Mapping(target = "stockId", ignore = true)
    @Mapping(target = "store", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "updatedBy", ignore = true)
    @Mapping(target = "isActive", constant = "true")
    StockModel toStockModel(NewStockRequestDto stockRequest);

    @Mapping(target = "stockId", ignore = true)
    @Mapping(target = "store", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "updatedBy", ignore = true)
    void updateStockFromDto(
        UpdateStockRequestDto updateRequest,
        @MappingTarget StockModel stockModel
    );
}
