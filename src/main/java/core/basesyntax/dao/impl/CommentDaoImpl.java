package core.basesyntax.dao.impl;

import core.basesyntax.dao.CommentDao;
import core.basesyntax.model.Comment;
import java.util.List;
import javax.persistence.criteria.CriteriaQuery;
import lombok.extern.log4j.Log4j;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

@Log4j
public class CommentDaoImpl extends AbstractDao implements CommentDao {
    public CommentDaoImpl(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    @Override
    public Comment create(Comment comment) {
        log.info("Calling a create() method of CommentDaoImpl class");
        Session session = null;
        Transaction transaction;
        try {
            session = factory.openSession();
            transaction = session.beginTransaction();
            session.save(comment);
            log.info("Attempt to save comment " + comment + " in db.");
            transaction.commit();
            return comment;
        } catch (Exception e) {
            if (session.getTransaction() != null) {
                session.getTransaction().rollback();
            }
            throw new RuntimeException("Can't insert Content entity", e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    @Override
    public Comment get(Long id) {
        log.info("Calling a get() method of CommentDaoImpl class");
        try (Session session = factory.openSession()) {
            return session.get(Comment.class, id);
        }
    }

    @Override
    public List<Comment> getAll() {
        log.info("Calling a getAll() method of CommentDaoImpl class");
        try (Session session = factory.openSession()) {
            CriteriaQuery<Comment> criteriaQuery = session.getCriteriaBuilder()
                    .createQuery(Comment.class);
            criteriaQuery.from(Comment.class);
            log.info("Attempt to retrieve collection of comments from db.");
            return session.createQuery(criteriaQuery).getResultList();
        } catch (Exception e) {
            throw new RuntimeException("Error retrieving all comments. ", e);
        }
    }

    @Override
    public void remove(Comment comment) {
        log.info("Calling a remove() method of CommentDaoImpl class");
        Session session = null;
        Transaction transaction = null;
        try {
            session = factory.openSession();
            transaction = session.beginTransaction();
            session.remove(comment);
            log.info("Attempt to remove comment " + comment + " from db");
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException("Can't delete comment entity. ", e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }
}
