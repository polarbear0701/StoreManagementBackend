package nguyen.storemanagementbackend.domain.store.mapper;

import nguyen.storemanagementbackend.common.dto.StoreResponseBasedDto;
import nguyen.storemanagementbackend.common.mapper.UuidMapper;
import nguyen.storemanagementbackend.domain.store.dto.storemodel.request.NewStoreRequestDto;
import nguyen.storemanagementbackend.domain.store.dto.storemodel.response.DetailedStoreResponseDto;
import nguyen.storemanagementbackend.domain.store.model.StoreModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {UuidMapper.class, StoreInfoMapper.class})
public interface StoreMapper {

    StoreResponseBasedDto toStoreResponseBasedDto(StoreModel store);
    DetailedStoreResponseDto toDetailedStoreResponseDto(StoreModel store);

    @Mapping(target = "storeId", ignore = true)
    @Mapping(target = "storeOwner", ignore = true)
    @Mapping(target = "staff", ignore = true)
    @Mapping(target = "stocks", ignore = true)
    @Mapping(target = "slots", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "admins", ignore = true)
    StoreModel toEntity(NewStoreRequestDto newStoreRequestDto);
}
