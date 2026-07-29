//package org.example.techtalksskillbasedrecruitment.modules.user;
//
//import org.example.techtalksskillbasedrecruitment.common.exceptions.ConflictException;
//import org.example.techtalksskillbasedrecruitment.common.exceptions.ResourceNotFoundException;
//import org.example.techtalksskillbasedrecruitment.modules.role.Role;
//import org.example.techtalksskillbasedrecruitment.modules.role.RoleRepository;
//import org.example.techtalksskillbasedrecruitment.security.jwt.JwtService;
//import org.example.techtalksskillbasedrecruitment.modules.user.dto.request.CreateUserRequest;
//import org.example.techtalksskillbasedrecruitment.modules.user.dto.request.LoginRequest;
//import org.example.techtalksskillbasedrecruitment.modules.user.dto.request.UpdateUserRequest;
//import org.example.techtalksskillbasedrecruitment.modules.user.dto.response.LoginResponse;
//import org.example.techtalksskillbasedrecruitment.modules.user.dto.response.UserResponse;
//import org.example.techtalksskillbasedrecruitment.modules.user.mapper.UserMapper;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.Pageable;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.stereotype.Service;
//
//@Service
//public class UserService {
//
//    private final UserRepository userRepository;
//    private final RoleRepository roleRepository;
//    private final UserMapper userMapper;
//    private final PasswordEncoder passwordEncoder;
//    private final AuthenticationManager authenticationManager;
//    private final JwtService jwtService;
//
//
//    public UserService(
//            UserRepository userRepository,
//            RoleRepository roleRepository,
//            UserMapper userMapper,
//            PasswordEncoder passwordEncoder,
//            AuthenticationManager authenticationManager,
//            JwtService jwtService
//    ) {
//        this.userRepository = userRepository;
//        this.roleRepository = roleRepository;
//        this.userMapper = userMapper;
//        this.passwordEncoder = passwordEncoder;
//        this.authenticationManager = authenticationManager;
//        this.jwtService = jwtService;
//    }
//
//
////    public UserResponse createUserService(CreateUserRequest userRequest) {
////
////        if (userRepository.existsByEmail(userRequest.getEmail())) {
////            throw new ConflictException("User already exists with this email");
////        }
////
////        if (userRepository.existsByUsername(userRequest.getUsername())) {
////            throw new ConflictException("User already exists with this username");
////        }
////
////        if (userRepository.existsByPhoneNumber(userRequest.getPhoneNumber())) {
////            throw new ConflictException("User already exists with this phone number");
////        }
////
////
////        Role role = roleRepository.findById(userRequest.getRoleId())
////                .orElseThrow(() ->
////                        new ResourceNotFoundException("Role Id not found")
////                );
////
////
////        User user = new User();
////
////        user.setUsername(userRequest.getUsername());
////        user.setPhoneNumber(userRequest.getPhoneNumber());
////        user.setRole(role);
////        user.setEmail(userRequest.getEmail());
////        user.setPassword(userRequest.getPassword());
////
////
////        User savedUser = userRepository.save(user);
////
////        return userMapper.toUserResponseDTO(savedUser);
////    }
//
//    public UserResponse createUserService(CreateUserRequest userRequest) {
//
//        if (userRepository.existsByEmail(userRequest.getEmail())) {
//            throw new ConflictException("User already exists with this email");
//        }
//
//        if (userRepository.existsByUsername(userRequest.getUsername())) {
//            throw new ConflictException("User already exists with this username");
//        }
//
//        if (userRepository.existsByPhoneNumber(userRequest.getPhoneNumber())) {
//            throw new ConflictException("User already exists with this phone number");
//        }
//
//
//        Role role = roleRepository.findById(userRequest.getRoleId())
//                .orElseThrow(() ->
//                        new ResourceNotFoundException("Role Id not found")
//                );
//
//
//        User user = new User();
//
//        user.setUsername(userRequest.getUsername());
//        user.setPhoneNumber(userRequest.getPhoneNumber());
//        user.setRole(role);
//        user.setEmail(userRequest.getEmail());
//        user.setPassword(passwordEncoder.encode(userRequest.getPassword()));
//
//
//        User savedUser = userRepository.save(user);
//
//        return userMapper.toUserResponseDTO(savedUser);
//    }
//
//
//    public UserResponse updateUserService(UpdateUserRequest userRequest) {
//
//
//        if (userRepository.existsByEmailAndUserIdNot(
//                userRequest.getEmail(),
//                userRequest.getUserId()
//        )) {
//            throw new ConflictException("User already exists with this email");
//        }
//
//
//        if (userRepository.existsByUsernameAndUserIdNot(
//                userRequest.getUsername(),
//                userRequest.getUserId()
//        )) {
//            throw new ConflictException("User already exists with this username");
//        }
//
//
//        if (userRepository.existsByPhoneNumberAndUserIdNot(
//                userRequest.getPhoneNumber(),
//                userRequest.getUserId()
//        )) {
//            throw new ConflictException("User already exists with this phone number");
//        }
//
//
//        User existingUser = userRepository.findById(userRequest.getUserId())
//                .orElseThrow(() ->
//                        new ResourceNotFoundException(
//                                "User does not exist with this id"
//                        )
//                );
//
//
//        Role role = roleRepository.findById(userRequest.getRoleId())
//                .orElseThrow(() ->
//                        new ResourceNotFoundException("Role not found")
//                );
//
//
//        existingUser.setUsername(userRequest.getUsername());
//        existingUser.setEmail(userRequest.getEmail());
//        existingUser.setPhoneNumber(userRequest.getPhoneNumber());
//        existingUser.setRole(role);
//
//
//        User updatedUser = userRepository.save(existingUser);
//
//        return userMapper.toUserResponseDTO(updatedUser);
//    }
//
//
//    public Page<UserResponse> getAllUsersService(Pageable pageable) {
//
//        return userRepository.findAll(pageable)
//                .map(userMapper::toUserResponseDTO);
//    }
//
//
//    public LoginResponse loginUserService(LoginRequest loginRequest) {
//
//        authenticationManager.authenticate(
//                new UsernamePasswordAuthenticationToken(
//                        loginRequest.getUsername(),
//                        loginRequest.getPassword()
//                )
//        );
//
//        User user = userRepository.findByUsername(loginRequest.getUsername())
//                .orElseThrow(() -> new ResourceNotFoundException("User not found with this username"));
//
//        String token = jwtService.generateToken(user.getUsername());
//
//        return new LoginResponse(
//                user.getUserId(),
//                user.getUsername(),
//                user.getEmail(),
//                user.getPhoneNumber(),
//                user.getRole().getRoleName(),
//                token
//        );
//    }
//}







//
//package org.example.techtalksskillbasedrecruitment.modules.user;
//
//import org.example.techtalksskillbasedrecruitment.common.exceptions.ConflictException;
//import org.example.techtalksskillbasedrecruitment.common.exceptions.ResourceNotFoundException;
//import org.example.techtalksskillbasedrecruitment.common.exceptions.UnauthorizedException;
//import org.example.techtalksskillbasedrecruitment.modules.role.Role;
//import org.example.techtalksskillbasedrecruitment.modules.role.RoleRepository;
//import org.example.techtalksskillbasedrecruitment.security.jwt.JwtService;
//import org.example.techtalksskillbasedrecruitment.modules.user.dto.request.CreateUserRequest;
//import org.example.techtalksskillbasedrecruitment.modules.user.dto.request.LoginRequest;
//import org.example.techtalksskillbasedrecruitment.modules.user.dto.request.RefreshTokenRequest;
//import org.example.techtalksskillbasedrecruitment.modules.user.dto.request.UpdateUserRequest;
//import org.example.techtalksskillbasedrecruitment.modules.user.dto.response.LoginResponse;
//import org.example.techtalksskillbasedrecruitment.modules.user.dto.response.UserResponse;
//import org.example.techtalksskillbasedrecruitment.modules.user.mapper.UserMapper;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.Pageable;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.stereotype.Service;
//
//@Service
//public class UserService {
//
//    private final UserRepository userRepository;
//    private final RoleRepository roleRepository;
//    private final UserMapper userMapper;
//    private final PasswordEncoder passwordEncoder;
//    private final AuthenticationManager authenticationManager;
//    private final JwtService jwtService;
//
//
//    public UserService(
//            UserRepository userRepository,
//            RoleRepository roleRepository,
//            UserMapper userMapper,
//            PasswordEncoder passwordEncoder,
//            AuthenticationManager authenticationManager,
//            JwtService jwtService
//    ) {
//        this.userRepository = userRepository;
//        this.roleRepository = roleRepository;
//        this.userMapper = userMapper;
//        this.passwordEncoder = passwordEncoder;
//        this.authenticationManager = authenticationManager;
//        this.jwtService = jwtService;
//    }
//
//
//    public UserResponse createUserService(CreateUserRequest userRequest) {
//
//        if (userRepository.existsByEmail(userRequest.getEmail())) {
//            throw new ConflictException("User already exists with this email");
//        }
//
//        if (userRepository.existsByUsername(userRequest.getUsername())) {
//            throw new ConflictException("User already exists with this username");
//        }
//
//        if (userRepository.existsByPhoneNumber(userRequest.getPhoneNumber())) {
//            throw new ConflictException("User already exists with this phone number");
//        }
//
//
//        Role role = roleRepository.findById(userRequest.getRoleId())
//                .orElseThrow(() ->
//                        new ResourceNotFoundException("Role Id not found")
//                );
//
//
//        User user = new User();
//
//        user.setUsername(userRequest.getUsername());
//        user.setPhoneNumber(userRequest.getPhoneNumber());
//        user.setRole(role);
//        user.setEmail(userRequest.getEmail());
//        user.setPassword(passwordEncoder.encode(userRequest.getPassword()));
//
//
//        User savedUser = userRepository.save(user);
//
//        return userMapper.toUserResponseDTO(savedUser);
//    }
//
//
//    public UserResponse updateUserService(UpdateUserRequest userRequest) {
//
//
//        if (userRepository.existsByEmailAndUserIdNot(
//                userRequest.getEmail(),
//                userRequest.getUserId()
//        )) {
//            throw new ConflictException("User already exists with this email");
//        }
//
//
//        if (userRepository.existsByUsernameAndUserIdNot(
//                userRequest.getUsername(),
//                userRequest.getUserId()
//        )) {
//            throw new ConflictException("User already exists with this username");
//        }
//
//
//        if (userRepository.existsByPhoneNumberAndUserIdNot(
//                userRequest.getPhoneNumber(),
//                userRequest.getUserId()
//        )) {
//            throw new ConflictException("User already exists with this phone number");
//        }
//
//
//        User existingUser = userRepository.findById(userRequest.getUserId())
//                .orElseThrow(() ->
//                        new ResourceNotFoundException(
//                                "User does not exist with this id"
//                        )
//                );
//
//
//        Role role = roleRepository.findById(userRequest.getRoleId())
//                .orElseThrow(() ->
//                        new ResourceNotFoundException("Role not found")
//                );
//
//
//        existingUser.setUsername(userRequest.getUsername());
//        existingUser.setEmail(userRequest.getEmail());
//        existingUser.setPhoneNumber(userRequest.getPhoneNumber());
//        existingUser.setRole(role);
//
//
//        User updatedUser = userRepository.save(existingUser);
//
//        return userMapper.toUserResponseDTO(updatedUser);
//    }
//
//
//    public Page<UserResponse> getAllUsersService(Pageable pageable) {
//
//        return userRepository.findAll(pageable)
//                .map(userMapper::toUserResponseDTO);
//    }
//
//
//    public LoginResponse loginUserService(LoginRequest loginRequest) {
//
//        authenticationManager.authenticate(
//                new UsernamePasswordAuthenticationToken(
//                        loginRequest.getUsername(),
//                        loginRequest.getPassword()
//                )
//        );
//
//        User user = userRepository.findByUsername(loginRequest.getUsername())
//                .orElseThrow(() -> new ResourceNotFoundException("User not found with this username"));
//
//        String accessToken = jwtService.generateAccessToken(user.getUsername());
//        String refreshToken = jwtService.generateRefreshToken(user.getUsername());
//
//        return new LoginResponse(
//                user.getUserId(),
//                user.getUsername(),
//                user.getEmail(),
//                user.getPhoneNumber(),
//                user.getRole().getRoleName(),
//                accessToken,
//                refreshToken
//        );
//    }
//
//
//    public LoginResponse refreshTokenService(RefreshTokenRequest refreshTokenRequest) {
//
//        String refreshToken = refreshTokenRequest.getRefreshToken();
//
//        if (!jwtService.isRefreshToken(refreshToken)) {
//            throw new UnauthorizedException("Provided token is not a valid refresh token");
//        }
//
//        if (jwtService.isTokenExpired(refreshToken)) {
//            throw new UnauthorizedException("Refresh token has expired");
//        }
//
//        String username = jwtService.extractUsername(refreshToken);
//
//        User user = userRepository.findByUsername(username)
//                .orElseThrow(() ->
//                        new ResourceNotFoundException("User not found with this username"));
//
//        if (!jwtService.validateRefreshToken(refreshToken, user.getUsername())) {
//            throw new UnauthorizedException("Invalid refresh token");
//        }
//
//        String newAccessToken = jwtService.generateAccessToken(user.getUsername());
//
//        return new LoginResponse(
//                user.getUserId(),
//                user.getUsername(),
//                user.getEmail(),
//                user.getPhoneNumber(),
//                user.getRole().getRoleName(),
//                newAccessToken,
//                refreshToken
//        );
//    }
//}










package org.example.techtalksskillbasedrecruitment.modules.user;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import org.example.techtalksskillbasedrecruitment.common.exceptions.ConflictException;
import org.example.techtalksskillbasedrecruitment.common.exceptions.ResourceNotFoundException;
import org.example.techtalksskillbasedrecruitment.common.exceptions.UnauthorizedException;
import org.example.techtalksskillbasedrecruitment.modules.role.Role;
import org.example.techtalksskillbasedrecruitment.modules.role.RoleRepository;
import org.example.techtalksskillbasedrecruitment.modules.user.Email.EmailService;
import org.example.techtalksskillbasedrecruitment.modules.user.dto.request.CreateUserRequest;
import org.example.techtalksskillbasedrecruitment.modules.user.dto.request.ForgotPasswordRequest;
import org.example.techtalksskillbasedrecruitment.modules.user.dto.request.LoginRequest;
import org.example.techtalksskillbasedrecruitment.modules.user.dto.request.RefreshTokenRequest;
import org.example.techtalksskillbasedrecruitment.modules.user.dto.request.ResetPasswordRequest;
import org.example.techtalksskillbasedrecruitment.modules.user.dto.request.UpdateUserRequest;
import org.example.techtalksskillbasedrecruitment.modules.user.dto.response.LoginResponse;
import org.example.techtalksskillbasedrecruitment.modules.user.dto.response.UserResponse;

import org.example.techtalksskillbasedrecruitment.modules.user.mapper.UserMapper;
import org.example.techtalksskillbasedrecruitment.security.jwt.JwtService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.mail.MailException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Locale;
import java.util.Optional;

@Service
public class UserService {

    private static final String FORGOT_PASSWORD_MESSAGE =
            "If an account exists with this email, " +
                    "a password reset link has been sent.";

    private static final String RESET_PASSWORD_SUCCESS_MESSAGE =
            "Password reset successfully.";

    private static final String PASSWORD_RESET_TOKEN_TYPE =
            "PASSWORD_RESET";

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final EmailService emailService;
    private final String resetPasswordUrl;

    public UserService(
            UserRepository userRepository,
            RoleRepository roleRepository,
            UserMapper userMapper,
            PasswordEncoder passwordEncoder,
            AuthenticationManager authenticationManager,
            JwtService jwtService,
            EmailService emailService,
            @Value("${app.frontend.reset-password-url}")
            String resetPasswordUrl
    ) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.userMapper = userMapper;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
        this.emailService = emailService;
        this.resetPasswordUrl = resetPasswordUrl;
    }

    @Transactional
    public UserResponse createUserService(
            CreateUserRequest userRequest
    ) {
        String username =
                normalizeRequiredValue(
                        userRequest.getUsername(),
                        "Username"
                );

        String email =
                normalizeEmail(
                        userRequest.getEmail()
                );

        String phoneNumber =
                normalizeOptionalValue(
                        userRequest.getPhoneNumber()
                );

        if (userRepository.existsByEmail(email)) {
            throw new ConflictException(
                    "User already exists with this email"
            );
        }

        if (userRepository.existsByUsername(username)) {
            throw new ConflictException(
                    "User already exists with this username"
            );
        }

        if (StringUtils.hasText(phoneNumber)
                && userRepository.existsByPhoneNumber(
                phoneNumber
        )) {
            throw new ConflictException(
                    "User already exists with this phone number"
            );
        }

        Role role = roleRepository
                .findById(userRequest.getRoleId())
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Role Id not found"
                        )
                );

        User user = new User();

        user.setUsername(username);
        user.setPhoneNumber(phoneNumber);
        user.setRole(role);
        user.setEmail(email);

        /*
         * Never store the raw password.
         */
        user.setPassword(
                passwordEncoder.encode(
                        userRequest.getPassword()
                )
        );

        User savedUser =
                userRepository.save(user);

        return userMapper.toUserResponseDTO(
                savedUser
        );
    }

    @Transactional
    public UserResponse updateUserService(
            UpdateUserRequest userRequest
    ) {
        User existingUser = userRepository
                .findById(userRequest.getUserId())
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "User does not exist with this id"
                        )
                );

        String username =
                normalizeRequiredValue(
                        userRequest.getUsername(),
                        "Username"
                );

        String email =
                normalizeEmail(
                        userRequest.getEmail()
                );

        String phoneNumber =
                normalizeOptionalValue(
                        userRequest.getPhoneNumber()
                );

        if (userRepository.existsByEmailAndUserIdNot(
                email,
                userRequest.getUserId()
        )) {
            throw new ConflictException(
                    "User already exists with this email"
            );
        }

        if (userRepository.existsByUsernameAndUserIdNot(
                username,
                userRequest.getUserId()
        )) {
            throw new ConflictException(
                    "User already exists with this username"
            );
        }

        if (StringUtils.hasText(phoneNumber)
                && userRepository
                .existsByPhoneNumberAndUserIdNot(
                        phoneNumber,
                        userRequest.getUserId()
                )) {
            throw new ConflictException(
                    "User already exists with this phone number"
            );
        }

        Role role = roleRepository
                .findById(userRequest.getRoleId())
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Role not found"
                        )
                );

        existingUser.setUsername(username);
        existingUser.setEmail(email);
        existingUser.setPhoneNumber(phoneNumber);
        existingUser.setRole(role);

        User updatedUser =
                userRepository.save(existingUser);

        return userMapper.toUserResponseDTO(
                updatedUser
        );
    }

    @Transactional(readOnly = true)
    public Page<UserResponse> getAllUsersService(
            Pageable pageable
    ) {
        return userRepository
                .findAll(pageable)
                .map(userMapper::toUserResponseDTO);
    }

    @Transactional(readOnly = true)
    public LoginResponse loginUserService(
            LoginRequest loginRequest
    ) {
        String username =
                normalizeRequiredValue(
                        loginRequest.getUsername(),
                        "Username"
                );

        authenticationManager.authenticate(
                UsernamePasswordAuthenticationToken
                        .unauthenticated(
                                username,
                                loginRequest.getPassword()
                        )
        );

        User user = userRepository
                .findByUsername(username)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "User not found with this username"
                        )
                );

        String accessToken =
                jwtService.generateAccessToken(
                        user.getUsername()
                );

        String refreshToken =
                jwtService.generateRefreshToken(
                        user.getUsername()
                );

        return createLoginResponse(
                user,
                accessToken,
                refreshToken
        );
    }

    @Transactional(readOnly = true)
    public LoginResponse refreshTokenService(
            RefreshTokenRequest refreshTokenRequest
    ) {
        String refreshToken =
                refreshTokenRequest.getRefreshToken();

        if (!StringUtils.hasText(refreshToken)) {
            throw new UnauthorizedException(
                    "Refresh token is required"
            );
        }

        try {
            String tokenType =
                    jwtService.extractTokenType(
                            refreshToken
                    );

            if (!"REFRESH".equals(tokenType)) {
                throw new UnauthorizedException(
                        "Provided token is not a valid refresh token"
                );
            }

            String username =
                    jwtService.extractUsername(
                            refreshToken
                    );

            User user = userRepository
                    .findByUsername(username)
                    .orElseThrow(() ->
                            new ResourceNotFoundException(
                                    "User not found with this username"
                            )
                    );

            if (!jwtService.validateRefreshToken(
                    refreshToken,
                    user.getUsername()
            )) {
                throw new UnauthorizedException(
                        "Invalid refresh token"
                );
            }

            String newAccessToken =
                    jwtService.generateAccessToken(
                            user.getUsername()
                    );

            return createLoginResponse(
                    user,
                    newAccessToken,
                    refreshToken
            );

        } catch (ExpiredJwtException exception) {
            throw new UnauthorizedException(
                    "Refresh token has expired"
            );

        } catch (UnauthorizedException exception) {
            throw exception;

        } catch (JwtException | IllegalArgumentException exception) {
            throw new UnauthorizedException(
                    "Invalid refresh token"
            );
        }
    }

    /*
     * Always returns the same message whether or not the email exists.
     *
     * This prevents attackers from discovering registered addresses.
     */
    public String forgotPasswordService(
            ForgotPasswordRequest request
    ) {
        String email =
                normalizeEmail(request.getEmail());

        Optional<User> optionalUser =
                userRepository.findByEmailIgnoreCase(email);

        if (optionalUser.isEmpty()) {
            return FORGOT_PASSWORD_MESSAGE;
        }

        User user = optionalUser.get();

        String passwordResetToken =
                jwtService.generatePasswordResetToken(
                        user.getUsername()
                );

        /*
         * UriComponentsBuilder safely appends and encodes the token as
         * a query parameter.
         */
        String resetLink =
                UriComponentsBuilder
                        .fromUriString(resetPasswordUrl)
                        .queryParam(
                                "token",
                                passwordResetToken
                        )
                        .build()
                        .encode()
                        .toUriString();

        try {
            emailService.sendPasswordResetEmail(
                    user.getEmail(),
                    resetLink
            );

        } catch (MailException exception) {
            /*
             * Do not expose Gmail or SMTP implementation details.
             * Do not log the reset token or complete reset URL.
             */
            throw new IllegalStateException(
                    "Unable to send password reset email. " +
                            "Please try again later."
            );
        }

        return FORGOT_PASSWORD_MESSAGE;
    }

    @Transactional
    public String resetPasswordService(
            ResetPasswordRequest request
    ) {
        if (!request.getNewPassword().equals(
                request.getConfirmPassword()
        )) {
            throw new ConflictException(
                    "New password and confirmation password " +
                            "do not match."
            );
        }

        String token = request.getToken();

        if (!StringUtils.hasText(token)) {
            throw new UnauthorizedException(
                    "Password reset token is invalid."
            );
        }

        try {
            /*
             * extractTokenType verifies the signature and expiration.
             *
             * An expired token throws ExpiredJwtException.
             * A modified or malformed token throws another JwtException.
             */
            String tokenType =
                    jwtService.extractTokenType(token);

            if (!PASSWORD_RESET_TOKEN_TYPE.equals(
                    tokenType
            )) {
                throw new UnauthorizedException(
                        "Provided token is not a valid " +
                                "password reset token."
                );
            }

            String username =
                    jwtService.extractUsername(token);

            if (!StringUtils.hasText(username)) {
                throw new UnauthorizedException(
                        "Password reset token is invalid."
                );
            }

            User user = userRepository
                    .findByUsername(username)
                    .orElseThrow(() ->
                            new ResourceNotFoundException(
                                    "User not found with this username"
                            )
                    );

            if (!jwtService.validatePasswordResetToken(
                    token,
                    user.getUsername()
            )) {
                throw new UnauthorizedException(
                        "Password reset token is invalid."
                );
            }

            user.setPassword(
                    passwordEncoder.encode(
                            request.getNewPassword()
                    )
            );

            userRepository.save(user);

            return RESET_PASSWORD_SUCCESS_MESSAGE;

        } catch (ExpiredJwtException exception) {
            throw new UnauthorizedException(
                    "Password reset token has expired. " +
                            "Request a new reset link."
            );

        } catch (UnauthorizedException
                 | ResourceNotFoundException exception) {
            throw exception;

        } catch (JwtException | IllegalArgumentException exception) {
            throw new UnauthorizedException(
                    "Password reset token is invalid."
            );

        } catch (DataIntegrityViolationException exception) {
            throw new IllegalStateException(
                    "Unable to reset password. " +
                            "Please try again later."
            );
        }
    }

    private LoginResponse createLoginResponse(
            User user,
            String accessToken,
            String refreshToken
    ) {
        return new LoginResponse(
                user.getUserId(),
                user.getUsername(),
                user.getEmail(),
                user.getPhoneNumber(),
                user.getRole().getRoleName(),
                accessToken,
                refreshToken
        );
    }

    private String normalizeEmail(String email) {
        return normalizeRequiredValue(
                email,
                "Email"
        ).toLowerCase(Locale.ROOT);
    }

    private String normalizeRequiredValue(
            String value,
            String fieldName
    ) {
        if (!StringUtils.hasText(value)) {
            throw new IllegalArgumentException(
                    fieldName + " must not be empty"
            );
        }

        return value.trim();
    }

    private String normalizeOptionalValue(
            String value
    ) {
        if (!StringUtils.hasText(value)) {
            return null;
        }

        return value.trim();
    }
}