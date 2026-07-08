package org.example.techtalksskillbasedrecruitment.user;

import org.example.techtalksskillbasedrecruitment.exceptions.ConflictException;
import org.example.techtalksskillbasedrecruitment.exceptions.ResourceNotFoundException;
import org.example.techtalksskillbasedrecruitment.exceptions.UnauthorizedException;
import org.example.techtalksskillbasedrecruitment.role.Role;
import org.example.techtalksskillbasedrecruitment.role.RoleRepository;
import org.example.techtalksskillbasedrecruitment.security.JwtUtil;
import org.example.techtalksskillbasedrecruitment.user.dto.request.CreateUserRequest;
import org.example.techtalksskillbasedrecruitment.user.dto.request.LoginRequest;
import org.example.techtalksskillbasedrecruitment.user.dto.request.UpdateUserRequest;
import org.example.techtalksskillbasedrecruitment.user.dto.response.LoginResponse;
import org.example.techtalksskillbasedrecruitment.user.dto.response.UserResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

    public UserService(UserRepository userRepository, RoleRepository roleRepository,
                        PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager,
                        JwtUtil jwtUtil) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
    }



    public UserResponse createUserService(CreateUserRequest userRequest){
        if (userRepository.existsByEmail(userRequest.getEmail())){
            throw new ConflictException("User already exists with this email");
        }

        if(userRepository.existsByUsername(userRequest.getUsername())){
            throw new ConflictException("User already exists with this username");
        }

        if(userRepository.existsByPhoneNumber(userRequest.getPhoneNumber())) {
            throw new ConflictException("User already exists with this phone number");
        }

        Role role = roleRepository.findById(userRequest.getRoleId()).orElseThrow(() ->
                new ResourceNotFoundException("Role Id not found"));

        User user = new User();
        user.setUsername(userRequest.getUsername());
        user.setPhoneNumber(userRequest.getPhoneNumber());
        user.setRole(role);
        user.setEmail(userRequest.getEmail());
        user.setPassword(passwordEncoder.encode(userRequest.getPassword()));

        User savedUser = userRepository.save(user);
        return new UserResponse(savedUser.getUserId(), savedUser.getUsername(), savedUser.getEmail(),
                savedUser.getPhoneNumber(), savedUser.getRole().getRoleId(),savedUser.getRole().getRoleName(),savedUser.getCreatedAt());
    }

    public LoginResponse loginService(LoginRequest loginRequest) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword())
            );
        } catch (BadCredentialsException ex) {
            throw new UnauthorizedException("Invalid username or password");
        }

        User user = userRepository.findByUsername(loginRequest.getUsername())
                .orElseThrow(() -> new UnauthorizedException("Invalid username or password"));

        String token = jwtUtil.generateToken(user.getUsername());

        return new LoginResponse(token, user.getUserId(), user.getUsername(), user.getEmail(),
                user.getRole().getRoleId(), user.getRole().getRoleName());
    }

    public UserResponse updateUserService(UpdateUserRequest userRequest) {
        if (userRepository.existsByEmailAndUserIdNot(userRequest.getEmail(), userRequest.getUserId())){
            throw new ConflictException("User already exists with this email");
        }

        if(userRepository.existsByUsernameAndUserIdNot(userRequest.getUsername(), userRequest.getUserId())){
            throw new ConflictException("User already exists with this username");
        }

        if(userRepository.existsByPhoneNumberAndUserIdNot(userRequest.getPhoneNumber(), userRequest.getUserId())) {
            throw new ConflictException("User already exists with this phone number");
        }

        User existingUser = userRepository.findById(userRequest.getUserId()).orElseThrow(
                () -> new ResourceNotFoundException("User does not exist with this id")
        );

        Role role = roleRepository.findById(userRequest.getRoleId())
                .orElseThrow(() -> new ResourceNotFoundException("Role not found"));

        existingUser.setUsername(userRequest.getUsername());
        existingUser.setEmail(userRequest.getEmail());
        existingUser.setPhoneNumber(userRequest.getPhoneNumber());
        existingUser.setRole(role);

        User updatedUser = userRepository.save(existingUser);
        return new UserResponse(updatedUser.getUserId(), updatedUser.getUsername(), updatedUser.getEmail(),
                updatedUser.getPhoneNumber(), updatedUser.getRole().getRoleId(),updatedUser.getRole().getRoleName(),updatedUser.getCreatedAt());
    }

    public List<UserResponse> getAllUsersService() {
        List<User> userList = userRepository.findAll();
        List<UserResponse> userResponseList = new ArrayList<>();
        for (User user : userList) {
            UserResponse userResponse =  new UserResponse(user.getUserId(), user.getUsername(), user.getEmail(),
                    user.getPhoneNumber(), user.getRole().getRoleId(),user.getRole().getRoleName(),user.getCreatedAt());
            userResponseList.add(userResponse);
        }


        return userResponseList;
    }

}
