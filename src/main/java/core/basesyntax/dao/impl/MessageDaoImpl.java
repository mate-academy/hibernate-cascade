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
        Session session = factory.openSession();
        Transaction transaction = session.beginTransaction();

        try {
            session.persist(message);
            transaction.commit();
            return message;
        } catch (Exception e) {
            transaction.rollback();
            throw new RuntimeException("Can't create new message " + message, e);
        } finally {
            session.close();
        }
    }

    @Override
    public Message get(Long id) {
        try (Session session = factory.openSession()) {
            return session.get(Message.class, id);
        } catch (Exception e) {
            throw new RuntimeException("Can't find message with id " + id, e);
        }
    }

    @Override
    public List<Message> getAll() {
        String sql = "SELECT * FROM message";
        try (Session session = factory.openSession()) {
            return session.createNativeQuery(sql, Message.class).getResultList();
        } catch (Exception e) {
            throw new RuntimeException("Can't get all messages ", e);
        }
    }

    @Override
    public void remove(Message message) {
        Session session = factory.openSession();
        Transaction transaction = session.beginTransaction();
        try {
            session.remove(message);
            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
            throw new RuntimeException("Can't remove message " + message, e);
        } finally {
            session.close();
        }
    }
}
