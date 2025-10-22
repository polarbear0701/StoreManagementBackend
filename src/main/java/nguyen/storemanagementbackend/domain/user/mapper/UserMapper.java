package nguyen.storemanagementbackend.domain.user.mapper;

import nguyen.storemanagementbackend.common.dto.UserResponseBasedDto;
import nguyen.storemanagementbackend.common.mapper.UuidMapper;
import nguyen.storemanagementbackend.domain.address.mapper.AddressMapper;
import nguyen.storemanagementbackend.domain.user.dto.response.DetailedUserResponseDto;
import nguyen.storemanagementbackend.domain.user.dto.request.RegisterRequestDto;
import nguyen.storemanagementbackend.domain.user.dto.response.UserSelectionResponseDto;
import nguyen.storemanagementbackend.domain.user.model.Users;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring", uses = {UuidMapper.class, AddressMapper.class})
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
    Users toEntity(RegisterRequestDto registerRequestDto);
}
