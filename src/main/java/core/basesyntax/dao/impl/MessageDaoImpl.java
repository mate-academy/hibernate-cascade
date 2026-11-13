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
        Session session = null;
        Transaction transaction = null;
        try {
            session = factory.openSession();
            transaction = session.beginTransaction();
            session.persist(entity);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException("Can't create new message", e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return entity;
    }

    @Override
    public Message get(Long id) {
        try (Session session = factory.openSession();) {
            return session.get(Message.class, id);
        } catch (Exception e) {
            throw new RuntimeException("Can't retrieve message with id: " + id, e);
        }
    }

    @Override
    public List<Message> getAll() {
        String sql = "SELECT m FROM Message m";
        try (Session session = factory.openSession();) {
            return session.createQuery(sql, Message.class).getResultList();
        } catch (Exception e) {
            throw new RuntimeException("Can't retrieve all messages", e);
        }
    }

    @Override
    public void remove(Message entity) {
        Session session = null;
        Transaction transaction = null;
        try {
            session = factory.openSession();
            transaction = session.beginTransaction();
            session.remove(entity);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException("Can't remove message with id: " + entity.getId(), e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }
}
