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
        Session currentSession = null;
        Transaction transaction = null;
        try {
            currentSession = super.factory.openSession();
            transaction = currentSession.beginTransaction();
            currentSession.save(entity);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException("Can't create message details: " + entity,e);
        } finally {
            currentSession.close();
        }
        return entity;
    }

    @Override
    public MessageDetails get(Long id) {
        try (Session currentSession = super.factory.openSession();) {
            MessageDetails messageDetails = currentSession.get(MessageDetails.class, id);
            return messageDetails;
        } catch (Exception e) {
            throw new RuntimeException("Can't get by id: " + id, e);
        }
    }
}
