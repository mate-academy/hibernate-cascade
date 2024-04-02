package core.basesyntax.dao.impl;

import core.basesyntax.dao.MessageDao;
import core.basesyntax.model.Message;
import java.util.List;
import java.util.Optional;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

public class MessageDaoImpl extends AbstractDao implements MessageDao {
    public MessageDaoImpl(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    @Override
    public Message create(Message message) {
        Transaction transaction = null;
        try (Session session = factory.openSession()) {
            transaction = session.beginTransaction();
            session.persist(message);
            transaction.commit();
        } catch (Exception ex) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException(String.format(
                    "An error occurred while trying to add a message %s to the database",
                    message), ex);
        }
        return message;
    }

    @Override
    public Message get(Long id) {
        try (Session session = factory.openSession()) {
            return Optional.ofNullable(session.get(Message.class, id))
                    .orElseThrow(() -> new RuntimeException(String.format(
                    "An error occurred while trying to retrieve a message "
                            + "with ID %d from the database", id)));
        }
    }

    @Override
    public List<Message> getAll() {
        try (Session session = factory.openSession()) {
            return session.createQuery("SELECT a FROM Message a", Message.class).getResultList();
        } catch (Exception ex) {
            throw new RuntimeException("An error occurred while trying to retrieve "
                    + "all messages from the database", ex);
        }
    }

    @Override
    public void remove(Message message) {
        Transaction transaction = null;
        try (Session session = factory.openSession()) {
            transaction = session.beginTransaction();
            session.remove(message);
            transaction.commit();
        } catch (Exception ex) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException(String.format("An error occurred while trying to remove "
                    + "a message %s from the database", message), ex);
        }
    }
}
