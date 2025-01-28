package core.basesyntax.dao.impl;

import core.basesyntax.dao.MessageDao;
import core.basesyntax.exception.DataProcessingException;
import core.basesyntax.model.Message;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

public class MessageDaoImpl extends AbstractDao implements MessageDao {
    public MessageDaoImpl(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    @Override
    public Message create(Message message) {
        return executeTransaction(session -> {
            session.persist(message);
            return message;
        });
    }

    @Override
    public Message get(Long id) {
        try (Session session = factory.openSession()) {
            return session.get(Message.class, id);
        } catch (Exception e) {
            throw new DataProcessingException("Couldn't retrieve message with id " + id, e);
        }
    }

    @Override
    public List<Message> getAll() {
        try (Session session = factory.openSession()) {
            return session.createQuery("FROM Message", Message.class).getResultList();
        } catch (Exception e) {
            throw new DataProcessingException("Couldn't retrieve all messages", e);
        }
    }

    @Override
    public void remove(Message message) {
        executeTransaction(session -> {
            session.remove(session.contains(message) ? message : session.merge(message));
            return null;
        });
    }
}
