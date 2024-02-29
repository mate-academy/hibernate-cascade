package core.basesyntax.dao.impl;

import core.basesyntax.dao.MessageDao;
import core.basesyntax.model.Message;
import java.util.List;
import org.hibernate.HibernateException;
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
        Session session = null;
        Transaction transaction = null;
        try {
            session = sessionFactory.openSession();
            transaction = session.beginTransaction();
            if (entity.getMessageDetails() != null) {
                session.persist(entity.getMessageDetails());
            }
            session.persist(entity);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException("Error creating message", e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return entity;
    }

    @Override
    public Message get(Long id) {
        Message message;
        try (Session session = sessionFactory.openSession()) {
            message = session.get(Message.class, id);
        } catch (HibernateException e) {
            throw new RuntimeException("Error getting message from db", e);
        }
        return message;
    }

    @Override
    public List<Message> getAll() {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("from Message", Message.class).list();
        } catch (Exception e) {
            throw new RuntimeException("Error getting all messages", e);
        }
    }

    @Override
    public void remove(Message entity) {
        Session session = null;
        Transaction transaction = null;
        try {
            session = sessionFactory.openSession();
            transaction = session.beginTransaction();
            session.remove(entity);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException("Error removing comment", e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }
}
