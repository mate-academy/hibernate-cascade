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
    public MessageDetails create(MessageDetails entity) {
        EntityManager entityManager = null;
        EntityTransaction transaction = null;

        try {
            entityManager = factory.createEntityManager();
            transaction = entityManager.getTransaction();
            transaction.begin();
            entityManager.persist(entity);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }

            throw new RuntimeException("Can't save message details to DB: " + entity, e);
        } finally {
            if (entityManager != null) {
                entityManager.close();
            }
        }

        return entity;
    }

    @Override
    public MessageDetails get(Long id) {
        MessageDetails messageDetails;

        EntityManager entityManager = null;

        try {
            entityManager = factory.createEntityManager();
            messageDetails = entityManager.find(MessageDetails.class, id);
        } catch (Exception e) {
            throw new RuntimeException("Could not retrieve message details from DB with id: "
                + id, e);
        } finally {
            if (entityManager != null) {
                entityManager.close();
            }
        }

        return messageDetails;
    }
}
