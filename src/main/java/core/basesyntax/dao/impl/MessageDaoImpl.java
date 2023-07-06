package core.basesyntax.dao.impl;

import core.basesyntax.dao.MessageDao;
import core.basesyntax.model.Message;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

public class MessageDaoImpl extends AbstractDao implements MessageDao {
    public MessageDaoImpl(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    @Override
    public Message create(Message entity) {
        EntityManager session = null;
        EntityTransaction transaction = null;
        try {
            session = factory.createEntityManager();
            transaction = session.getTransaction();
            transaction.begin();
            session.persist(entity);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException("An error occurred while creating a message to the DB. "
                    + "Message: " + entity, e);
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
        } catch (Exception e) {
            throw new RuntimeException("Can't get a message with id: " + id, e);
        }
    }

    @Override
    public List<Message> getAll() {
        try (Session session = factory.openSession()) {
            return session.createQuery("SELECT a FROM Message a", Message.class).getResultList();
        } catch (Exception e) {
            throw new RuntimeException("Can't get all messages from DB", e);
        }
    }

    @Override
    public void remove(Message entity) {
        EntityManager session = null;
        EntityTransaction transaction = null;
        try {
            session = factory.createEntityManager();
            transaction = session.getTransaction();
            transaction.begin();
            session.remove(entity);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException("An error occurred while removing a message from the DB. "
                    + "Message: " + entity, e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }
}
