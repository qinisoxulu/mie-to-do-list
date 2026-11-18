package za.co.mie.todolist.controller;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import za.co.mie.todolist.dto.AuthResponseDTO;
import za.co.mie.todolist.dto.UserLoginDTO;
import za.co.mie.todolist.dto.UserRegistrationDTO;
import za.co.mie.todolist.model.UserModel;
import za.co.mie.todolist.service.UserService;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<UserModel> register(@Valid @RequestBody UserRegistrationDTO dto) {
        UserModel user = userService.registerUser(dto);
        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponseDTO> login(@Valid @RequestBody UserLoginDTO dto) {
        AuthResponseDTO response = userService.loginUser(dto);
        return ResponseEntity.ok(response);
    }
}