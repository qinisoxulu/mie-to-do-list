package za.co.mie.todolist.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import za.co.mie.todolist.model.AuthTokenModel;
import za.co.mie.todolist.model.UserModel;
import java.util.Optional;

import java.util.UUID;

@Repository
public interface AuthTokenRepository extends JpaRepository<AuthTokenModel, UUID> {
    Optional<AuthTokenModel> findByToken(String token);
    void deleteByUser(UserModel user);
}