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
        Session session = null;
        Transaction transaction = null;
        try {
            session = factory.openSession();
            transaction = session.beginTransaction();
            session.persist(messageDetails);
            transaction.commit();
        } catch (RuntimeException exception) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException("Transaction was rollback, ",
                    exception);
        } finally {
            if (transaction != null) {
                session.close();
            }
        }
        return messageDetails;
    }

    @Override
    public MessageDetails get(Long id) {
        MessageDetails messageDetails;
        try (Session session = factory.openSession()) {
            messageDetails = session.get(MessageDetails.class, id);
        } catch (RuntimeException exception) {
            throw new RuntimeException("There is no way to get MessageDetail by id: "
                    + id + ", ", exception);
        }
        return messageDetails;
    }
}
