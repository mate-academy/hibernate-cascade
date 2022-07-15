package core.basesyntax.dao.impl;

import core.basesyntax.dao.MessageDao;
import core.basesyntax.model.Message;
import java.util.ArrayList;
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
            session.save(entity);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException("Cant create message: " + entity);
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return entity;
    }

    @Override
    public Message get(Long id) {
        Message message = null;
        try (Session session = factory.openSession();) {
            message = session.get(Message.class, id);
        } catch (Exception e) {
            throw new RuntimeException("Cant get message by id: " + id);
        }
        return message;
    }

    @Override
    public List<Message> getAll() {
        List<Message> messages = new ArrayList<>();
        try (Session session = factory.openSession();) {
            messages = session.createQuery("FROM Message").getResultList();
        } catch (Exception e) {
            throw new RuntimeException("Cant get all messages");
        }
        return messages;
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
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException("Cant delete message: " + entity);
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }
}
