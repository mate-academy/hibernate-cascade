package core.basesyntax.dao.impl;

import core.basesyntax.dao.SmileDao;
import core.basesyntax.model.Smile;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import java.util.List;
import org.hibernate.SessionFactory;

public class SmileDaoImpl extends AbstractDao implements SmileDao {
    public SmileDaoImpl(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    @Override
    public Smile create(Smile entity) {
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
            throw new RuntimeException("Cannot create smile: " + entity, e);
        } finally {
            if (entityManager != null) {
                entityManager.close();
            }
        }
        return entity;
    }

    @Override
    public Smile get(Long id) {
        EntityManager entityManager = factory.createEntityManager();
        return entityManager.find(Smile.class, id);
    }

    @Override
    public List<Smile> getAll() {
        EntityManager entityManager = factory.createEntityManager();
        return entityManager.createQuery("SELECT c FROM Smile c", Smile.class).getResultList();
    }
}
