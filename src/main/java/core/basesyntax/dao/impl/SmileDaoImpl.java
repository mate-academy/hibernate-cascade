package core.basesyntax.dao.impl;

import core.basesyntax.dao.SmileDao;
import core.basesyntax.model.Smile;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.EntityTransaction;
import java.util.List;
import java.util.Optional;
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
            throw new RuntimeException("Error adding smile " + entity, e);
        } finally {
            if (entityManager != null) {
                entityManager.close();
            }
        }
        return entity;
    }

    @Override
    public Smile get(Long id) {
        try (EntityManager entityManager = factory.createEntityManager()) {
            return Optional
                    .ofNullable(entityManager.find(Smile.class, id))
                    .orElseThrow(() -> new EntityNotFoundException(
                            "Smile with id " + id + " not found"));
        }
    }

    @Override
    public List<Smile> getAll() {
        try (EntityManager entityManager = factory.createEntityManager()) {
            return entityManager
                    .createQuery("SELECT s FROM Smile s", Smile.class)
                    .getResultList();
        } catch (Exception e) {
            throw new RuntimeException("Error founding all smiles", e);
        }
    }
}
