package core.basesyntax.dao.impl;

import core.basesyntax.dao.MessageDao;
import core.basesyntax.model.Message;
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
            return entity;
        } catch (Exception ex) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException("Failed to create the Message. " + ex.getMessage());
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    @Override
    public Message get(Long id) {
        Session session = null;
        try {
            session = factory.openSession();
            return session.get(Message.class, id);
        } catch (Exception ex) {
            throw new RuntimeException("Failed to get a Message. " + ex.getMessage());
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    @Override
    public List<Message> getAll() {
        Session session = null;
        List<Message> results;
        try {
            session = factory.openSession();
            results = session.createQuery("SELECT a FROM Message a", Message.class).getResultList();
        } catch (Exception ex) {
            throw new RuntimeException("Failed to get all comments. " + ex.getMessage());
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return results;
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
        } catch (Exception ex) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException("Failed to create the comment. " + ex.getMessage());
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }
}
