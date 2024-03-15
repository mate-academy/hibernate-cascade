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
    public MessageDetails create(MessageDetails entity) {
        if (entity == null) {
            throw new RuntimeException("Link to the message details  is null");
        }
        Session session = null;
        Transaction transaction = null;
        try {
            session = factory.openSession();
            transaction = session.beginTransaction();
            session.persist(entity);
            transaction.commit();
        } catch (RuntimeException ex) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException("Can't create message details in db: " + entity, ex);
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return entity;
    }

    @Override
    public MessageDetails get(Long id) {
        if (id == null) {
            throw new RuntimeException("This 'id' is null");
        }
        MessageDetails messageDetails;
        try (Session session = factory.openSession()) {
            messageDetails = session.get(MessageDetails.class, id);
        } catch (RuntimeException ex) {
            throw new RuntimeException("Can't get entity of message details from db by id = " + id);
        }
        return messageDetails;
    }
}
