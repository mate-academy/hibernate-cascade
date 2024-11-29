package core.basesyntax.dao.impl;

import core.basesyntax.DataProcessingException;
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
        if (messageDetails == null) {
            throw new RuntimeException("unacceptable data");
        }

        if (messageDetails.getId() != null && get(messageDetails.getId()) != null) {
            throw new RuntimeException(messageDetails + "already existed in database");
        }

        Session session = null;
        Transaction transaction = null;

        try {
            session = factory.openSession();
            transaction = session.beginTransaction();
            session.persist(messageDetails);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new DataProcessingException("adding " + messageDetails + " into database failed");
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return messageDetails;
    }

    @Override
    public MessageDetails get(Long id) {
        Session session = null;
        MessageDetails messageDetails = null;
        try {
            session = factory.openSession();
            messageDetails = session.get(MessageDetails.class, id);
        } catch (Exception e) {

            throw new DataProcessingException("getting messageDetails with "
                    + id + " from database failed");
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return messageDetails;
    }
}
