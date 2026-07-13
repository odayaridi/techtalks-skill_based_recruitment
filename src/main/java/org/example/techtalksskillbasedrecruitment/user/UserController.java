package org.example.techtalksskillbasedrecruitment.user;

import lombok.Getter;
import org.example.techtalksskillbasedrecruitment.common.response.PaginatedResponse;
import org.example.techtalksskillbasedrecruitment.common.response.PaginationMeta;
import org.example.techtalksskillbasedrecruitment.user.dto.request.CreateUserRequest;
import org.example.techtalksskillbasedrecruitment.user.dto.request.UpdateUserRequest;
import org.example.techtalksskillbasedrecruitment.user.dto.response.UserResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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

    @PutMapping("/update")
    public ResponseEntity<UserResponse> updateUserController(
            @RequestBody UpdateUserRequest userRequest) {

        UserResponse updatedUser = userService.updateUserService(userRequest);
        return ResponseEntity.ok(updatedUser);
    }

    @GetMapping("/getAll")
    public ResponseEntity<PaginatedResponse<UserResponse>> getAllUsersController(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int limit
    ) {

        Pageable pageable = PageRequest.of(page, limit);

        Page<UserResponse> userPage = userService.getAllUsersService(pageable);

        PaginationMeta meta = new PaginationMeta(
                userPage.getNumber(),
                userPage.getSize(),
                userPage.getTotalElements(),
                userPage.getTotalPages(),
                userPage.isFirst(),
                userPage.isLast()
        );

        PaginatedResponse<UserResponse> response =
                new PaginatedResponse<>(
                        userPage.getContent(),
                        meta
                );

        return ResponseEntity.ok(response);
    }
}
