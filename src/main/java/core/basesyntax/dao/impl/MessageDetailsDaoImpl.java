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
        EntityTransaction entityTransaction = null;
        try {
            entityManager = factory.openSession();
            entityTransaction = entityManager.getTransaction();
            entityTransaction.begin();
            entityManager.persist(entity);
            entityTransaction.commit();
            return entity;
        } catch (Exception e) {
            if (entityTransaction != null) {
                entityTransaction.rollback();
            }
            throw new RuntimeException("Can't add message details to DB" + entity);
        } finally {
            if (entityManager != null) {
                entityManager.close();
            }
        }
    }

    @Override
    public MessageDetails get(Long id) {
        EntityManager entityManager = null;
        EntityTransaction entityTransaction = null;
        try {
            entityManager = factory.openSession();
            entityTransaction = entityManager.getTransaction();
            entityTransaction.begin();
            return entityManager.find(MessageDetails.class, id);
        } catch (Exception e) {
            if (entityTransaction != null) {
                entityTransaction.rollback();
            }
            throw new RuntimeException("Can't get message details from DB by id" + id);
        } finally {
            if (entityManager != null) {
                entityManager.close();
            }
        }
    }
}
