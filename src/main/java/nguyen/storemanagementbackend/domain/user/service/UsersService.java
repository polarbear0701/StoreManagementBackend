package nguyen.storemanagementbackend.domain.user.service;

import com.nulabinc.zxcvbn.Strength;
import com.nulabinc.zxcvbn.Zxcvbn;
import nguyen.storemanagementbackend.common.dto.UserResponseBasedDto;
import nguyen.storemanagementbackend.common.exception.InvalidNewPasswordException;
import nguyen.storemanagementbackend.common.exception.NoUserFoundException;
import nguyen.storemanagementbackend.common.exception.UserAlreadyExistsException;

import nguyen.storemanagementbackend.domain.user.dto.request.RegisterUserRequestDto;
import nguyen.storemanagementbackend.domain.user.dto.request.UpdateUserRequestDto;
import nguyen.storemanagementbackend.domain.user.mapper.UserMapper;
import nguyen.storemanagementbackend.domain.user.model.Users;
import nguyen.storemanagementbackend.domain.user.repository.UsersRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UsersService {
    private final UsersRepository usersRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;

    private static final int MIN_LENGTH = 12;
    private static final String HAS_UPPERCASE = ".*[A-Z].*";
    private static final String HAS_LOWERCASE = ".[a-z].*";
    private static final String HAS_DIGIT = ".*[0-9].*";
    private static final String HAS_SPECIAL_CHAR = ".*[!@#$%^&*?].*";

    private static final Zxcvbn externalPasswordValidator = new Zxcvbn();

    private final Logger logger = LoggerFactory.getLogger(UsersService.class);

    public UsersService (
        UsersRepository usersRepository,
        PasswordEncoder passwordEncoder,
        UserMapper userMapper
    ) {
        this.usersRepository = usersRepository;
        this.passwordEncoder = passwordEncoder;
        this.userMapper = userMapper;
    }

    public List<Users> findAllUsersService() {
        List<Users> usersList = usersRepository.findAll();

        if (usersList.isEmpty()) {
            throw new NoUserFoundException("No user found!");
        }

        return usersList;
    }

    public UserResponseBasedDto registerNewUsers(RegisterUserRequestDto requestDto) {
        if (usersRepository.existsByEmail(requestDto.getEmail())) {
            throw new UserAlreadyExistsException(
                    "User with this email already exists!"
            );
        }

        checkPasswordQuality(requestDto.getPassword());
        checkPasswordIsUsername(requestDto.getPassword(), requestDto.getUserName());

        Users user = userMapper.toEntity(requestDto);

        user.setPassword(passwordEncoder.encode(requestDto.getPassword()));
        return userMapper.toUserResponseBasedDto(usersRepository.save(user));
    }

    public Optional<Users> fetchUserByEmail(String email) {
        return usersRepository.findByEmail(email);
    }


    public Optional<Users> fetchUserById(UUID userId) {
        return usersRepository.findById(userId);
    }

    public UserResponseBasedDto updatePassword(String newPassword, UUID userId) {
        Users currentUser = usersRepository.findById(userId).orElseThrow(
                () -> new NoUserFoundException("No user found")
        );
        checkPasswordIsUsername(newPassword, currentUser.getUserName());
        validatePasswordOnUpdate(newPassword, currentUser.getPassword());

        //If validatePasswordOnUpdate pass, move to this section
        currentUser.setPassword(passwordEncoder.encode(newPassword));
        return userMapper.toUserResponseBasedDto(usersRepository.save(currentUser));
    }

    public void updateUser(UUID userId, UpdateUserRequestDto updateUserRequestDto) {

    }

    private void validatePasswordOnUpdate(String newPassword, String currentEncodedPassword) {
        if (passwordEncoder.matches(newPassword, currentEncodedPassword)) {
            throw new InvalidNewPasswordException("New password and current password is similar");
        }

        checkPasswordQuality(newPassword);
    }

    /**
     * Function to check new password quality.
     * <p>The password should be:</p>
     * <ul>
     *     <li>At least 12 characters</li>
     *     <li>Has at least one digit, lowercase, uppercase</li>
     *     <li>Has a special character [!@#$%^&*?]</li>
     * </ul>
     * @param newPassword New password when create or update
     * @throws InvalidNewPasswordException The function will throw InvalidNewPasswordException
     */
    private void checkPasswordQuality (String newPassword) throws InvalidNewPasswordException {
        if (newPassword == null || newPassword.isEmpty()) {
            throw  new InvalidNewPasswordException("No password provided");
        }

        if (newPassword.length() < MIN_LENGTH) {
            throw new InvalidNewPasswordException("Password should have at lease 12 characters");
        }

         if (!newPassword.matches(HAS_LOWERCASE)) {
             throw new InvalidNewPasswordException("Password doesn't have lowercase character");
         }

         if (!newPassword.matches(HAS_UPPERCASE)) {
             throw new InvalidNewPasswordException("Password doesn't have uppercase character");
         }

         if (!newPassword.matches(HAS_DIGIT)) {
             throw new InvalidNewPasswordException("Password doesn't have digit");
         }

         if (!newPassword.matches(HAS_SPECIAL_CHAR)) {
             throw new InvalidNewPasswordException("Password doesn't have special character");
         }

         Strength passwordStrength = externalPasswordValidator.measure(newPassword);

         if (passwordStrength.getScore() <= 3) {
             throw new InvalidNewPasswordException(
                     String.format("Password is not good enough. Issue: %s", passwordStrength.getFeedback())
             );
         }

    }

    /**
     * Function to check if userName is similar or a part of password.
     *
     * @param newPassword the password from request dto
     * @param userName the username that need to create or change
     */
    private void checkPasswordIsUsername(String newPassword, String userName) {
        String checkedNewPassword = newPassword.toLowerCase();
        String checkedUserName = userName.toLowerCase();

        if (checkedNewPassword.contains(checkedUserName)) {
            throw new InvalidNewPasswordException("Password is similar to username");
        }

        if (checkedNewPassword.matches(".*checkedUserName.*")) {
            throw new InvalidNewPasswordException("Password contains username");
        }
    }
}
