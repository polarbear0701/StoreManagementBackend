package nguyen.storemanagementbackend.domain.user.controller;

import nguyen.storemanagementbackend.common.enumeration.AuthProvider;
import nguyen.storemanagementbackend.common.enumeration.Role;
import nguyen.storemanagementbackend.common.generic.GenericResponseDto;
import nguyen.storemanagementbackend.domain.user.dto.AuthRequestDto;
import nguyen.storemanagementbackend.domain.user.dto.AuthResponseDto;
import nguyen.storemanagementbackend.domain.user.dto.RegisterRequestDto;
import nguyen.storemanagementbackend.domain.user.model.Users;
import nguyen.storemanagementbackend.domain.user.repository.UsersRepository;
import nguyen.storemanagementbackend.security.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @GetMapping("/greet")
    public ResponseEntity<GenericResponseDto<String>> greetController() {
        return ResponseEntity
                .status(HttpStatus.ACCEPTED)
                .body(new GenericResponseDto<>(
                        HttpStatus.ACCEPTED.value(),
                        "Success",
                        "Hello"));
    }

    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;
    private final UsersRepository usersRepository;
    private final PasswordEncoder passwordEncoder;

    @Value("${security.jwt.expiration-ms}")
    private long expirationMs;

    public AuthController(AuthenticationManager authenticationManager,
                          JwtTokenProvider jwtTokenProvider,
                          UsersRepository usersRepository,
                          PasswordEncoder passwordEncoder) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
        this.usersRepository = usersRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/login")
    public ResponseEntity<GenericResponseDto<AuthResponseDto>> login(@RequestBody AuthRequestDto request) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
        );
        User principal = (User) authentication.getPrincipal();

        String token = jwtTokenProvider.generateToken(principal.getUsername(), principal.getAuthorities());

        return ResponseEntity
                .status(HttpStatus.ACCEPTED)
                .body(new GenericResponseDto<>(
                        HttpStatus.ACCEPTED.value(),
                        "Logged in Successfully",
                        new AuthResponseDto(token, expirationMs / 1000)
                        )
                );
    }

    @PostMapping("/register")
    public ResponseEntity<GenericResponseDto<Users>> register(@RequestBody RegisterRequestDto req) {
        if (usersRepository.findByEmail(req.getEmail()).isPresent()) {
            return ResponseEntity
                    .badRequest()
                    .body(new GenericResponseDto<>(
                            HttpStatus.BAD_REQUEST.value(),
                            "User already exists!",
                            null
                    ));
        }

        Users user = Users.builder()
                .email(req.getEmail())
                .password(passwordEncoder.encode(req.getPassword()))
                .userName(req.getUserName())
                .userAge(req.getUserAge())
                .role(Role.STAFF)
                .authProvider(AuthProvider.LOCAL)
                .build();

        Users saved = usersRepository.save(user);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new GenericResponseDto<>(
                        HttpStatus.CREATED.value(),
                        "User created successfully",
                        saved
                ));
    }
}
