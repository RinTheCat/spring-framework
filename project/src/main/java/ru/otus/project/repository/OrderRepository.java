package ru.otus.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.project.domain.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
