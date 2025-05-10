package core.basesyntax.dao.impl;

import core.basesyntax.dao.MessageDao;
import core.basesyntax.model.Message;
import jakarta.persistence.EntityManagerFactory;
import java.util.List;

public class MessageDaoImpl extends AbstractDao implements MessageDao {
    public MessageDaoImpl(EntityManagerFactory entityManagerFactory) {
        super(entityManagerFactory);
    }

    @Override
    public Message create(Message entity) {
        return null;
    }

    @Override
    public Message get(Long id) {
        return null;
    }

    @Override
    public List<Message> getAll() {
        return null;
    }

    @Override
    public void remove(Message entity) {

    }
}
