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

        try (Session session = factory.openSession()) {
            transaction = session.beginTransaction();

            session.persist(entity);
            transaction.commit();
            return entity;
        } catch (Exception e) {
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
            throw new RuntimeException("Cannot create message", e);
        }
    }

    @Override
    public Message get(Long id) {
        Transaction transaction = null;

        try (Session session = factory.openSession()) {
            transaction = session.beginTransaction();
            Message entity = session.get(Message.class, id);
            transaction.commit();
            return entity;
        } catch (Exception e) {
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
            throw new RuntimeException("Cannot get message", e);
        }
    }

    @Override
    public List<Message> getAll() {
        Transaction transaction = null;

        try (Session session = factory.openSession()) {
            transaction = session.beginTransaction();
            List<Message> list = session
                    .createQuery("FROM Message", Message.class)
                    .getResultList();
            transaction.commit();
            return list;
        } catch (Exception e) {
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
            throw new RuntimeException("Cannot get messages", e);
        }
    }

    @Override
    public void remove(Message entity) {
        Transaction transaction = null;

        try (Session session = factory.openSession()) {
            transaction = session.beginTransaction();
            session.remove(entity);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
            throw new RuntimeException("Cannot remove message", e);
        }
    }
}
