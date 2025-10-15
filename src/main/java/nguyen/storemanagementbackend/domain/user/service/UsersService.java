package nguyen.storemanagementbackend.domain.user.service;

import nguyen.storemanagementbackend.common.enumeration.AuthProvider;
import nguyen.storemanagementbackend.common.enumeration.Role;
import nguyen.storemanagementbackend.common.exception.NoUserFoundException;
import nguyen.storemanagementbackend.domain.address.model.Address;
import nguyen.storemanagementbackend.domain.user.dto.RegisterRequestDto;
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

    private final Logger logger = LoggerFactory.getLogger(UsersService.class);

    public UsersService (
        UsersRepository usersRepository,
        PasswordEncoder passwordEncoder
    ) {
        this.usersRepository = usersRepository;
        this.passwordEncoder = passwordEncoder;
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

    public Users registerNewUsers(RegisterRequestDto requestDto) {
        Users user = Users.builder()
                .email(requestDto.getEmail())
                .password(passwordEncoder.encode(requestDto.getPassword()))
                .userName(requestDto.getUserName())
                .userAge(requestDto.getUserAge())
                .role(Role.STAFF)
                .authProvider(AuthProvider.LOCAL)
                .build();

        logger.info(user.toString());
        if (requestDto.getAddress() != null) {

            Address newAddress = Address.builder()
                    .addressNumber(requestDto.getAddress().getAddressNumber())
                    .addressWard(requestDto.getAddress().getAddressWard())
                    .addressDistrict(requestDto.getAddress().getAddressDistrict())
                    .addressCity(requestDto.getAddress().getAddressCity())
                    .addressCountry(requestDto.getAddress().getAddressCountry())
                    .build();

            logger.info(newAddress.toString());
            user.setAddress(newAddress);
        }
        return usersRepository.save(user);
    }

    public boolean findByEmail(String email) {
        return usersRepository.findByEmail(email).isPresent();
    }


    public Optional<Users> fetchUserById(UUID userId) {
        return usersRepository.findById(userId);
    }

}
