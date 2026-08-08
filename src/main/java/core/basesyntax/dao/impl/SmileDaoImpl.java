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
            throw new RuntimeException("Can't create new smile: " + entity, e);
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
            return entityManager.find(Smile.class, id);
        } catch (Exception e) {
            throw new RuntimeException("Can't get smile by id: " + id, e);
        }
    }

    @Override
    public List<Smile> getAll() {
        try (EntityManager entityManager = factory.createEntityManager()) {
            return entityManager
                    .createNamedQuery("getAllSmiles", Smile.class)
                    .getResultList();
        } catch (Exception e) {
            throw new RuntimeException("Can't get all smiles", e);
        }
    }
}
