package core.basesyntax.dao.impl;

import core.basesyntax.dao.MessageDao;
import core.basesyntax.exception.DataProcessingException;
import core.basesyntax.model.Message;
import java.util.List;
import java.util.function.Consumer;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

public class MessageDaoImpl extends AbstractDao implements MessageDao {
    public MessageDaoImpl(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    @Override
    public Message create(Message entity) {
        executeInTransaction(session -> session.persist(entity));
        return entity;
    }

    @Override
    public Message get(Long id) {
        try (Session session = factory.openSession()) {
            return session.get(Message.class, id);
        } catch (DataProcessingException e) {
            throw new DataProcessingException("Message not found with id: " + id);
        }
    }

    @Override
    public List<Message> getAll() {
        List<Message> messages;
        try (Session session = factory.openSession()) {
            messages = session.createQuery("from Message").list();
            return messages;
        } catch (DataProcessingException e) {
            throw new DataProcessingException("Messages was not found. " + e.getMessage());
        }
    }

    @Override
    public void remove(Message entity) {
        executeInTransaction(session -> session.remove(entity));
    }

    private void executeInTransaction(Consumer<Session> action) {
        Session session = null;
        Transaction transaction = null;
        try {
            session = factory.openSession();
            transaction = session.beginTransaction();
            action.accept(session);
            transaction.commit();
        } catch (RuntimeException e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new DataProcessingException("Operation with message not done. " + e.getMessage());
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }
}
