package core.basesyntax.dao.impl;

import core.basesyntax.dao.MessageDetailsDao;
import core.basesyntax.exception.DataProcessingException;
import core.basesyntax.model.MessageDetails;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

public class MessageDetailsDaoImpl extends AbstractDao implements MessageDetailsDao {
    public MessageDetailsDaoImpl(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    @Override
    public MessageDetails create(MessageDetails entity) {
        Session session = null;
        Transaction transaction = null;
        try {
            session = factory.openSession();
            transaction = session.beginTransaction();
            session.save(entity);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new DataProcessingException("Couldn't add messageDetails: "
                    + entity + " to DB. ", e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return entity;
    }

    @Override
    public MessageDetails get(Long id) {
        try (Session session = factory.openSession()) {
            return session.get(MessageDetails.class, id);
        } catch (Exception e) {
            throw new DataProcessingException("Couldn't get messageDetails by id: "
                    + id + " from DB. ", e);
        }
    }

    @Override
    public List<MessageDetails> getAll() {
        try (Session session = factory.openSession()) {
            return session.createQuery("from MessageDetails",
                    MessageDetails.class).getResultList();
        } catch (Exception e) {
            throw new DataProcessingException("Couldn't get all message details objects from DB. ",
                    e);
        }
    }

    @Override
    public void remove(MessageDetails entity) {
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
            throw new DataProcessingException("Couldn't delete message details object: "
                    + entity + " from DB. ", e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }
}
