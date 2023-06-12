package core.basesyntax.dao.impl;

import core.basesyntax.dao.MessageDao;
import core.basesyntax.model.Message;
import java.util.List;
import java.util.Optional;
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
            session = super.factory.openSession();
            transaction = session.beginTransaction();
            session.persist(entity);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException("Can't save message " + entity + " to DB", e);
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
        try (Session session = super.factory.openSession()) {
            message = session.get(Message.class, id);
        } catch (Exception e) {
            throw new RuntimeException("Can't get message with id " + id + " from DB", e);
        }
        return Optional.ofNullable(message).get();
    }

    @Override
    public List<Message> getAll() {
        List<Message> resultList;
        try (Session session = super.factory.openSession()) {
            Query<Message> allMessagesQuery = session.createQuery("from Message", Message.class);
            resultList = allMessagesQuery.getResultList();
        } catch (Exception e) {
            throw new RuntimeException("Can't get all message from DB", e);
        }
        return resultList;
    }

    @Override
    public void remove(Message entity) {
        Session session = null;
        Transaction transaction = null;
        try {
            session = super.factory.openSession();
            transaction = session.beginTransaction();
            session.remove(entity);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException("Can't remove message " + entity + " from DB", e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }
}
