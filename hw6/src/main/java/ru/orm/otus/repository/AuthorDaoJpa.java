package ru.orm.otus.repository;

import org.springframework.stereotype.Repository;
import ru.orm.otus.domain.Author;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.List;

@Repository
public class AuthorDaoJpa implements  AuthorDao {

    @PersistenceContext
    private final EntityManager em;

    public AuthorDaoJpa(EntityManager entityManager) {
        this.em = entityManager;
    }

    @Override
    public Long count() {
        Query query = em.createQuery("select count(a) from Author a");
        return (Long) query.getSingleResult();
    }

    @Override
    public Author insert(Author author) {
        if (author.getId() == 0) {
            em.persist(author);
            return author;
        } else {
            return em.merge(author);
        }
    }

    @Override
    public Author getById(long id) {
        return em.find(Author.class, id);
    }

    @Override
    public void deleteById(long id) {
        em.remove(em.find(Author.class, id));
    }

    @Override
    public List<Author> getByFullName(String fullName) {
        TypedQuery<Author> query = em.createQuery("select a from Author a where a.fullName = :fullName", Author.class);
        query.setParameter("fullName", fullName);
        return query.getResultList();
    }

    @Override
    public List<Author> getAll() {
        TypedQuery<Author> query = em.createQuery("select a from Author a", Author.class);
        return query.getResultList();
    }
}
