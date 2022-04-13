package ru.orm.otus.repository;

import org.springframework.stereotype.Repository;
import ru.orm.otus.domain.Comment;

import javax.persistence.*;
import java.util.List;

@Repository
public class CommentDaoJpa implements CommentDao {

    @PersistenceContext
    private final EntityManager em;

    public CommentDaoJpa(EntityManager entityManager) {
        this.em = entityManager;
    }

    @Override
    public Long count() {
        Query query = em.createQuery("select count(c) from Comment c");
        return (Long) query.getSingleResult();
    }

    @Override
    public Comment insert(Comment comment) {
        if (comment.getId() == 0) {
            em.persist(comment);
            return comment;
        } else {
            return em.merge(comment);
        }
    }

    @Override
    public Comment getById(long id) {
        TypedQuery<Comment> query = em.createQuery("select c from Comment c join fetch c.book " +
                "where c.id = :id", Comment.class);
        query.setParameter("id", id);
        try {
            return query.getSingleResult();
        } catch (NoResultException exception) {
            return null;
        }
    }

    @Override
    public void deleteById(long id) {
        em.remove(em.find(Comment.class, id));
    }

    @Override
    public List<Comment> getAll() {
        TypedQuery<Comment> query = em.createQuery("select c from Comment c join fetch c.book", Comment.class);
        return query.getResultList();
    }
}
