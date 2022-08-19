//package ru.otus.project.domain.status;
//
//import ru.otus.project.domain.Order;
//import ru.otus.project.domain.Status;
//
//import javax.persistence.Entity;
//
//@Entity
//public class Sent extends Status {
//    @Override
//    public void next(Order order) {
//        order.setStatus(new Closed());
//    }
//
//    @Override
//    public void prev(Order order) {
//        order.setStatus(new Inspection());
//    }
//}
