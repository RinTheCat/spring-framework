//package ru.otus.project.domain.status;
//
//import ru.otus.project.domain.Order;
//import ru.otus.project.domain.Status;
//
//import javax.persistence.Entity;
//
//@Entity
//public class Created extends Status {
//    @Override
//    public void next(Order order) {
//        order.setStatus(new Assembly());
//    }
//
//    @Override
//    public void prev(Order order) {
//        System.out.println("This is the root status");
//    }
//}
