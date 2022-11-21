package core.basesyntax.dao.impl;

import core.basesyntax.dao.MessageDao;
import core.basesyntax.model.Message;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

public class MessageDaoImpl extends GenericDaoImpl<Message> implements MessageDao {
    public MessageDaoImpl(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    @Override
    public Message get(Long id) {
        try (Session session = factory.openSession()) {
            return session.get(Message.class, id);
        } catch (Exception e) {
            throw new RuntimeException("Can't get Message from DB with id " + id, e);
        }
    }

    @Override
    public List<Message> getAll() {
        try (Session session = factory.openSession()) {
            Query<Message> getAllMessages =
                    session.createQuery("SELECT m FROM Message m", Message.class);
            return getAllMessages.getResultList();
        } catch (Exception e) {
            throw new RuntimeException("Can't get all messages from DB");
        }
    }
}
