package core.basesyntax.dao.impl;

import core.basesyntax.dao.CommentDao;
import core.basesyntax.model.Comment;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

public class CommentDaoImpl extends AbstractDao implements CommentDao {
    public CommentDaoImpl(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    @Override
    public Comment create(Comment entity) {
        Transaction transaction = null;
        Session session = null;
        try {
            session = factory.openSession();
            transaction = session.getTransaction();
            transaction.begin();
            session.persist(entity);
            transaction.commit();
            return entity;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException("Can not add a entity to DB.", e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    @Override
    public Comment get(Long id) {
        Transaction transaction = null;
        try (Session session = factory.openSession()) {
            transaction = session.getTransaction();
            transaction.begin();
            Comment foundedEntity = session.find(Comment.class, id);
            transaction.commit();
            return foundedEntity;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException("Can not get a " + Comment.class.getSimpleName()
                    + " to a DB. ", e);
        }
    }

    @Override
    public List<Comment> getAll() {
        Transaction transaction = null;
        try (Session session = factory.openSession()) {
            transaction = session.getTransaction();
            transaction.begin();
            List<Comment> allFoundedEntities =
                    session.createQuery("FROM " + Comment.class.getName(), Comment.class)
                    .getResultList();

            transaction.commit();
            return allFoundedEntities;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }

            throw new RuntimeException("Can not get a " + Comment.class.getSimpleName()
                    + " to a DB. ", e);

        }
    }

    @Override
    public void remove(Comment entity) {
        Transaction transaction = null;
        try (Session session = factory.openSession()) {
            transaction = session.getTransaction();
            transaction.begin();
            session.remove(entity);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException("Can not delete " + Comment.class.getSimpleName()
                    + " from a DB.", e);
        }
    }

}
