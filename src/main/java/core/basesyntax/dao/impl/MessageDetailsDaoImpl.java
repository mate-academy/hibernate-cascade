package core.basesyntax.dao.impl;

import core.basesyntax.dao.MessageDetailsDao;
import core.basesyntax.exception.DataProcessingException;
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
        Session session = null;
        Transaction transaction = null;
        try {
            session = factory.openSession();
            transaction = session.beginTransaction();
            session.persist(entity);
            transaction.commit();
            return entity;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new DataProcessingException("Could not create new "
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
    public MessageDetails get(Long id) {
        Session session = null;
        try {
            session = factory.openSession();
            return session.get(MessageDetails.class, id);
        } catch (DataProcessingException e) {
            throw new DataProcessingException("Can't get "
                    + MessageDetails.class.getSimpleName()
                    + " by id: " + id);
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }
}
