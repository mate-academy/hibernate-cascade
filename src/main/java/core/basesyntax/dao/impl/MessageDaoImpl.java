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
            throw new RuntimeException("Can't save a Message entity with ID:" + entity.getId()
                    + "\t Exception: ", e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return entity;
    }

    @Override
    public Message get(Long id) {
        Session session = factory.openSession();
        Message entity = session.get(Message.class, id);
        session.close();
        return entity;
    }

    @Override
    public List<Message> getAll() {
        Session session = factory.openSession();
        List<Message> entityList =
                session.createQuery("SELECT a FROM Message a", Message.class).getResultList();
        session.close();
        return entityList;
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
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException("Can't remove a Message entity with ID:" + entity.getId()
                    + "\t Exception: ", e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }
}
