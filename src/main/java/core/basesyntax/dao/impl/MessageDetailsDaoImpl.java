package core.basesyntax.dao.impl;

import core.basesyntax.dao.MessageDetailsDao;
import core.basesyntax.model.MessageDetails;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

public class MessageDetailsDaoImpl extends AbstractDao implements MessageDetailsDao {
    public MessageDetailsDaoImpl(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    @Override
    public MessageDetails create(MessageDetails messageDetails) {
        Transaction transaction = null;
        Session session = null;
        try {
            session = this.factory.openSession();
            transaction = session.beginTransaction();
            session.save(messageDetails);
            transaction.commit();
            return messageDetails;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException("Can't insert message details", e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    @Override
    public MessageDetails get(Long id) {
        Transaction transaction = null;
        try (Session session = this.factory.openSession()) {
            transaction = session.beginTransaction();
            MessageDetails messageDetails = session.get(MessageDetails.class, id);
            transaction.commit();
            return messageDetails;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException("Can't get message details with id " + id, e);
        }
    }
}
