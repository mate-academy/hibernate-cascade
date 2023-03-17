package core.basesyntax.dao.impl;

import core.basesyntax.dao.MessageDao;
import core.basesyntax.model.Message;
import java.util.List;
import javax.persistence.Query;
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
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException("Can't add messages to DB " + entity, e);
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
        try (Session session = factory.openSession()) {
            message = session.find(Message.class, id);
        } catch (Exception e) {
            throw new RuntimeException("Can't find message by id " + id, e);
        }
        return message;
    }

    @Override
    public List<Message> getAll() {
        List<Message> messageList = null;
        Session session = null;
        try {
            session = factory.openSession();
            Query getAllMessageQuery = session.createQuery("from Message", Message.class);
            messageList = getAllMessageQuery.getResultList();
        } catch (Exception e) {
            throw new RuntimeException("Can't get all message from DB", e);
        }
        return messageList;
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
            throw new RuntimeException("Can't remove message from DB " + entity, e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }
}
