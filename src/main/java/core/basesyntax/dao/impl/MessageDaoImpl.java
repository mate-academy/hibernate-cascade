package core.basesyntax.dao.impl;

import static core.basesyntax.util.HqlQueries.GET_ALL_MESSAGES;

import core.basesyntax.dao.MessageDao;
import core.basesyntax.model.Message;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

public class MessageDaoImpl extends AbstractDao implements MessageDao {
    public MessageDaoImpl(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    @Override
    public Message create(Message entity) {
        return executeInsideTransaction(session -> {
            session.persist(entity);
            return entity;
        });
    }

    @Override
    public Message get(Long id) {
        try (Session session = factory.openSession()) {
            return session.get(Message.class, id);
        } catch (Exception e) {
            throw new RuntimeException("Can't get message by id: " + id, e);
        }
    }

    @Override
    public List<Message> getAll() {
        try (Session session = factory.openSession()) {
            return session.createQuery(GET_ALL_MESSAGES, Message.class).list();
        } catch (Exception e) {
            throw new RuntimeException("Can't get all messages", e);
        }
    }

    @Override
    public void remove(Message entity) {
        executeInsideTransaction(session -> {
            session.remove(entity);
        });
    }
}
