package core.basesyntax.dao.impl;

import core.basesyntax.dao.MessageDao;
import core.basesyntax.model.Message;
import java.util.List;
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
        return executeInsideTransaction(session -> session.get(Message.class, id));
    }

    @Override
    public List<Message> getAll() {
        return executeInsideTransaction(session -> session.createQuery(
                "FROM Message", Message.class).list());
    }

    @Override
    public void remove(Message entity) {
        executeInsideTransaction(session -> {
            Message mergedMessage = session.merge(entity);
            session.remove(mergedMessage);
            return null;
        });
    }
}