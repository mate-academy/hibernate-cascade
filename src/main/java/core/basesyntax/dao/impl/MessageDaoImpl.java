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
            session.persist(entity);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException("Something is wrong with writing the MESSAGE: "
                    + System.lineSeparator()
                    + entity
                    + System.lineSeparator()
                    + " row to database. Closing connection and rolling back the transaction.", e);
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
            throw new RuntimeException(
                    "Something is wrong with getting data from DB for MESSAGE."
                            + "Maybe such ID as: " + id + " is absent in corresponding DB table",
                    e);
        }
    }

    @Override
    public List<Message> getAll() {
        try (Session session = factory.openSession()) {
            return session.createQuery("select a from messages a", Message.class).getResultList();
        } catch (Exception e) {
            throw new RuntimeException(
                    "Something is wrong with getting ALL the data from DB for MESSAGE."
                            + "Maybe corresponding table is corrupted", e);
        }
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
            throw new RuntimeException("Something is wrong with removing the MESSAGE: "
                    + System.lineSeparator()
                    + entity
                    + System.lineSeparator()
                    + " row from database. Closing connection and rolling back the transaction.",
                    e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }
}
