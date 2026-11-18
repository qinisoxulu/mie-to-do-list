package za.co.mie.todolist.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import za.co.mie.todolist.dto.AuthResponseDTO;
import za.co.mie.todolist.dto.UserLoginDTO;
import za.co.mie.todolist.dto.UserRegistrationDTO;
import za.co.mie.todolist.model.AuthTokenModel;
import za.co.mie.todolist.model.UserModel;
import za.co.mie.todolist.repository.AuthTokenRepository;
import za.co.mie.todolist.repository.UserRepository;
import java.util.UUID;

@Service
public class UserService {

    private final UserRepository user_repository;
    private final AuthTokenRepository auth_token_repository;
    private final PasswordEncoder password_encoder;

    public UserService(UserRepository user_repository, AuthTokenRepository auth_token_repository, PasswordEncoder password_encoder) {
        this.user_repository = user_repository;
        this.auth_token_repository = auth_token_repository;
        this.password_encoder = password_encoder;
    }

    public UserModel registerUser(UserRegistrationDTO dto) {
        if (user_repository.existsByUsername(dto.getUsername())) {
            throw new RuntimeException("Username is already taken!");
        }

        UserModel user = new UserModel();
        user.setUsername(dto.getUsername());
        user.setPassword(password_encoder.encode(dto.getPassword()));
        user.setFirstName(dto.getFirstName());
        user.setLastName(dto.getLastName());
        return user_repository.save(user);
    }

    public AuthResponseDTO loginUser(UserLoginDTO dto) {
        UserModel user = user_repository.findByUsername(dto.getUsername()).orElseThrow(() -> new RuntimeException("Invalid username or password"));
        if (!password_encoder.matches(dto.getPassword(), user.getPassword())) {
            throw new RuntimeException("Invalid username or password");
        }
        String tokenString = UUID.randomUUID().toString();
        AuthTokenModel authToken = new AuthTokenModel();
        authToken.setToken(tokenString);
        authToken.setUser(user);
        auth_token_repository.save(authToken);
        return new AuthResponseDTO(user.getUsername(), tokenString, "Login successful!");
    }

    public UserModel getUserByToken(String authHeader) {
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            throw new RuntimeException("Missing or invalid Authorization header");
        }
        String token = authHeader.substring(7);
        AuthTokenModel authToken = auth_token_repository.findByToken(token).orElseThrow(() -> new RuntimeException("Invalid or expired token"));
        return authToken.getUser();
    }
}