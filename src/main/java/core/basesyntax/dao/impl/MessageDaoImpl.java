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
    public Message create(Message entity) {
        Session session = null;
        Transaction transaction = null;
        try {
            session = super.factory.openSession();
            transaction = session.beginTransaction();
            session.persist(entity);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException("Can't save a message " + entity + " to DB", e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return entity;
    }

    @Override
    public Message get(Long id) {
        try (Session session = super.factory.openSession()) {
            Message message = session.get(Message.class, id);
            return message;
        } catch (Exception e) {
            throw new RuntimeException("Can't get user by id " + id + " from DB", e);
        }
    }

    @Override
    public List<Message> getAll() {
        try (Session session = super.factory.openSession()) {
            Query<Message> getAllQuery = session.createQuery("from Message", Message.class);
            return getAllQuery.getResultList();
        } catch (Exception e) {
            throw new RuntimeException("Something went wrong...", e);
        }
    }

    @Override
    public void remove(Message entity) {
        Session session = null;
        Transaction transaction = null;
        try {
            session = super.factory.openSession();
            transaction = session.beginTransaction();
            session.remove(entity);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException("Can't remove message " + entity + " to DB", e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }
}
