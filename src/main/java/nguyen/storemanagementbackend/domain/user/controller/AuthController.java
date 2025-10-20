package nguyen.storemanagementbackend.domain.user.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import nguyen.storemanagementbackend.common.exception.FailToRegisterException;
import nguyen.storemanagementbackend.common.exception.UserAlreadyExistsException;
import nguyen.storemanagementbackend.common.generic.GenericResponseDto;
import nguyen.storemanagementbackend.domain.user.dto.AuthRequestDto;
import nguyen.storemanagementbackend.domain.user.dto.AuthResponseDto;
import nguyen.storemanagementbackend.domain.user.dto.RegisterRequestDto;
import nguyen.storemanagementbackend.domain.user.dto.RegisterResponseDto;
import nguyen.storemanagementbackend.domain.user.model.Users;
import nguyen.storemanagementbackend.domain.user.service.UsersService;
import nguyen.storemanagementbackend.security.CustomUserDetails;
import nguyen.storemanagementbackend.security.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

	@GetMapping("/greet")
	public ResponseEntity<GenericResponseDto<String>> greetController() {
		return ResponseEntity.status(HttpStatus.ACCEPTED).body(
			new GenericResponseDto<>(
				HttpStatus.ACCEPTED.value(),
				"Success",
				"Hello"
			)
		);
	}

	private final AuthenticationManager authenticationManager;
	private final JwtTokenProvider jwtTokenProvider;
	private final UsersService usersService;

	@Value("${security.jwt.expiration-ms}")
	private long expirationMs;

	public AuthController(
		AuthenticationManager authenticationManager,
		JwtTokenProvider jwtTokenProvider,
		UsersService usersService
	) {
		this.authenticationManager = authenticationManager;
		this.jwtTokenProvider = jwtTokenProvider;
		this.usersService = usersService;
	}

	@PostMapping("/login")
	public ResponseEntity<GenericResponseDto<AuthResponseDto>> login(
		@RequestBody AuthRequestDto request
	) {
		Authentication authentication = authenticationManager.authenticate(
			new UsernamePasswordAuthenticationToken(
				request.getEmail(),
				request.getPassword()
			)
		);
		CustomUserDetails principal = (CustomUserDetails) authentication.getPrincipal();

		Map<String, String> userInfo = new HashMap<>();
		userInfo.put("userEmail", principal.getEmail());
		userInfo.put("userId", principal.getId().toString());

		String token = jwtTokenProvider.generateToken(
			userInfo,
			principal.getAuthorities()
		);

		return ResponseEntity.status(HttpStatus.ACCEPTED).body(
			new GenericResponseDto<>(
				HttpStatus.ACCEPTED.value(),
				"Logged in Successfully",
				new AuthResponseDto(token, expirationMs / 1000)
			)
		);
	}

	@GetMapping("/all")
	public ResponseEntity<GenericResponseDto<List<Users>>> findAllController() {
		return ResponseEntity.status(HttpStatus.ACCEPTED.value()).body(
			new GenericResponseDto<>(
				HttpStatus.ACCEPTED.value(),
				"User found!",
				usersService.findAllUsersService()
			)
		);
	}

	@PostMapping("/register")
	public ResponseEntity<GenericResponseDto<RegisterResponseDto>> register(
		@RequestBody RegisterRequestDto req
	) {
		if (usersService.findByEmail(req.getEmail())) {
			throw new UserAlreadyExistsException(
				"User with this email already exists!"
			);
		}

		Users saved = usersService.registerNewUsers(req);

		if (saved == null) {
			throw new FailToRegisterException(
				"Cannot register right now. Please check again."
			);
		}
		
		RegisterResponseDto response = RegisterResponseDto
			.builder()
			.email(saved.getEmail())
			.role(saved.getRole())
			.userId(saved.getUserId().toString())
			.userName(saved.getUserName())
			.build();

		return ResponseEntity.status(HttpStatus.CREATED).body(
			new GenericResponseDto<>(
				HttpStatus.CREATED.value(),
				"User created successfully",
				response
			)
		);
	}

	@GetMapping("/userid/{token}")
	public String getUserId(@PathVariable String token) {
		return jwtTokenProvider.getUserIdFromToken(token);
	}
}
