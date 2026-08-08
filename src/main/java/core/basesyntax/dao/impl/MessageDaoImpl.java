package core.basesyntax.dao.impl;

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
        return sessionContainer(session -> {
            session.persist(entity);
            return entity;
        });
    }
   
    @Override
    public Message get(Long id) {
        try (Session session = factory.openSession()) {
            return session.get(Message.class, id);
        } catch (Exception e) {
            throw new RuntimeException(
                    "Can't get a message by id: %d from DB".formatted(id), e);
        }
    }
    
    @Override
    public List<Message> getAll() {
        try (Session session = factory.openSession()) {
            return session.createQuery("FROM Message", Message.class).getResultList();
        } catch (Exception e) {
            throw new RuntimeException(
                    "Can't get all the messages from DB", e);
        }
    }
    
    @Override
    public void remove(Message entity) {
        sessionContainer(session -> {
            session.remove(entity);
            return entity;
        });
    }
}
