package core.basesyntax.dao.impl;

import core.basesyntax.dao.MessageDao;
import core.basesyntax.model.Message;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

public class MessageDaoImpl extends AbstractDao<Message> implements MessageDao {
    public MessageDaoImpl(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    @Override
    public Message get(Long id) {
        try (Session session = factory.openSession()) {
            Message message = session.get(Message.class, id);
            return message;
        } catch (Exception e) {
            throw new RuntimeException("Failed to get the message with ID "
                    + id + " from the DB.", e);
        }
    }

    @Override
    public List<Message> getAll() {
        try (Session session = factory.openSession()) {
            Query<Message> getAllMessagesQuery = session.createQuery("from Message", Message.class);
            return getAllMessagesQuery.getResultList();
        } catch (Exception e) {
            throw new RuntimeException("Failed to get all messages from the DB.", e);
        }
    }
}
