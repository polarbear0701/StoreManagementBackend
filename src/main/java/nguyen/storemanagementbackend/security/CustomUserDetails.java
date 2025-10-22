package nguyen.storemanagementbackend.security;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import nguyen.storemanagementbackend.domain.user.model.Users;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.UUID;


@Getter
@Setter
@AllArgsConstructor
public class CustomUserDetails implements UserDetails {

    private final UUID id;
    private final String email;
    private final transient String password; // transient: don't serialize the password
    private final Collection<? extends GrantedAuthority> authorities;

    public CustomUserDetails(Users user) {
        this.id = user.getUserId();
        this.email = user.getEmail();
        this.password = user.getPassword();
        this.authorities = user.getRole() == null
                ? Collections.emptyList()
                : List.of(new SimpleGrantedAuthority("ROLE_" + user.getRole().name()));
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        // Spring Security's convention is that "username" is the unique identifier for login.
        // In your case, it's the email.
        return email;
    }

    // You can implement custom logic here if you add fields like 'is_locked' to your Users entity
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
