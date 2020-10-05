package core.basesyntax.dao.impl;

import core.basesyntax.dao.MessageDetailsDao;
import core.basesyntax.model.MessageDetails;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

public class MessageDetailsDaoImpl extends AbstractDao<MessageDetails>
        implements MessageDetailsDao {
    public MessageDetailsDaoImpl(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    @Override
    public MessageDetails create(MessageDetails details) {
        Transaction transaction = null;
        Session session = null;
        try {
            session = factory.openSession();
            transaction = session.beginTransaction();
            session.save(details);
            transaction.commit();
            return details;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException("Failed to create the message details "
                    + details + " to the DB.", e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    @Override
    public MessageDetails get(Long id) {
        try (Session session = factory.openSession()) {
            Transaction transaction = session.beginTransaction();
            MessageDetails details = session.get(MessageDetails.class, id);
            transaction.commit();
            return details;
        } catch (Exception e) {
            throw new RuntimeException("Failed to get the message details with ID "
                    + id + " from the DB.", e);
        }
    }
}
