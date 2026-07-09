package org.example.techtalksskillbasedrecruitment.user;

import org.example.techtalksskillbasedrecruitment.exceptions.ConflictException;
import org.example.techtalksskillbasedrecruitment.exceptions.ResourceNotFoundException;
import org.example.techtalksskillbasedrecruitment.role.Role;
import org.example.techtalksskillbasedrecruitment.role.RoleRepository;
import org.example.techtalksskillbasedrecruitment.user.dto.request.CreateUserRequest;
import org.example.techtalksskillbasedrecruitment.user.dto.request.UpdateUserRequest;
import org.example.techtalksskillbasedrecruitment.user.dto.response.UserResponse;
import org.example.techtalksskillbasedrecruitment.user.mapper.UserMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final UserMapper userMapper;


    public UserService(
            UserRepository userRepository,
            RoleRepository roleRepository,
            UserMapper userMapper
    ) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.userMapper = userMapper;
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
        user.setPassword(userRequest.getPassword());


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


    public List<UserResponse> getAllUsersService() {

        return userRepository.findAll()
                .stream()
                .map(userMapper::toUserResponseDTO)
                .toList();
    }
}