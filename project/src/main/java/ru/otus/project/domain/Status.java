package ru.otus.project.domain;

import ru.otus.project.domain.status.Cancelled;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.MappedSuperclass;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.List;

@Entity
@Table(name = "statuses")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public abstract class Status {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "name")
    private String name;

    @OneToMany(orphanRemoval = true, mappedBy = "status", fetch = FetchType.EAGER)
    private List<Order> orders;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public abstract long next(Order order);
    public abstract long prev(Order order);

    public void cancel(Order order) {
        order.setStatus(new Cancelled());
    }
}
