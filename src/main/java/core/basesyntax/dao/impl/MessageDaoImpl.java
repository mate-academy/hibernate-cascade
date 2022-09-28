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
        } catch (Exception exception) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException(
                    "Govna poesh! message " + entity + " cannot create", exception);
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
        } catch (Exception exception) {
            throw new RuntimeException("A her tebe nety tyt " + id, exception);
        }
    }

    @Override
    public List<Message> getAll() {
        String hqlQuery = "FROM Message";
        try (Session session = factory.openSession()) {
            return session.createQuery(hqlQuery, Message.class).getResultList();
        } catch (Exception exception) {
            throw new RuntimeException("Can't get all comments from DB", exception);
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
        } catch (Exception exception) {
            if (transaction != null) {
                transaction.commit();
            }
            throw new RuntimeException("NOOOOOOOOOOOOO " + entity, exception);
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }
}
