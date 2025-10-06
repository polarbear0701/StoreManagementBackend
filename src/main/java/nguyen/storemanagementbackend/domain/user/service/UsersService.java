package nguyen.storemanagementbackend.domain.user.service;

import nguyen.storemanagementbackend.domain.user.repository.UsersRepository;
import org.springframework.stereotype.Service;

@Service
public class UsersService {
    private final UsersRepository usersRepository;

    public UsersService (
        UsersRepository usersRepository
    ) {
        this.usersRepository = usersRepository;
    }

}
