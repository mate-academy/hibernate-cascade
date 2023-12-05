package core.basesyntax.dao.impl;

import core.basesyntax.dao.MessageDao;
import core.basesyntax.model.Message;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

public class MessageDaoImpl extends AbstractDao<Message> implements MessageDao {
    public MessageDaoImpl(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    @Override
    public Message create(Message message) {
        save(message);
        return message;
    }

    @Override
    public Message get(Long id) {
        return findById(Message.class, id);
    }

    @Override
    public List<Message> getAll() {
        try (Session session = factory.openSession()) {
            return session.createQuery("FROM Message", Message.class).list();
        } catch (Exception e) {
            throw new RuntimeException("Error while getting all messages", e);
        }
    }

    @Override
    public void remove(Message message) {
        delete(message);
    }
}
