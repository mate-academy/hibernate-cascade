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
    public MessageDaoImpl(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    @Override
    public Message create(Message entity) {
        Session session = null;
        Transaction transaction = null;
        try {
            session = factory.openSession();
            transaction = session.beginTransaction();
            session.persist(entity);
            transaction.commit();
        } catch (RuntimeException ex) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException("Message creating failure! "
                    + "Message: " + entity, ex);
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return entity;
    }

    @Override
    public Message get(Long id) {
        try (Session session = factory.openSession()) {
            return session.get(Message.class, id);
        } catch (RuntimeException ex) {
            throw new RuntimeException("Message getting failure! "
                    + "Message Id: " + id, ex);
        }
    }

    @Override
    public List<Message> getAll() {
        try (Session session = factory.openSession()) {
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<Message> criteria = builder.createQuery(Message.class);
            criteria.from(Message.class);
            return session.createQuery(criteria).getResultList();
        } catch (RuntimeException ex) {
            throw new RuntimeException("Getting all data failure!", ex);
        }
    }

    @Override
    public void remove(Message entity) {
        Session session = null;
        Transaction transaction = null;
        try {
            session = factory.openSession();
            transaction = session.beginTransaction();
            session.remove(entity);
            transaction.commit();
        } catch (RuntimeException ex) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException("Message removing failure! "
                    + "Message: " + entity, ex);
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }
}
