package core.basesyntax.dao.impl;

import core.basesyntax.dao.MessageDao;
import core.basesyntax.model.Message;
import java.util.List;
import java.util.Objects;
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
        } catch (Exception e) {
            Objects.requireNonNull(transaction).rollback();
            throw new RuntimeException("Can't add message to db", e);
        } finally {
            Objects.requireNonNull(session).close();
        }
        return entity;
    }

    @Override
    public Message get(Long id) {

        try (Session session = factory.openSession()) {
            return session.get(Message.class, id);
        } catch (Exception e) {
            throw new RuntimeException("Can't get message from db by id: " + id, e);
        }
    }

    @Override
    public List<Message> getAll() {

        Session session = null;
        try {
            session = factory.openSession();
            return session.createQuery("SELECT a FROM Message a", Message.class).getResultList();
        } catch (Exception e) {
            throw new RuntimeException("Can't get all messages from db", e);
        } finally {
            Objects.requireNonNull(session).close();
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
            Objects.requireNonNull(transaction).rollback();
            throw new RuntimeException("Can't delete message from db", e);
        } finally {
            Objects.requireNonNull(session).close();
        }

    }
}
