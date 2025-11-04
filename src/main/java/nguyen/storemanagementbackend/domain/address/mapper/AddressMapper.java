package nguyen.storemanagementbackend.domain.address.mapper;

import nguyen.storemanagementbackend.common.dto.AddressResponseBasedDto;
import nguyen.storemanagementbackend.common.mapper.UuidMapper;
import nguyen.storemanagementbackend.domain.address.dto.NewAddressRequestDto;
import nguyen.storemanagementbackend.domain.address.model.Address;
import org.mapstruct.*;

@Mapper(componentModel = "spring", uses = {UuidMapper.class})
public interface AddressMapper {
    AddressResponseBasedDto toAddressResponseBasedDto(Address address);

    @Mapping(target = "addressId", ignore = true)
    Address toEntity(NewAddressRequestDto newAddressRequestDto);

    @Mapping(target = "addressId", ignore = true)
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateAddress(NewAddressRequestDto newAddressRequestDto, @MappingTarget Address address);
}
