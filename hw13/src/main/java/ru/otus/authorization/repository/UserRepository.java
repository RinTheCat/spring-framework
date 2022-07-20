package ru.otus.authorization.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.authorization.domain.User;

public interface UserRepository extends JpaRepository<User, Long> {

    User findByUsername(String username);
}
