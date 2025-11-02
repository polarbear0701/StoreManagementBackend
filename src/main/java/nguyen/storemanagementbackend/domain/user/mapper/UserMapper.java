package nguyen.storemanagementbackend.domain.user.mapper;

import nguyen.storemanagementbackend.common.dto.UserResponseBasedDto;
import nguyen.storemanagementbackend.common.mapper.UuidMapper;
import nguyen.storemanagementbackend.domain.address.mapper.AddressMapper;
import nguyen.storemanagementbackend.domain.user.dto.request.RegisterUserRequestDto;
import nguyen.storemanagementbackend.domain.user.dto.request.UpdateUserRequestDto;
import nguyen.storemanagementbackend.domain.user.dto.response.DetailedUserResponseDto;
import nguyen.storemanagementbackend.domain.user.dto.response.UserSelectionResponseDto;
import nguyen.storemanagementbackend.domain.user.model.Users;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring", uses = {UuidMapper.class, AddressMapper.class}, nullValueMapMappingStrategy = NullValueMappingStrategy.RETURN_DEFAULT)
public interface UserMapper {

    //Response Mappers
    UserResponseBasedDto toUserResponseBasedDto(Users users);
    DetailedUserResponseDto toDetailedUserDto(Users users);
    UserSelectionResponseDto toUserSelectionResponseDto(Users users);
    List<UserResponseBasedDto> toUserResponseBasedDtoList(List<Users> usersList);

    //Request Mappers

    @Mapping(target = "userId", ignore = true)
    @Mapping(target = "password", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "role", ignore = true)
    @Mapping(target = "authProvider", ignore = true)
    Users toEntity(RegisterUserRequestDto registerRequestDto);

    @Mapping(target = "userId", ignore = true)
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateUser(UpdateUserRequestDto updateUserRequestDto, @MappingTarget Users users);
}
