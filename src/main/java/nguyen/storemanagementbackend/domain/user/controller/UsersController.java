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
            @PathVariable String email
    ) {
        Users fetchedUserByEmail = usersService.fetchUserByEmail(email).orElseThrow(
                () -> new NoUserFoundException("User with this email does not exist. Please try again")
        );

        return userMapper.toDetailedUserDto(fetchedUserByEmail);
    }
    
    @DeleteMapping("/delete")
    public ResponseEntity<GenericResponseDto<String>> disableUser(
        @AuthenticationPrincipal CustomUserDetails currentUser
    ) {
        usersService.disableUser(currentUser.getId());
        return ResponseEntity.ok().body(
            new GenericResponseDto<String>(
                HttpStatus.OK.value(),
                "User deleted successfully",
                null
            )
        );
    }

    @PatchMapping("/update/info")
    public ResponseEntity<GenericResponseDto<UserResponseBasedDto>> updateUser (
            @AuthenticationPrincipal CustomUserDetails currentUser,
            @RequestBody UpdateUserRequestDto updateUserRequestDto
    ) {

        UserResponseBasedDto updatedUser = usersService.updateUser(
                currentUser.getId(),
                updateUserRequestDto
        );

        return ResponseEntity.status(HttpStatus.OK.value())
                .body(new GenericResponseDto<>(HttpStatus.OK.value(), "Updated successfully", updatedUser));
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
