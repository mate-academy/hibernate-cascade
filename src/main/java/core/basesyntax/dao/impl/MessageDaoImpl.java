package core.basesyntax.dao.impl;

import core.basesyntax.dao.MessageDao;
import core.basesyntax.model.Message;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;

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
            throw new RuntimeException("Can` create message " + entity.getContent(), e);
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
        } catch (Exception e) {
            throw new RuntimeException("Can`t get message by id: " + id, e);
        }
    }

    @Override
    public List<Message> getAll() {
        try (Session session = factory.openSession()) {
            Query<Message> messageQuery = session.createQuery("FROM Message ", Message.class);
            return messageQuery.getResultList();
        } catch (Exception e) {
            throw new RuntimeException("Can`t get all message", e);
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
            throw new RuntimeException("Can` remove message: " + entity.getContent(), e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }
}
