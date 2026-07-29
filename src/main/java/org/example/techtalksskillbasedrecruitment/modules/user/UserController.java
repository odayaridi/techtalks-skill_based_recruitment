//package org.example.techtalksskillbasedrecruitment.modules.user;
//
//import org.example.techtalksskillbasedrecruitment.common.pagination.PaginatedResponse;
//import org.example.techtalksskillbasedrecruitment.common.pagination.PaginationMeta;
//import org.example.techtalksskillbasedrecruitment.ratelimit.RateLimit;
//import org.example.techtalksskillbasedrecruitment.modules.user.dto.request.CreateUserRequest;
//import org.example.techtalksskillbasedrecruitment.modules.user.dto.request.LoginRequest;
//import org.example.techtalksskillbasedrecruitment.modules.user.dto.request.UpdateUserRequest;
//import org.example.techtalksskillbasedrecruitment.modules.user.dto.response.LoginResponse;
//import org.example.techtalksskillbasedrecruitment.modules.user.dto.response.UserResponse;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.PageRequest;
//import org.springframework.data.domain.Pageable;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.security.access.prepost.PreAuthorize;
//import org.springframework.web.bind.annotation.*;
//
//@CrossOrigin(origins = "*", allowCredentials = "false")
//@RestController
//@RequestMapping("/api/users")
//public class UserController {
//    private final UserService userService;
//
//    public UserController(UserService userService) {
//        this.userService = userService;
//    }
//
//
//    @PostMapping("/create")
//    public ResponseEntity<UserResponse> createUserController(@RequestBody CreateUserRequest userRequest) {
//        UserResponse newUser = userService.createUserService(userRequest);
//        return new ResponseEntity<UserResponse>(newUser, HttpStatus.CREATED);
//    }
//
//    @PutMapping("/update")
//    public ResponseEntity<UserResponse> updateUserController(
//            @RequestBody UpdateUserRequest userRequest) {
//
//        UserResponse updatedUser = userService.updateUserService(userRequest);
//        return ResponseEntity.ok(updatedUser);
//    }
//
//
//    @GetMapping("/getAll")
//    @PreAuthorize("hasRole('CANDIDATE')")
//    @RateLimit(requests = 3, windowSeconds = 60)
//    public ResponseEntity<PaginatedResponse<UserResponse>> getAllUsersController(
//            @RequestParam(defaultValue = "0") int page,
//            @RequestParam(defaultValue = "10") int limit
//    ) {
//
//        Pageable pageable = PageRequest.of(page, limit);
//
//        Page<UserResponse> userPage = userService.getAllUsersService(pageable);
//
//        PaginationMeta meta = new PaginationMeta(
//                userPage.getNumber(),
//                userPage.getSize(),
//                userPage.getTotalElements(),
//                userPage.getTotalPages(),
//                userPage.isFirst(),
//                userPage.isLast()
//        );
//
//        PaginatedResponse<UserResponse> response =
//                new PaginatedResponse<>(
//                        userPage.getContent(),
//                        meta
//                );
//
//        return ResponseEntity.ok(response);
//    }
//
//    @PostMapping("/login")
//    public ResponseEntity<LoginResponse> loginUserController(@RequestBody LoginRequest loginRequest) {
//        LoginResponse loginResponse = userService.loginUserService(loginRequest);
//        return ResponseEntity.ok(loginResponse);
//    }
//}









//
//package org.example.techtalksskillbasedrecruitment.modules.user;
//
//import org.example.techtalksskillbasedrecruitment.common.pagination.PaginatedResponse;
//import org.example.techtalksskillbasedrecruitment.common.pagination.PaginationMeta;
//import org.example.techtalksskillbasedrecruitment.ratelimit.RateLimit;
//import org.example.techtalksskillbasedrecruitment.modules.user.dto.request.CreateUserRequest;
//import org.example.techtalksskillbasedrecruitment.modules.user.dto.request.LoginRequest;
//import org.example.techtalksskillbasedrecruitment.modules.user.dto.request.RefreshTokenRequest;
//import org.example.techtalksskillbasedrecruitment.modules.user.dto.request.UpdateUserRequest;
//import org.example.techtalksskillbasedrecruitment.modules.user.dto.response.LoginResponse;
//import org.example.techtalksskillbasedrecruitment.modules.user.dto.response.UserResponse;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.PageRequest;
//import org.springframework.data.domain.Pageable;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.security.access.prepost.PreAuthorize;
//import org.springframework.web.bind.annotation.*;
//
//@CrossOrigin(origins = "*", allowCredentials = "false")
//@RestController
//@RequestMapping("/api/users")
//public class UserController {
//    private final UserService userService;
//
//    public UserController(UserService userService) {
//        this.userService = userService;
//    }
//
//
//    @PostMapping("/create")
//    public ResponseEntity<UserResponse> createUserController(@RequestBody CreateUserRequest userRequest) {
//        UserResponse newUser = userService.createUserService(userRequest);
//        return new ResponseEntity<UserResponse>(newUser, HttpStatus.CREATED);
//    }
//
//    @PutMapping("/update")
//    public ResponseEntity<UserResponse> updateUserController(
//            @RequestBody UpdateUserRequest userRequest) {
//
//        UserResponse updatedUser = userService.updateUserService(userRequest);
//        return ResponseEntity.ok(updatedUser);
//    }
//
//
//    @GetMapping("/getAll")
//    @RateLimit(requests = 3, windowSeconds = 60)
//    public ResponseEntity<PaginatedResponse<UserResponse>> getAllUsersController(
//            @RequestParam(defaultValue = "0") int page,
//            @RequestParam(defaultValue = "10") int limit
//    ) {
//
//        Pageable pageable = PageRequest.of(page, limit);
//
//        Page<UserResponse> userPage = userService.getAllUsersService(pageable);
//
//        PaginationMeta meta = new PaginationMeta(
//                userPage.getNumber(),
//                userPage.getSize(),
//                userPage.getTotalElements(),
//                userPage.getTotalPages(),
//                userPage.isFirst(),
//                userPage.isLast()
//        );
//
//        PaginatedResponse<UserResponse> response =
//                new PaginatedResponse<>(
//                        userPage.getContent(),
//                        meta
//                );
//
//        return ResponseEntity.ok(response);
//    }
//
//    @PostMapping("/login")
//    public ResponseEntity<LoginResponse> loginUserController(@RequestBody LoginRequest loginRequest) {
//        LoginResponse loginResponse = userService.loginUserService(loginRequest);
//        return ResponseEntity.ok(loginResponse);
//    }
//
//    @PostMapping("/refresh")
//    public ResponseEntity<LoginResponse> refreshTokenController(@RequestBody RefreshTokenRequest refreshTokenRequest) {
//        LoginResponse loginResponse = userService.refreshTokenService(refreshTokenRequest);
//        return ResponseEntity.ok(loginResponse);
//    }
//}










package org.example.techtalksskillbasedrecruitment.modules.user;

import jakarta.validation.Valid;
import org.example.techtalksskillbasedrecruitment.common.pagination.PaginatedResponse;
import org.example.techtalksskillbasedrecruitment.common.pagination.PaginationMeta;
import org.example.techtalksskillbasedrecruitment.modules.user.dto.request.CreateUserRequest;
import org.example.techtalksskillbasedrecruitment.modules.user.dto.request.ForgotPasswordRequest;
import org.example.techtalksskillbasedrecruitment.modules.user.dto.request.LoginRequest;
import org.example.techtalksskillbasedrecruitment.modules.user.dto.request.RefreshTokenRequest;
import org.example.techtalksskillbasedrecruitment.modules.user.dto.request.ResetPasswordRequest;
import org.example.techtalksskillbasedrecruitment.modules.user.dto.request.UpdateUserRequest;
import org.example.techtalksskillbasedrecruitment.modules.user.dto.response.LoginResponse;
import org.example.techtalksskillbasedrecruitment.modules.user.dto.response.UserResponse;
import org.example.techtalksskillbasedrecruitment.ratelimit.RateLimit;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@CrossOrigin(
        origins = "*",
        allowCredentials = "false"
)
@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(
            UserService userService
    ) {
        this.userService = userService;
    }

    @PostMapping("/create")
    public ResponseEntity<UserResponse> createUserController(
            @Valid @RequestBody
            CreateUserRequest userRequest
    ) {
        UserResponse newUser =
                userService.createUserService(userRequest);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(newUser);
    }

    @PutMapping("/update")
    public ResponseEntity<UserResponse> updateUserController(
            @Valid @RequestBody
            UpdateUserRequest userRequest
    ) {
        UserResponse updatedUser =
                userService.updateUserService(userRequest);

        return ResponseEntity.ok(updatedUser);
    }

    @GetMapping("/getAll")
    @RateLimit(
            requests = 3,
            windowSeconds = 60
    )
    public ResponseEntity<PaginatedResponse<UserResponse>>
    getAllUsersController(
            @RequestParam(defaultValue = "0")
            int page,

            @RequestParam(defaultValue = "10")
            int limit
    ) {
        Pageable pageable =
                PageRequest.of(page, limit);

        Page<UserResponse> userPage =
                userService.getAllUsersService(pageable);

        PaginationMeta meta =
                new PaginationMeta(
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

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> loginUserController(
            @Valid @RequestBody
            LoginRequest loginRequest
    ) {
        LoginResponse loginResponse =
                userService.loginUserService(loginRequest);

        return ResponseEntity.ok(loginResponse);
    }

    @PostMapping("/refresh")
    public ResponseEntity<LoginResponse> refreshTokenController(
            @Valid @RequestBody
            RefreshTokenRequest refreshTokenRequest
    ) {
        LoginResponse loginResponse =
                userService.refreshTokenService(
                        refreshTokenRequest
                );

        return ResponseEntity.ok(loginResponse);
    }

    /*
     * Public endpoint.
     *
     * The same generic response is returned whether the email exists or not.
     */
    @PostMapping("/forgot-password")
    @RateLimit(
            requests = 3,
            windowSeconds = 300
    )
    public ResponseEntity<Map<String, String>>
    forgotPasswordController(
            @Valid @RequestBody
            ForgotPasswordRequest request
    ) {
        String message =
                userService.forgotPasswordService(request);

        return ResponseEntity.ok(
                Map.of("message", message)
        );
    }

    /*
     * Public endpoint.
     *
     * It accepts only a valid PASSWORD_RESET token.
     */
    @PostMapping("/reset-password")
    public ResponseEntity<Map<String, String>>
    resetPasswordController(
            @Valid @RequestBody
            ResetPasswordRequest request
    ) {
        String message =
                userService.resetPasswordService(request);

        return ResponseEntity.ok(
                Map.of("message", message)
        );
    }
}