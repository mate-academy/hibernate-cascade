package core.basesyntax.dao.impl;

import core.basesyntax.dao.MessageDao;
import java.util.List;

import core.basesyntax.model.Message;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

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
            throw new RuntimeException("Can't create message: " + entity, e);
        } finally {
            session.close();
        }
        return entity;
    }

    @Override
    public Message get(Long id) {
        Session session = null;
        Message message;
        try {
            session = factory.openSession();
            message = session.get(Message.class, id);
        } catch (Exception e) {
            throw new RuntimeException("Can't get message with id: " + id, e);
        } finally {
            session.close();
        }
        return message;
    }

    @Override
    public List<Message> getAll() {
        Session session = null;
        List<Message> messages;
        try {
            session = factory.openSession();
            CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
            CriteriaQuery<Message> criteriaQuery = criteriaBuilder.createQuery(Message.class);
            Root<Message> messageRoot = criteriaQuery.from(Message.class);
            CriteriaQuery<Message> all = criteriaQuery.select(messageRoot);
            TypedQuery<Message> allQuery = session.createQuery(all);
            messages = allQuery.getResultList();
        } catch (Exception e) {
            throw new RuntimeException("Can't get all messages", e);
        } finally {
            session.close();
        }
        return messages;
    }

    @Override
    public void remove(Message entity) {
        Session session = null;
        try {
            session = factory.openSession();
            session.delete(entity);
        }  catch (Exception e) {
            throw new RuntimeException("Can't remove comment: " + entity, e);
        } finally {
            session.close();
        }
    }
}
