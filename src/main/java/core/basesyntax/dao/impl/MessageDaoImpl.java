package core.basesyntax.dao.impl;

import core.basesyntax.dao.MessageDao;
import core.basesyntax.model.Message;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

public class MessageDaoImpl extends AbstractDao implements MessageDao {
    public MessageDaoImpl(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    @Override
    public Message create(Message message) {
        Session session = null;
        Transaction transaction = null;
        try {
            session = factory.openSession();
            transaction = session.beginTransaction();
            session.persist(message);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException("Can't create message " + message + " to DB", e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return message;
    }

    @Override
    public Message get(Long id) {
        try (Session session = factory.openSession()) {
            Message message = session.get(Message.class,id);
            return message;
        } catch (Exception e) {
            throw new RuntimeException("Can't get message by id" + id, e);
        }
    }

    @Override
    public List<Message> getAll() {
        try (Session session = factory.openSession()) {
            Query<Message> getAllMessageQuery = session.createQuery(
                    "from Message", Message.class);
            return getAllMessageQuery.getResultList();
        } catch (Exception e) {
            throw new RuntimeException("Can't get all messages from DB",e);
        }
    }

    @Override
    public void remove(Message message) {
        Session session = null;
        Transaction transaction = null;
        try {
            session = factory.openSession();
            transaction = session.beginTransaction();
            session.remove(message);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException("Can't create message " + message + " to DB", e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }
}
