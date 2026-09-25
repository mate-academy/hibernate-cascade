package core.basesyntax.dao.impl;

import core.basesyntax.dao.MessageDao;
import core.basesyntax.exception.DataProcessingException;
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
            if (entity.getId() == null) {
                session.persist(entity);
            } else {
                session.merge(entity);
            }
            transaction.commit();
            return entity;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new DataProcessingException("Could not remove "
                    + entity.getClass().getSimpleName()
                    + ", "
                    + entity, e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    @Override
    public Message get(Long id) {
        Session session = null;
        try {
            session = factory.openSession();
            return session.get(Message.class, id);
        } catch (DataProcessingException e) {
            throw new DataProcessingException("Can't get "
                    + Message.class.getSimpleName()
                    + " by id: " + id);
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    @Override
    public List<Message> getAll() {
        Session session = null;
        try {
            session = factory.openSession();
            return session.createQuery("from Message", Message.class).list();
        } catch (Exception e) {
            throw new DataProcessingException("Can't get any "
                    + Message.class.getSimpleName());
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    @Override
    public void remove(Message entity) {
        Session session = null;
        Transaction transaction = null;
        try {
            session = factory.openSession();
            transaction = session.beginTransaction();
            Message managedMessage = session.createQuery(
                            "select m from Message m left join fetch m.messageDetails "
                                    + "where m.id = :id",
                            Message.class)
                    .setParameter("id", entity.getId())
                    .uniqueResult();
            if (managedMessage != null) {
                session.remove(managedMessage);
            }
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new DataProcessingException("Could not remove "
                    + entity.getClass().getSimpleName()
                    + ", "
                    + entity, e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }
}
