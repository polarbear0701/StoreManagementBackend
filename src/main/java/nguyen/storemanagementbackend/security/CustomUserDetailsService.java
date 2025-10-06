package nguyen.storemanagementbackend.security;

import nguyen.storemanagementbackend.domain.user.model.Users;
import nguyen.storemanagementbackend.domain.user.repository.UsersRepository;

import java.util.List;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class CustomUserDetailsService implements UserDetailsService {

    private final UsersRepository usersRepository;

    public CustomUserDetailsService(
        UsersRepository usersRepository
    ) {
        this.usersRepository = usersRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Users users = usersRepository
                .findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + email));
        
        var authorities = List.of(new SimpleGrantedAuthority("ROLE_" + users.getRole()));
        
        return User.builder()
                .username(users.getEmail())
                .password(users.getPassword())
            	.authorities(authorities)
           		.accountLocked(false)
           		.accountExpired(false)
           		.credentialsExpired(false)
           		.disabled(false)
           		.build();
        
    }
}
