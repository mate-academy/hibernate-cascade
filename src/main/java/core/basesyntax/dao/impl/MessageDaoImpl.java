package core.basesyntax.dao.impl;

import core.basesyntax.dao.MessageDao;
import core.basesyntax.model.Message;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

public class MessageDaoImpl extends AbstractDao implements MessageDao {
    private static final String GET_ALL_MESSAGES_SQL = "FROM Message";
    private Session session;
    private Transaction transaction;

    public MessageDaoImpl(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    @Override
    public Message create(Message entity) {
        checkMessage(entity);
        try {
            session = factory.openSession();
            transaction = session.beginTransaction();
            session.persist(entity);
            transaction.commit();
            return entity;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException("Could not add message", e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    @Override
    public Message get(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("ID cannot be null");
        }
        if (id <= 0L) {
            throw new IllegalArgumentException("ID cannot be negative or zero");
        }
        try {
            session = factory.openSession();
            transaction = session.beginTransaction();
            return session.get(Message.class, id);
        } catch (Exception e) {
            throw new RuntimeException("Could not get message", e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    @Override
    public List<Message> getAll() {
        try {
            session = factory.openSession();
            transaction = session.beginTransaction();
            return session.createQuery(GET_ALL_MESSAGES_SQL, Message.class).getResultList();
        } catch (Exception e) {
            throw new RuntimeException("Could not get messages", e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    @Override
    public void remove(Message entity) {
        checkMessage(entity);
        try {
            session = factory.openSession();
            transaction = session.beginTransaction();
            session.remove(entity);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException("Could not remove message", e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    private void checkMessage(Message entity) {
        if (entity == null) {
            throw new IllegalArgumentException("Message cannot be null");
        }
        if (entity.getContent() == null || entity.getContent().isEmpty()) {
            throw new IllegalArgumentException("Message content cannot be null or empty");
        }
    }
}
