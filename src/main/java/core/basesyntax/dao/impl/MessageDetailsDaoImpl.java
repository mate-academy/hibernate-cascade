package core.basesyntax.dao.impl;

import core.basesyntax.dao.MessageDetailsDao;
import core.basesyntax.exception.DataProcessingException;
import core.basesyntax.model.MessageDetails;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
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
            throw new DataProcessingException("Failed to save message details: "
                    + messageDetails, e);
        } finally {
            if (entityManager != null) {
                entityManager.close();
            }
        }
        return messageDetails;
    }

    @Override
    public MessageDetails get(Long id) {
        try (EntityManager entityManager = factory.openSession()) {
            return entityManager.find(MessageDetails.class, id);
        } catch (Exception e) {
            throw new DataProcessingException("Failed to get message details with id: " + id, e);
        }
    }
}
