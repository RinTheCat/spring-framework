//package ru.otus.project.domain.status;
//
//import ru.otus.project.domain.Order;
//import ru.otus.project.domain.Status;
//
//import javax.persistence.Entity;
//
//@Entity
//public class Inspection extends Status {
//    @Override
//    public void next(Order order) {
//        System.out.println("No next");
//    }
//
//    @Override
//    public void prev(Order order) {
//        order.setStatus(new Assembly());
//    }
//}
