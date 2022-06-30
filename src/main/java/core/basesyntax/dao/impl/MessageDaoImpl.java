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
            session.save(entity);
            transaction.commit();
            return entity;
        } catch (RuntimeException cause) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException("Can't create Message: " + entity, cause);
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    @Override
    public Message get(Long id) {
        Session session = factory.openSession();
        return session.get(Message.class, id);
    }

    @Override
    public List<Message> getAll() {
        Session session = factory.openSession();
        String hql = "FROM Message";
        Query<Message> getAllMessagesQuery = session.createQuery(hql, Message.class);
        return getAllMessagesQuery.getResultList();
    }

    @Override
    public void remove(Message entity) {
        Session session = null;
        Transaction transaction = null;
        try {
            session = factory.openSession();
            transaction = session.beginTransaction();
            session.delete(entity);
            transaction.commit();
        } catch (RuntimeException cause) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException("Can't delete Message: " + entity, cause);
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }
}
