package core.basesyntax.dao.impl;

import core.basesyntax.dao.MessageDao;
import core.basesyntax.model.Comment;
import core.basesyntax.model.Message;
import java.util.List;

import org.hibernate.HibernateException;
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
            session.persist(entity);
            transaction.commit();
        } catch (HibernateException e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException("Couldn't create the message in DB. Message: " + entity, e);
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
            message = session.get(Message.class, id);
        } catch (HibernateException e) {
            throw new RuntimeException("Couldn't get the message from DB by id: " + id, e);
        }
        if (message == null) {
            throw new RuntimeException("There is no message in DB by such id: " + id);
        }
        return message;
    }

    @Override
    public List<Message> getAll() {
        try (Session session = factory.openSession()) {
            Query<Message> getAllMessagesQuery =
                    session.createQuery("from Message", Message.class);
            return getAllMessagesQuery.getResultList();
        } catch (HibernateException e) {
            throw new RuntimeException("Couldn't get all messages from DB");
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
        } catch (HibernateException e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException("Couldn't delete the message from DB. Message: " + entity, e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }
}
