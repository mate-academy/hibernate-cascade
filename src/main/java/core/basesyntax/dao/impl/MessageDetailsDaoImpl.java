package core.basesyntax.dao.impl;

import core.basesyntax.dao.MessageDetailsDao;
import core.basesyntax.model.MessageDetails;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import org.hibernate.SessionFactory;

public class MessageDetailsDaoImpl extends AbstractDao implements MessageDetailsDao {
    public MessageDetailsDaoImpl(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    @Override
    public MessageDetails create(MessageDetails entity) {
        EntityManager entityManager = null;
        EntityTransaction entityTransaction = null;
        try {
            entityManager = factory.createEntityManager();
            entityTransaction = entityManager.getTransaction();
            entityTransaction.begin();
            entityManager.persist(entity);
            entityTransaction.commit();
        } catch (Exception e) {
            if (entityTransaction != null) {
                entityTransaction.rollback();
            }
            throw new RuntimeException("Cannot create message details: " + entity, e);
        } finally {
            if (entityManager != null) {
                entityManager.close();
            }
        }
        return entity;
    }

    @Override
    public MessageDetails get(Long id) {
        EntityManager entityManager = factory.createEntityManager();
        return entityManager.find(MessageDetails.class, id);
    }
}
