package ru.orm.otus.repository;

import org.springframework.stereotype.Repository;
import ru.orm.otus.domain.Book;

import javax.persistence.*;
import java.util.List;

@Repository
public class BookDaoJpa implements  BookDao {

    @PersistenceContext
    private final EntityManager em;

    public BookDaoJpa(EntityManager entityManager) {
        this.em = entityManager;
    }

    @Override
    public Long count() {
        Query query = em.createQuery("select count(b) from Book b");
        return (Long) query.getSingleResult();
    }

    @Override
    public Book insert(Book book) {
        if (book.getId() == 0) {
            em.persist(book);
            return book;
        } else {
            return em.merge(book);
        }
    }

    @Override
    public Book getById(long id) {
        TypedQuery<Book> query = em.createQuery("select b from Book b join fetch b.author join fetch b.genre " +
                                                                        "where b.id = :id", Book.class);
        query.setParameter("id", id);
        try {
            return query.getSingleResult();
        } catch (NoResultException exception) {
            return null;
        }
    }

    @Override
    public void deleteById(long id) {
        em.remove(em.find(Book.class, id));
    }

    @Override
    public List<Book> getByTitle(String title) {
        TypedQuery<Book> query = em.createQuery("select b from Book b join fetch b.author join fetch b.genre" +
                                                                " where b.title = :title", Book.class);
        query.setParameter("title", title);
        return query.getResultList();
    }

    @Override
    public List<Book> getAll() {
        TypedQuery<Book> query = em.createQuery("select b from Book b join fetch b.author join fetch b.genre", Book.class);
        return query.getResultList();
    }
}
