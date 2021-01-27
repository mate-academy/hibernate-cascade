package core.basesyntax.dao.impl;

import core.basesyntax.dao.MessageDao;
import core.basesyntax.exception.DataProcessException;
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
        Transaction transaction = null;
        Session session = null;
        try {
            session = factory.openSession();
            transaction = session.beginTransaction();
            session.persist(entity);
            transaction.commit();
            return entity;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new DataProcessException("Could not save message " + entity + " item. ", e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    @Override
    public Message get(Long id) {
        try (Session session = factory.openSession()) {
            return session.get(Message.class, id);
        } catch (Exception e) {
            throw new DataProcessException("Could not get a list of messages. ", e);
        }
    }

    @Override
    public List<Message> getAll() {
        try (Session session = factory.openSession()) {
            Query<Message> getAllCommentsQuery = session.createQuery("from Message", Message.class);
            return getAllCommentsQuery.getResultList();
        } catch (Exception e) {
            throw new DataProcessException("Could not get a list of messages. ", e);
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
            throw new DataProcessException("Could not remove a message " + entity + ". ", e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }
}
