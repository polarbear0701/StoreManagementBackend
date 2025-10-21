package nguyen.storemanagementbackend.domain.user.mapper;

import nguyen.storemanagementbackend.common.dto.UserResponseBasedDto;
import nguyen.storemanagementbackend.common.mapper.UuidMapper;
import nguyen.storemanagementbackend.domain.address.mapper.AddressMapper;
import nguyen.storemanagementbackend.domain.user.dto.DetailedUserDto;
import nguyen.storemanagementbackend.domain.user.model.Users;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring", uses = {UuidMapper.class, AddressMapper.class})
public interface UserMapper {

    UserResponseBasedDto toUserResponseBasedDto(Users users);

    DetailedUserDto toDetailedUserDto(Users users);

    List<UserResponseBasedDto> toUserResponseBasedDtoList(List<Users> usersList);
}
