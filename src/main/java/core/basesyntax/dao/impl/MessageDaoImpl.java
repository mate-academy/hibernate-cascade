package core.basesyntax.dao.impl;

import core.basesyntax.HibernateUtil;
import core.basesyntax.dao.MessageDao;
import core.basesyntax.model.Message;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

public class MessageDaoImpl extends AbstractDao implements MessageDao {
    public MessageDaoImpl(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    @Override
    public Message create(Message entity) {
        return persistEntity(entity);
    }

    @Override
    public Message get(Long id) {
        return findById(Message.class, id);
    }

    @Override
    public List<Message> getAll() {
        return getEntities(Message.class);
    }

    @Override
    public void remove(Message entity) {

        removeEntity(entity);
    }
}
