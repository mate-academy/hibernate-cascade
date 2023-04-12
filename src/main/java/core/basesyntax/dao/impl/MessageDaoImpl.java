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
            session = factory.openSession();
            transaction = session.beginTransaction();
            session.persist(entity);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException("Exception was occurred during create the Message: "
                    + entity.getContent(), e);
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
            return session.get(Message.class, id);
        } catch (RuntimeException e) {
            throw new RuntimeException("Exception was occurred during getting Message by id:"
                    + id, e);
        }
    }

    @Override
    public List<Message> getAll() {
        String hql = "FROM Message";
        try (Session session = factory.openSession()) {
            Query<Message> getAllMessagesQuery = session.createQuery(hql, Message.class);
            return getAllMessagesQuery.getResultList();
        } catch (RuntimeException e) {
            throw new RuntimeException("Exception was occurred during getting all Messages", e);
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
            throw new RuntimeException("Exception was occurred during removing the Message: "
                    + entity.getContent(), e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }
}
