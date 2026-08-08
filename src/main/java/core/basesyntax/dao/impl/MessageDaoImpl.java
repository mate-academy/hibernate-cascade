package core.basesyntax.dao.impl;

import core.basesyntax.dao.MessageDao;
import core.basesyntax.model.Message;
import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

public class MessageDaoImpl extends AbstractDao implements MessageDao {
    public MessageDaoImpl(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    @Override
    public Message create(Message entity) {
        if (entity == null) {
            throw new RuntimeException("Message can't be null");
        }
        Session session = null;
        Transaction transaction = null;
        try {
            session = factory.openSession();
            transaction = session.beginTransaction();
            session.persist(entity);
            transaction.commit();
        } catch (HibernateException e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException("Can't create Message " + entity, e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return entity;
    }

    @Override
    public Message get(Long id) {
        if (id == null) {
            throw new RuntimeException("Id can't be null");
        }
        try (Session session = factory.openSession()) {
            return session.createQuery(
                            "SELECT m FROM Message m LEFT JOIN FETCH m.messageDetails "
                                    + "WHERE m.id = :id",
                            Message.class)
                    .setParameter("id", id)
                    .uniqueResult();
        } catch (HibernateException e) {
            throw new RuntimeException("Can't get Message by id " + id, e);
        }
    }

    @Override
    public List<Message> getAll() {
        try (Session session = factory.openSession()) {
            return session.createQuery(
                    "SELECT DISTINCT m FROM Message m LEFT JOIN FETCH m.messageDetails",
                    Message.class).list();
        } catch (HibernateException e) {
            throw new RuntimeException("Can't get Messages", e);
        }
    }

    @Override
    public void remove(Message entity) {
        if (entity == null) {
            throw new RuntimeException("Message can't be null");
        }
        Session session = null;
        Transaction transaction = null;
        try {
            session = factory.openSession();
            transaction = session.beginTransaction();
            Message messageManaged = session.get(Message.class, entity.getId());
            if (messageManaged == null) {
                throw new RuntimeException("Message not found");
            }
            session.remove(messageManaged);
            transaction.commit();
        } catch (HibernateException e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException("Can't delete Message " + entity, e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }
}
