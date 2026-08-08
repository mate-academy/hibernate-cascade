package core.basesyntax.dao.impl;

import core.basesyntax.dao.MessageDetailsDao;
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
    public MessageDetails create(MessageDetails messageDetails) {
        Session session = null;
        Transaction transaction = null;
        try {
            session = factory.openSession();
            transaction = session.beginTransaction();
            session.persist(messageDetails);
            transaction.commit();
            return messageDetails;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException("Can't insert details", e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    @Override
    public MessageDetails get(Long id) {
        try (Session session = factory.openSession()) {
            return session.get(MessageDetails.class, id);
        } catch (Exception e) {
            throw new RuntimeException("Can't get details by id: " + id, e);
        }
    }

    @Override
    public List<MessageDetails> getAll() {
        try (Session session = factory.openSession()) {
            return session.createQuery("from MessageDetails", MessageDetails.class).getResultList();
        } catch (Exception e) {
            throw new RuntimeException("Can't get all details", e);
        }
    }

    @Override
    public void remove(MessageDetails messageDetails) {
        Session session = null;
        Transaction transaction = null;
        try {
            session = factory.openSession();
            transaction = session.beginTransaction();
            session.remove(messageDetails);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException("Can't remove details", e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }
}
