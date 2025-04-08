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
            session.save(entity);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException("Cannot create Message: " + entity.toString(), e);
        } finally {
            if (session != null) {
                session.close();
            }
        }

        return entity;
    }

    @Override
    public Message get(Long id) {
        Session session = null;
        Message entity = null;

        try {
            session = factory.openSession();
            entity = (Message) session.get(Message.class, id);
        } catch (Exception e) {
            throw new RuntimeException("Cannot get Message: " + id, e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return entity;
    }

    @Override
    public List<Message> getAll() {
        List<Message> entityList = null;
        Session session = null;

        try {
            session = factory.openSession();
            entityList = session.createQuery("FROM Message", Message.class).getResultList();
        } catch (Exception e) {
            throw new RuntimeException("Cannot get Message: " + e, e);
        } finally {
            if (session != null) {
                session.close();
            }
        }

        return entityList;
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
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException("Cannot remove Message: " + entity.toString(), e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }
}
