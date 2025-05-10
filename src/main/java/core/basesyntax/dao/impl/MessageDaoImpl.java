package core.basesyntax.dao.impl;

import core.basesyntax.dao.MessageDao;
import core.basesyntax.model.Message;
import java.util.List;
import java.util.Optional;
import org.hibernate.SessionFactory;

public class MessageDaoImpl extends AbstractDao implements MessageDao {
    public MessageDaoImpl(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    @Override
    public Message create(Message entity) {
        return (Message) super.createEntity(entity);
    }

    @Override
    public Message get(Long id) {
        Optional<Message> message = super.get(id, Message.class);
        return message.orElse(null);
    }

    @Override
    public List<Message> getAll() {
        return super.getAll(Message.class);
    }

    @Override
    public void remove(Message entity) {
        super.removeEntity(entity);
    }
}
