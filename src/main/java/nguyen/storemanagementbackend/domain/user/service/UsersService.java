package nguyen.storemanagementbackend.domain.user.service;

import nguyen.storemanagementbackend.common.dto.UserResponseBasedDto;
import nguyen.storemanagementbackend.common.exception.NoUserFoundException;
import nguyen.storemanagementbackend.common.exception.UserAlreadyExistsException;

import nguyen.storemanagementbackend.domain.user.dto.RegisterRequestDto;
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

    public Users findUserByName(String userName) {
        Optional<Users> userInSearch = usersRepository.findByUserName(userName);
        if (userInSearch.isEmpty()) {
            throw new NoUserFoundException("No user found!");
        }
        return userInSearch.get();
    }

    public UserResponseBasedDto registerNewUsers(RegisterRequestDto requestDto) {
        if (this.findByEmail(requestDto.getEmail())) {
            throw new UserAlreadyExistsException(
                    "User with this email already exists!"
            );
        }

        Users user = userMapper.toEntity(requestDto);

        user.setPassword(passwordEncoder.encode(requestDto.getPassword()));
        logger.info("New user created, id: {}", user.getUserId().toString());
        return userMapper.toUserResponseBasedDto(usersRepository.save(user));
    }

    public boolean findByEmail(String email) {
        return usersRepository.findByEmail(email).isPresent();
    }

    public Optional<Users> fetchUserByEmail(String email) {
        return usersRepository.findByEmail(email);
    }


    public Optional<Users> fetchUserById(UUID userId) {
        return usersRepository.findById(userId);
    }

}
