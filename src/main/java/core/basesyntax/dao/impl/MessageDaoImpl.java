package core.basesyntax.dao.impl;

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
        Session session = factory.openSession();
        Transaction tx = session.beginTransaction();

        session.persist(entity);

        tx.commit();
        session.close();
        return entity;
    }

    @Override
    public Message get(Long id) {
        Session session = factory.openSession();
        Message message = session.get(Message.class, id);
        session.close();
        return message;
    }

    @Override
    public List<Message> getAll() {
        Session session = factory.openSession();
        List<Message> messages = session
                .createQuery("FROM Message", Message.class)
                .getResultList();
        session.close();
        return messages;
    }

    @Override
    public void remove(Message message) {
        Session session = factory.openSession();
        Transaction tx = session.beginTransaction();

        Message managed = session.get(Message.class, message.getId());
        session.remove(managed);

        tx.commit();
        session.close();
    }
}
