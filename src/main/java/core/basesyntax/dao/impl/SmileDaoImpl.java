package core.basesyntax.dao.impl;

import core.basesyntax.dao.SmileDao;
import core.basesyntax.exception.DataProcessingException;
import core.basesyntax.model.Smile;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Query;
import java.util.List;
import org.hibernate.SessionFactory;

public class SmileDaoImpl extends AbstractDao implements SmileDao {
    public SmileDaoImpl(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    @Override
    public Smile create(Smile smile) {
        EntityManager entityManager = null;
        EntityTransaction transaction = null;
        try {
            entityManager = factory.openSession();
            transaction = entityManager.getTransaction();
            transaction.begin();
            entityManager.persist(smile);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new DataProcessingException("Failed to save a smile: " + smile, e);
        } finally {
            if (entityManager != null) {
                entityManager.close();
            }
        }
        return smile;
    }

    @Override
    public Smile get(Long id) {
        try (EntityManager entityManager = factory.openSession()) {
            return entityManager.find(Smile.class, id);
        } catch (Exception e) {
            throw new DataProcessingException("Failed to get a smile with id: " + id, e);
        }
    }

    @Override
    public List<Smile> getAll() {
        try (EntityManager entityManager = factory.openSession()) {
            Query query = entityManager.createQuery("from Smile", Smile.class);
            return query.getResultList();
        } catch (Exception e) {
            throw new DataProcessingException("Failed to get all smiles", e);
        }
    }
}
