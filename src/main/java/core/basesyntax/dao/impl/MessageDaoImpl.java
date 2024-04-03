package core.basesyntax.dao.impl;

import core.basesyntax.dao.MessageDao;
import core.basesyntax.model.Message;

import java.util.ArrayList;
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
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException("Cannot create message " + entity);
        } finally {
            if (transaction != null) {
                session.close();
            }
        }
        return entity;
    }

    @Override
    public Message get(Long id) {
        try (Session session = factory.openSession()) {
            try {
                return session.get(Message.class, id);
            } catch (Exception e) {
                throw new RuntimeException("cannot getting message by id " + id, e);
            }
        } catch (Exception e) {
            throw new RuntimeException("cannot closing session", e);
        }
    }


    @Override
    public List<Message> getAll() {
        try (Session session = factory.openSession()) {
            String hql = "FROM Message";
            Query<Message> query = session.createQuery(hql, Message.class);
            List<Message> resultList = query.getResultList();
            return new ArrayList<>(resultList);
        } catch (Exception e) {
            throw new RuntimeException("Error getting all Message", e);
        }
    }

    @Override
    public void remove(Message entity) {
        Session session = null;
        Transaction transaction = null;
        try {
            session = factory.openSession();
            transaction = session.beginTransaction();
            Message message = session.get(Message.class, entity.getId());
            if (message != null) {
                session.delete(message);
            } else {
                throw new RuntimeException("Entity did not found with id: " + entity.getId());
            }
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException("Failed to delete entity " + entity, e);
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
    }
}
