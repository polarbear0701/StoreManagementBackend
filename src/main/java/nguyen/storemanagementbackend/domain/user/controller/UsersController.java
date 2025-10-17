package nguyen.storemanagementbackend.domain.user.controller;

import nguyen.storemanagementbackend.common.exception.NoUserFoundException;
import nguyen.storemanagementbackend.domain.user.model.Users;
import nguyen.storemanagementbackend.domain.user.repository.UsersRepository;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/users")
public class UsersController {

    private final UsersRepository usersRepository;

    public UsersController(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    @GetMapping("/find")
    public Users findUsersById(@RequestBody String user_name) {

        return usersRepository.findByUserName(user_name).orElseThrow(() -> new NoUserFoundException("No user found!"));
    }
}
