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
        Session session = null;
        Transaction transaction = null;
        try {
            session = factory.openSession();
            transaction = session.beginTransaction();
            session.persist(entity);
            transaction.commit();
            return entity;
        } catch (HibernateException e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException("Message create method RuntimeException", e);
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
        } catch (RuntimeException e) {
            throw new RuntimeException("Message get method RuntimeException", e);
        }
    }

    @Override
    public List<Message> getAll() {
        try (Session session = factory.openSession()) {
            return session.createQuery("from Message", Message.class).getResultList();
        } catch (RuntimeException e) {
            throw new RuntimeException("Message getAll method RuntimeException", e);
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
        } catch (HibernateException e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException("Message remove method RuntimeException", e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }
}
