package nguyen.storemanagementbackend.domain.user.controller;

import nguyen.storemanagementbackend.common.dto.UserResponseBasedDto;
import nguyen.storemanagementbackend.common.exception.NoUserFoundException;
import nguyen.storemanagementbackend.common.generic.GenericResponseDto;
import nguyen.storemanagementbackend.domain.user.dto.request.ChangePasswordRequestDto;
import nguyen.storemanagementbackend.domain.user.dto.request.UpdateUserRequestDto;
import nguyen.storemanagementbackend.domain.user.dto.response.DetailedUserResponseDto;
import nguyen.storemanagementbackend.domain.user.mapper.UserMapper;
import nguyen.storemanagementbackend.domain.user.model.Users;
import nguyen.storemanagementbackend.domain.user.service.UsersService;
import nguyen.storemanagementbackend.security.CustomUserDetails;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/users")
public class UsersController {

    private final UsersService usersService;
    private final UserMapper userMapper;

    public UsersController(
            UsersService usersService,
            UserMapper userMapper
    ) {
        this.usersService = usersService;
        this.userMapper = userMapper;
    }

    @GetMapping("/find/{email}")
    public DetailedUserResponseDto fetchDetailedUserByEmail (
            @AuthenticationPrincipal CustomUserDetails currentUser
    ) {
        Users fetchedUserByEmail = usersService.fetchUserByEmail(currentUser.getEmail()).orElseThrow(
                () -> new NoUserFoundException("User with this email does not exist. Please try again")
        );

        return userMapper.toDetailedUserDto(fetchedUserByEmail);
    }

    @PatchMapping("/update")
    public ResponseEntity<GenericResponseDto<String>> updateUser (
            @AuthenticationPrincipal CustomUserDetails currentUser,
            @RequestBody UpdateUserRequestDto updateUserRequestDto
    ) {
        return ResponseEntity.status(HttpStatus.OK.value())
                .body(new GenericResponseDto<>(HttpStatus.OK.value(), "Updated successfully", ""));
    }

    @PatchMapping("/update/password")
    public ResponseEntity<GenericResponseDto<UserResponseBasedDto>> changePasswordController(
            @RequestBody ChangePasswordRequestDto dto,
            @AuthenticationPrincipal CustomUserDetails currentUser
    ) {
        UserResponseBasedDto userResponseBasedDto = usersService.updatePassword(
                dto.getNewPassword(), dto.getCurrentUserId()
        );
        return userResponseBasedDto != null ?
                ResponseEntity.ok().body(new GenericResponseDto<>(
                        HttpStatus.OK.value(), "Password updated!", userResponseBasedDto)
                ):
                ResponseEntity.badRequest().body(new GenericResponseDto<>(
                        HttpStatus.OK.value(), "Password updated!", null)
                );
    }
}
