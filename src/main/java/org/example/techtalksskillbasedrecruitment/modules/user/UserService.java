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


package org.example.techtalksskillbasedrecruitment.modules.user;

import org.example.techtalksskillbasedrecruitment.common.exceptions.ConflictException;
import org.example.techtalksskillbasedrecruitment.common.exceptions.ResourceNotFoundException;
import org.example.techtalksskillbasedrecruitment.common.exceptions.UnauthorizedException;
import org.example.techtalksskillbasedrecruitment.modules.role.Role;
import org.example.techtalksskillbasedrecruitment.modules.role.RoleRepository;
import org.example.techtalksskillbasedrecruitment.security.jwt.JwtService;
import org.example.techtalksskillbasedrecruitment.modules.user.dto.request.CreateUserRequest;
import org.example.techtalksskillbasedrecruitment.modules.user.dto.request.LoginRequest;
import org.example.techtalksskillbasedrecruitment.modules.user.dto.request.RefreshTokenRequest;
import org.example.techtalksskillbasedrecruitment.modules.user.dto.request.UpdateUserRequest;
import org.example.techtalksskillbasedrecruitment.modules.user.dto.response.LoginResponse;
import org.example.techtalksskillbasedrecruitment.modules.user.dto.response.UserResponse;
import org.example.techtalksskillbasedrecruitment.modules.user.mapper.UserMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;


    public UserService(
            UserRepository userRepository,
            RoleRepository roleRepository,
            UserMapper userMapper,
            PasswordEncoder passwordEncoder,
            AuthenticationManager authenticationManager,
            JwtService jwtService
    ) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.userMapper = userMapper;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
    }


    public UserResponse createUserService(CreateUserRequest userRequest) {

        if (userRepository.existsByEmail(userRequest.getEmail())) {
            throw new ConflictException("User already exists with this email");
        }

        if (userRepository.existsByUsername(userRequest.getUsername())) {
            throw new ConflictException("User already exists with this username");
        }

        if (userRepository.existsByPhoneNumber(userRequest.getPhoneNumber())) {
            throw new ConflictException("User already exists with this phone number");
        }


        Role role = roleRepository.findById(userRequest.getRoleId())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Role Id not found")
                );


        User user = new User();

        user.setUsername(userRequest.getUsername());
        user.setPhoneNumber(userRequest.getPhoneNumber());
        user.setRole(role);
        user.setEmail(userRequest.getEmail());
        user.setPassword(passwordEncoder.encode(userRequest.getPassword()));


        User savedUser = userRepository.save(user);

        return userMapper.toUserResponseDTO(savedUser);
    }


    public UserResponse updateUserService(UpdateUserRequest userRequest) {


        if (userRepository.existsByEmailAndUserIdNot(
                userRequest.getEmail(),
                userRequest.getUserId()
        )) {
            throw new ConflictException("User already exists with this email");
        }


        if (userRepository.existsByUsernameAndUserIdNot(
                userRequest.getUsername(),
                userRequest.getUserId()
        )) {
            throw new ConflictException("User already exists with this username");
        }


        if (userRepository.existsByPhoneNumberAndUserIdNot(
                userRequest.getPhoneNumber(),
                userRequest.getUserId()
        )) {
            throw new ConflictException("User already exists with this phone number");
        }


        User existingUser = userRepository.findById(userRequest.getUserId())
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "User does not exist with this id"
                        )
                );


        Role role = roleRepository.findById(userRequest.getRoleId())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Role not found")
                );


        existingUser.setUsername(userRequest.getUsername());
        existingUser.setEmail(userRequest.getEmail());
        existingUser.setPhoneNumber(userRequest.getPhoneNumber());
        existingUser.setRole(role);


        User updatedUser = userRepository.save(existingUser);

        return userMapper.toUserResponseDTO(updatedUser);
    }


    public Page<UserResponse> getAllUsersService(Pageable pageable) {

        return userRepository.findAll(pageable)
                .map(userMapper::toUserResponseDTO);
    }


    public LoginResponse loginUserService(LoginRequest loginRequest) {

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getUsername(),
                        loginRequest.getPassword()
                )
        );

        User user = userRepository.findByUsername(loginRequest.getUsername())
                .orElseThrow(() -> new ResourceNotFoundException("User not found with this username"));

        String accessToken = jwtService.generateAccessToken(user.getUsername());
        String refreshToken = jwtService.generateRefreshToken(user.getUsername());

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


    public LoginResponse refreshTokenService(RefreshTokenRequest refreshTokenRequest) {

        String refreshToken = refreshTokenRequest.getRefreshToken();

        if (!jwtService.isRefreshToken(refreshToken)) {
            throw new UnauthorizedException("Provided token is not a valid refresh token");
        }

        if (jwtService.isTokenExpired(refreshToken)) {
            throw new UnauthorizedException("Refresh token has expired");
        }

        String username = jwtService.extractUsername(refreshToken);

        User user = userRepository.findByUsername(username)
                .orElseThrow(() ->
                        new ResourceNotFoundException("User not found with this username"));

        if (!jwtService.validateRefreshToken(refreshToken, user.getUsername())) {
            throw new UnauthorizedException("Invalid refresh token");
        }

        String newAccessToken = jwtService.generateAccessToken(user.getUsername());

        return new LoginResponse(
                user.getUserId(),
                user.getUsername(),
                user.getEmail(),
                user.getPhoneNumber(),
                user.getRole().getRoleName(),
                newAccessToken,
                refreshToken
        );
    }
}