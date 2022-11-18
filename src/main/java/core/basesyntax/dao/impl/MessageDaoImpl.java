package core.basesyntax.dao.impl;

import core.basesyntax.dao.MessageDao;
import core.basesyntax.model.Message;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

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
            throw new RuntimeException("Can't add message to db: "
                    + entity.getContent());
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return entity;
    }

    @Override
    public Message get(Long id) {
        Session session = null;
        Message message;
        try {
            session = factory.openSession();
            message = session.get(Message.class, id);
        } catch (Exception e) {
            throw new RuntimeException("Can't get message from db: "
                    + id, e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return message;
    }

    @Override
    public List<Message> getAll() {
        Session session = null;
        List<Message> messages;
        try {
            session = factory.openSession();
            Query<Message> fromMessage = session.createQuery("FROM Message", Message.class);
            messages = fromMessage.getResultList();
        } catch (Exception e) {
            throw new RuntimeException("Can't get messages from db", e);
        } finally {
            if (session != null) {
                session.close();
            }
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
            session.delete(entity);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException("Can't delete message from db: "
                    + entity.getId(), e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }
}
