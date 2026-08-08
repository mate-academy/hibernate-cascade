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
        Transaction transaction = null;
        Session session = null;
        try {
            session = factory.openSession();
            transaction = session.beginTransaction();
            session.persist(entity);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException("Could not persist entity " + entity, e);
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
            throw new RuntimeException("Could not get entity " + id, e);
        }
    }

    @Override
    public List<Message> getAll() {
        try (Session session = factory.openSession()) {
            return session.createQuery("from Message", Message.class)
                    .setMaxResults(100)
                    .getResultList();
        } catch (Exception e) {
            throw new RuntimeException("Could not get all entities", e);
        }
    }

    @Override
    public void remove(Message entity) {
        Transaction transaction = null;
        Session session = null;
        try {
            session = factory.openSession();
            transaction = session.beginTransaction();
            Message message = session.get(Message.class, entity.getId());
            if (message != null) {
                session.remove(message);
            }
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException("Could not remove entity " + entity, e);
        } finally {
            if (session != null) {
                session.close();
            }
        }

    }
}
