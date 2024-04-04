package core.basesyntax.dao.impl;

import core.basesyntax.dao.MessageDao;
import core.basesyntax.model.Message;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

public class MessageDaoImpl extends AbstractDao implements MessageDao {
    private Session session = null;
    private Transaction transaction = null;

    public MessageDaoImpl(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    @Override
    public Message create(Message entity) {
        try {
            session = factory.openSession();
            transaction = session.beginTransaction();
            session.persist(entity);
            transaction.commit();
            session.close();
            return entity;
        } catch (Exception e) {
            transaction.rollback();
            throw new RuntimeException("Error creating entity to the database", e);
        } finally {
            session.close();
        }
    }

    @Override
    public Message get(Long id) {
        try (Session session = factory.openSession()) {
            return session.get(Message.class, id);
        } catch (Exception e) {
            throw new RuntimeException("can't get an Entity from the DB", e);
        }
    }

    @Override
    public List<Message> getAll() {
        List<Message> messages = null;
        try {
            session = factory.openSession();
            transaction = session.beginTransaction();
            messages = session.createQuery("FROM Message", Message.class).getResultList();
            transaction.commit();
            session.close();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException("Error retrieving all entities from the database", e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return messages;
    }

    @Override
    public void remove(Message entity) {
        try {
            session = factory.openSession();
            transaction = session.beginTransaction();
            session.remove(entity);
            transaction.commit();
            session.close();
        } catch (Exception e) {
            transaction.rollback();
            throw new RuntimeException("Error removing entity to the database", e);
        } finally {
            session.close();
        }
    }
}
