package nguyen.storemanagementbackend.domain.user.controller;

import java.util.*;

import nguyen.storemanagementbackend.common.dto.UserResponseBasedDto;
import nguyen.storemanagementbackend.common.exception.FailToRegisterException;
import nguyen.storemanagementbackend.common.generic.GenericResponseDto;
import nguyen.storemanagementbackend.domain.user.dto.request.AuthRequestDto;
import nguyen.storemanagementbackend.domain.user.dto.response.AuthResponseDto;
import nguyen.storemanagementbackend.domain.user.dto.request.RegisterRequestDto;
import nguyen.storemanagementbackend.domain.user.mapper.UserMapper;
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

	private final AuthenticationManager authenticationManager;
	private final JwtTokenProvider jwtTokenProvider;
	private final UsersService usersService;
	private final UserMapper userMapper;

	@Value("${security.jwt.expiration-ms}")
	private long expirationMs;

	public AuthController(
		AuthenticationManager authenticationManager,
		JwtTokenProvider jwtTokenProvider,
		UsersService usersService,
		UserMapper userMapper
	) {
		this.authenticationManager = authenticationManager;
		this.jwtTokenProvider = jwtTokenProvider;
		this.usersService = usersService;
		this.userMapper = userMapper;
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
	public ResponseEntity<GenericResponseDto<List<UserResponseBasedDto>>> findAllController() {

		List<Users> allUsers = usersService.findAllUsersService();



		return ResponseEntity.status(HttpStatus.ACCEPTED.value()).body(
			new GenericResponseDto<>(
				HttpStatus.ACCEPTED.value(),
				"User found!",
				userMapper.toUserResponseBasedDtoList(allUsers)
			)
		);
	}

	@PostMapping("/register")
	public ResponseEntity<GenericResponseDto<UserResponseBasedDto>> register(
		@RequestBody RegisterRequestDto req
	) {
		UserResponseBasedDto savedUser = usersService.registerNewUsers(req);

		if (savedUser == null) {
			throw new FailToRegisterException(
				"Cannot register right now. Please check again."
			);
		}

		return ResponseEntity.status(HttpStatus.CREATED).body(
			new GenericResponseDto<>(
				HttpStatus.CREATED.value(),
				"User created successfully",
				savedUser
			)
		);
	}

	@GetMapping("/userid/{token}")
	public String getUserId(@PathVariable String token) {
		return jwtTokenProvider.getUserIdFromToken(token);
	}
}
