package core.basesyntax.dao.impl;

import core.basesyntax.dao.MessageDao;
import core.basesyntax.model.Message;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

public class MessageDaoImpl extends AbstractDao implements MessageDao {
    private Session session;
    private Transaction transaction;

    public MessageDaoImpl(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    @Override
    public Message create(Message message) {
        checkMessage(message);
        try {
            session = factory.openSession();
            transaction = session.beginTransaction();
            session.persist(message);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException("Can`t add Message to database!" + message, e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return message;
    }

    @Override
    public Message get(Long id) {
        try (Session session = factory.openSession()) {
            return session.get(Message.class,id);
        } catch (Exception e) {
            throw new RuntimeException("Failed to retrieve "
                    + "Message from the database with id " + id, e);
        }
    }

    @Override
    public List<Message> getAll() {
        try (Session session = factory.openSession()) {
            return session.createQuery("FROM Message", Message.class).getResultList();
        } catch (Exception e) {
            throw new RuntimeException("Failed to retrieve messages "
                    + "from the database", e);
        }
    }

    @Override
    public void remove(Message message) {
        checkMessage(message);
        try {
            session = factory.openSession();
            transaction = session.beginTransaction();
            session.remove(message);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException("Cant remove message!" + message, e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    private void checkMessage(Message message) {
        if (message == null) {
            throw new RuntimeException("Message cannot be null! ");
        }
        if (message.getContent() == null || message.getContent().isEmpty()) {
            throw new RuntimeException("Message cannot be empty or null! " + message);
        }
    }
}
