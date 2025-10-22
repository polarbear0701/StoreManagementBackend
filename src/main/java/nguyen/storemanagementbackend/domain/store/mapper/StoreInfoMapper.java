package nguyen.storemanagementbackend.domain.store.mapper;

import nguyen.storemanagementbackend.common.dto.StoreInfoResponseBasedDto;
import nguyen.storemanagementbackend.common.mapper.UuidMapper;
import nguyen.storemanagementbackend.domain.address.mapper.AddressMapper;
import nguyen.storemanagementbackend.domain.store.dto.storeinfo.request.NewStoreInfoRequestDto;
import nguyen.storemanagementbackend.domain.store.model.StoreInfoModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {UuidMapper.class, AddressMapper.class})
public interface StoreInfoMapper {
    StoreInfoResponseBasedDto toStoreInfoResponseBasedDto(StoreInfoModel storeInfoModel);

    @Mapping(target = "storeInfoId", ignore = true)
    StoreInfoModel toEntity(NewStoreInfoRequestDto newStoreInfoRequestDto);
}
