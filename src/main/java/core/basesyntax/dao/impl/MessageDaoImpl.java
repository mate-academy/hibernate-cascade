package core.basesyntax.dao.impl;

import core.basesyntax.dao.MessageDao;
import core.basesyntax.model.Message;
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
            session.persist(entity);
            transaction.commit();
        } catch (Exception e) {
            assert transaction != null;
            transaction.rollback();
            throw new RuntimeException("Cant add Message to DB" + e);
        } finally {
            assert session != null;
            session.close();
        }
        return entity;
    }

    @Override
    public Message get(Long id) {
        Session session = null;
        try {
            session = factory.openSession();
            return session.get(Message.class, id);
        } catch (Exception e) {
            throw new RuntimeException("Cant get Message from DB" + e);
        } finally {
            assert session != null;
            session.close();
        }
    }

    @Override
    public List<Message> getAll() {
        Session session = null;
        try {
            session = factory.openSession();
            Query<Message> allMessagesFromDb = session.createQuery("from Message", Message.class);
            return allMessagesFromDb.getResultList();
        } catch (Exception e) {
            throw new RuntimeException("Cant get all Messages from DB" + e);
        } finally {
            assert session != null;
            session.close();
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
            assert transaction != null;
            transaction.rollback();
            throw new RuntimeException("Cant delete Message from DB" + e);
        } finally {
            assert session != null;
            session.close();
        }
    }
}
