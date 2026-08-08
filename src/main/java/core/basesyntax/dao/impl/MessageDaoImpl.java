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
    public Message create(Message message) {
        try (Session session = factory.openSession()) {
            Transaction transaction = session.beginTransaction();
            session.persist(message);
            transaction.commit();
            return message;
        }
    }

    @Override
    public Message get(Long id) {
        try (Session session = factory.openSession()) {
            return session.get(Message.class, id);
        }
    }

    @Override
    public List<Message> getAll() {
        try (Session session = factory.openSession()) {
            Transaction transaction = session.beginTransaction();
            List<Message> messages = session.createQuery("from Message", Message.class).list();
            transaction.commit();
            return messages;
        }
    }

    @Override
    public void remove(Message entity) {
        try (Session session = factory.openSession()) {
            Transaction transaction = session.beginTransaction();
            Message message = session.get(Message.class, entity.getId());
            if (message != null) {
                session.delete(message);
            }
            transaction.commit();
        }
    }
}
