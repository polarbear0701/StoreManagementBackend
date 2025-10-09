package nguyen.storemanagementbackend.domain.user.service;

import nguyen.storemanagementbackend.common.exception.NoUserFoundException;
import nguyen.storemanagementbackend.domain.user.model.Users;
import nguyen.storemanagementbackend.domain.user.repository.UsersRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UsersService {
    private final UsersRepository usersRepository;

    public UsersService (
        UsersRepository usersRepository
    ) {
        this.usersRepository = usersRepository;
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

}
