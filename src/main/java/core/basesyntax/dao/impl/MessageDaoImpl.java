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
            transaction = session.getTransaction();
            transaction.begin();
            session.persist(entity);
            transaction.commit();
            return entity;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException("Can not add a entity to DB.", e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    @Override
    public Message get(Long id) {
        Transaction transaction = null;
        try (Session session = factory.openSession()) {
            transaction = session.getTransaction();
            transaction.begin();
            Message foundedEntity = session.find(Message.class, id);
            transaction.commit();
            return foundedEntity;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException("Can not get a " + Message.class.getSimpleName()
                    + " to a DB. ", e);
        }
    }

    @Override
    public List<Message> getAll() {
        Transaction transaction = null;
        try (Session session = factory.openSession()) {
            transaction = session.getTransaction();
            transaction.begin();
            List<Message> allFoundedEntities =
                    session.createQuery("FROM " + Message.class.getName(), Message.class)
                            .getResultList();

            transaction.commit();
            return allFoundedEntities;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }

            throw new RuntimeException("Can not get a " + Message.class.getSimpleName()
                    + " to a DB. ", e);

        }
    }

    @Override
    public void remove(Message entity) {
        Transaction transaction = null;
        try (Session session = factory.openSession()) {
            transaction = session.getTransaction();
            transaction.begin();
            session.remove(entity);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException("Can not delete " + Message.class.getSimpleName()
                    + " from a DB.", e);
        }
    }
}
