package core.basesyntax.dao.impl;

import core.basesyntax.dao.MessageDao;
import core.basesyntax.model.Message;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

public class MessageDaoImpl extends AbstractDao implements MessageDao {

    private final SessionFactory sessionFactory;

    public MessageDaoImpl(SessionFactory sessionFactory) {
        super(sessionFactory);
        this.sessionFactory = sessionFactory;
    }

    @Override
    public Message create(Message entity) {
        Transaction transaction = null;
        Session session = null;
        try {
            session = sessionFactory.openSession();
            transaction = session.beginTransaction();
            session.persist(entity);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException("Saving Message to Database Error, cause: "
                    + e.getMessage(), e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return entity;
    }

    @Override
    public Message get(Long id) {
        try (Session session = sessionFactory.openSession()) {
            return session.get(Message.class,id);
        } catch (Exception e) {
            throw new RuntimeException("Loading Message from Database Error, cause: "
                    + e.getMessage(), e);
        }
    }

    @Override
    public List<Message> getAll() {
        try (Session session = sessionFactory.openSession()) {
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<Message> criteria = builder.createQuery(Message.class);
            criteria.from(Message.class);
            return session.createQuery(criteria).getResultList();
        } catch (Exception e) {
            throw new RuntimeException("Loading all Messages from Database Error, cause: "
                    + e.getMessage(), e);
        }
    }

    @Override
    public void remove(Message entity) {
        Transaction transaction = null;
        Session session = null;
        try {
            session = sessionFactory.openSession();
            transaction = session.beginTransaction();
            session.remove(entity);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException("Removing Message to Database Error, cause: "
                    + e.getMessage(), e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }
}
