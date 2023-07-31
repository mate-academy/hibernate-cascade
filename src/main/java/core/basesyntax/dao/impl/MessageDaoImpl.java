package core.basesyntax.dao.impl;

import core.basesyntax.dao.MessageDao;
import core.basesyntax.model.Message;
import java.util.List;
import javax.persistence.Query;
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

        } catch (RuntimeException e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException("Can't add " + entity, e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return entity;
    }

    @Override
    public Message get(Long id) {
        try (Session session = factory.openSession()) {
            Message entity = session.get(Message.class, id);
            return entity;
        } catch (RuntimeException e) {
            throw new RuntimeException("Can't get " + Message.class + " by id " + id, e);
        }
    }

    @Override
    public List<Message> getAll() {
        String query = "FROM Message";
        try (Session session = factory.openSession()) {
            Query getAllQuery = session.createQuery(query, Message.class);
            return getAllQuery.getResultList();
        } catch (RuntimeException e) {
            throw new RuntimeException("Can't get all messages.", e);
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

        } catch (RuntimeException e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException("Can't revove " + entity, e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }
}
