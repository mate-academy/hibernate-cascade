package core.basesyntax.dao.impl;

import core.basesyntax.dao.MessageDetailsDao;
import core.basesyntax.model.MessageDetails;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import org.hibernate.SessionFactory;

public class MessageDetailsDaoImpl extends AbstractDao implements MessageDetailsDao {
    public MessageDetailsDaoImpl(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    @Override
    public MessageDetails create(MessageDetails messageDetails) {
        EntityManager entityManager = null;
        EntityTransaction transaction = null;
        try {
            entityManager = factory.openSession();
            transaction = entityManager.getTransaction();
            transaction.begin();
            entityManager.persist(messageDetails);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException("Couldn't create messageDetails " + messageDetails, e);
        } finally {
            if (entityManager != null) {
                entityManager.close();
            }
        }
        return messageDetails;
    }

    @Override
    public MessageDetails get(Long id) {
        EntityManager entityManager = null;
        try {
            entityManager = factory.openSession();
            return entityManager.find(MessageDetails.class, id);
        } catch (Exception e) {
            throw new RuntimeException("Couldn't get messageDetails by ID " + id, e);
        } finally {
            if (entityManager != null) {
                entityManager.close();
            }
        }
    }
}
