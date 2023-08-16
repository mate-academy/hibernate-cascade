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
        Session currentSession = null;
        Transaction transaction = null;
        try {
            currentSession = super.factory.openSession();
            transaction = currentSession.beginTransaction();
            currentSession.save(entity);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException("Can't create message: " + entity,e);
        } finally {
            currentSession.close();
        }
        return entity;
    }

    @Override
    public Message get(Long id) {
        try (Session currentSession = super.factory.openSession();) {
            Message message = currentSession.get(Message.class, id);
            return message;
        } catch (Exception e) {
            throw new RuntimeException("Can't get by id: " + id, e);
        }
    }

    @Override
    public List<Message> getAll() {
        try (Session currentSession = super.factory.openSession();) {
            Query<Message> commentsQuery = currentSession.createQuery(
                    "from Message", Message.class);
            return commentsQuery.getResultList();
        } catch (Exception e) {
            throw new RuntimeException("Can't get messages", e);
        }
    }

    @Override
    public void remove(Message entity) {
        Session currentSession = null;
        Transaction transaction = null;
        try {
            currentSession = super.factory.openSession();
            transaction = currentSession.beginTransaction();
            currentSession.remove(entity);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException("Can't delete message: " + entity,e);
        } finally {
            currentSession.close();
        }
    }
}
