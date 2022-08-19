//package ru.otus.project.service;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//import ru.otus.project.domain.Order;
//import ru.otus.project.repository.OrderRepository;
//
//import javax.persistence.EntityNotFoundException;
//import java.util.List;
//import java.util.Optional;
//
//@Service
//public class OrderService {
//
//    private final OrderRepository orderRepository;
//
//    @Autowired
//    public OrderService(OrderRepository orderRepository) {
//        this.orderRepository = orderRepository;
//    }
//
//    public Long count() {
//        return orderRepository.count();
//    }
//
//    public Order insert(Order order) {
//        return orderRepository.save(order);
//    }
//
//    public Optional<Order> getById(long id) {
//        return orderRepository.findById(id);
//    }
//
//    @Transactional
//    public void deleteById(long id) {
//        orderRepository.deleteById(id);
//    }
//
//    @Transactional
//    public void pushStatus(long id) {
//        Optional<Order> order = orderRepository.findById(id);
//        if (order.isEmpty()) throw new EntityNotFoundException(String.format("Not found with id=%s", id));
//        order.get().nextStatus();
//        orderRepository.save(order.get());
//    }
//
//    @Transactional
//    public void pullStatus(long id) {
//        Optional<Order> order = orderRepository.findById(id);
//        if (order.isEmpty()) throw new EntityNotFoundException(String.format("Not found with id=%s", id));
//        order.get().previousStatus();
//        orderRepository.save(order.get());
//    }
//
//    public List<Order> getAll() {
//        return orderRepository.findAll();
//    }
//}
