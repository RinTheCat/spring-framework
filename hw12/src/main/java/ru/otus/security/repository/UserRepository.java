package ru.otus.security.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.security.domain.User;

public interface UserRepository extends JpaRepository<User, Long> {

    User findByUsername(String username);
}
