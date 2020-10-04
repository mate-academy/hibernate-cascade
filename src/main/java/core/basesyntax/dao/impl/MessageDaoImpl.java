package core.basesyntax.dao.impl;

import core.basesyntax.dao.MessageDao;
import core.basesyntax.model.Message;
import exceptions.DataProcessingException;
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
        Message message;
        try (Session session = factory.openSession()) {
            message = session.get(Message.class, id);
            return message;
        } catch (Exception e) {
            throw new DataProcessingException("Error retrieving message. ", e);
        }
    }

    @Override
    public List<Message> getAll() {
        try (Session session = factory.openSession()) {
            Query<Message> getAllMessageQuery = session.createQuery("from Message", Message.class);
            return getAllMessageQuery.getResultList();
        } catch (Exception e) {
            throw new DataProcessingException("Error retrieving all messages. ", e);
        }
    }
}
