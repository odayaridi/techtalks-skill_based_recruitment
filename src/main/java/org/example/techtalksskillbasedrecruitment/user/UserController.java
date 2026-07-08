package org.example.techtalksskillbasedrecruitment.user;

import lombok.Getter;
import org.example.techtalksskillbasedrecruitment.user.dto.request.CreateUserRequest;
import org.example.techtalksskillbasedrecruitment.user.dto.request.LoginRequest;
import org.example.techtalksskillbasedrecruitment.user.dto.request.UpdateUserRequest;
import org.example.techtalksskillbasedrecruitment.user.dto.response.LoginResponse;
import org.example.techtalksskillbasedrecruitment.user.dto.response.UserResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*", allowCredentials = "false")
@RestController
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/create")
    public ResponseEntity<UserResponse> createUserController(@RequestBody CreateUserRequest userRequest) {
        UserResponse newUser = userService.createUserService(userRequest);
        return new ResponseEntity<UserResponse>(newUser, HttpStatus.CREATED);
    }


    @PostMapping("/login")
    public ResponseEntity<LoginResponse> loginUserController(@RequestBody LoginRequest loginRequest) {
        LoginResponse loginResponse = userService.loginUserService(loginRequest);
        return ResponseEntity.ok(loginResponse);
    }
    @PutMapping("/update")
    public ResponseEntity<UserResponse> updateUserController(
            @RequestBody UpdateUserRequest userRequest) {

        UserResponse updatedUser = userService.updateUserService(userRequest);
        return ResponseEntity.ok(updatedUser);
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<UserResponse>> getAllUsersController() {
        return ResponseEntity.ok(userService.getAllUsersService());
    }
}
