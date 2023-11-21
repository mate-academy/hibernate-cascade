package core.basesyntax.dao.impl;

import core.basesyntax.dao.MessageDao;
import core.basesyntax.model.Message;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

public class MessageDaoImpl extends AbstractDao implements MessageDao {
    private static final String CANNOT_CREATE_MESSAGE_TEMPLATE = "Cannot create message: ";
    private static final String CANNOT_GET_MESSAGE_TEMPLATE = "Cannot get message with id: ";
    private static final String CANNOT_GET_MESSAGES = "Cannot get messages";
    private static final String CANNOT_REMOVE_MESSAGE_TEMPLATE = "Cannot remove message: ";
    private static final String GET_ALL_MESSAGES_QUERY = "FROM Message";

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
            throw new RuntimeException(CANNOT_CREATE_MESSAGE_TEMPLATE + entity, e);
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
            throw new RuntimeException(CANNOT_GET_MESSAGE_TEMPLATE + id, e);
        }
    }

    @Override
    public List<Message> getAll() {
        try (Session session = factory.openSession()) {
            return session.createQuery(GET_ALL_MESSAGES_QUERY, Message.class).getResultList();
        } catch (Exception e) {
            throw new RuntimeException(CANNOT_GET_MESSAGES, e);
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
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException(CANNOT_REMOVE_MESSAGE_TEMPLATE + entity, e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }
}
