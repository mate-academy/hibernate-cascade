package core.basesyntax.dao.impl;

import core.basesyntax.dao.SmileDao;
import core.basesyntax.model.Smile;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Query;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
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
            return entity;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException("Error while creating smile: " + entity, e);
        } finally {
            if (entityManager != null) {
                entityManager.close();
            }
        }
    }

    @Override
    public Smile get(Long id) {
        try (EntityManager entityManager = factory.createEntityManager()) {
            return entityManager.find(Smile.class, id);
        } catch (Exception e) {
            throw new RuntimeException("Error while getting smile: " + id, e);
        }
    }

    @Override
    public List<Smile> getAll() {
        try (EntityManager entityManager = factory.createEntityManager()) {
            CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
            CriteriaQuery<Smile> criteriaQuery = criteriaBuilder.createQuery(Smile.class);
            Root<Smile> smileRoot = criteriaQuery.from(Smile.class);

            criteriaQuery.select(smileRoot);
            Query query = entityManager.createQuery(criteriaQuery);
            return query.getResultList();
        } catch (Exception e) {
            throw new RuntimeException("Error while getting all smiles", e);
        }
    }
}
