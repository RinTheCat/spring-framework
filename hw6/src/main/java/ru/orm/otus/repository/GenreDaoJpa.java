package ru.orm.otus.repository;

import org.springframework.stereotype.Repository;
import ru.orm.otus.domain.Genre;

import javax.persistence.*;
import java.util.List;

@Repository
public class GenreDaoJpa implements  GenreDao {

    @PersistenceContext
    private final EntityManager em;

    public GenreDaoJpa(EntityManager entityManager) {
        this.em = entityManager;
    }

    @Override
    public Long count() {
        Query query = em.createQuery("select count(g) from Genre g");
        return (Long) query.getSingleResult();
    }

    @Override
    public Genre insert(Genre genre) {
        if (genre.getId() == 0) {
            em.persist(genre);
            return genre;
        } else {
            return em.merge(genre);
        }
    }

    @Override
    public Genre getById(long id) {
        return em.find(Genre.class, id);
    }

    @Override
    public void deleteById(long id) {
        em.remove(em.find(Genre.class, id));
    }

    @Override
    public List<Genre> getByName(String name) {
        TypedQuery<Genre> query = em.createQuery("select g from Genre g where g.name = :name", Genre.class);
        query.setParameter("name", name);
        return query.getResultList();
    }

    @Override
    public List<Genre> getAll() {
        TypedQuery<Genre> query = em.createQuery("select g from Genre g", Genre.class);
        return query.getResultList();
    }
}
