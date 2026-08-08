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
        Transaction transaction = null;
        Session session = null;
        try {
            session = factory.openSession();
            transaction = session.getTransaction();
            transaction.begin();
            session.persist(entity);
            transaction.commit();
            return entity;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException("Can not add a entity to DB.", e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    @Override
    public MessageDetails get(Long id) {
        Transaction transaction = null;
        try (Session session = factory.openSession()) {
            transaction = session.getTransaction();
            transaction.begin();
            MessageDetails foundedEntity = session.find(MessageDetails.class, id);
            transaction.commit();
            return foundedEntity;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException("Can not get a " + MessageDetails.class.getSimpleName()
                    + " to a DB. ", e);
        }
    }
}
