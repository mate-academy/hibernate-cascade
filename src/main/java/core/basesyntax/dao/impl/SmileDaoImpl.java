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
    public Smile create(Smile entity) {
        EntityManager entityManager = null;
        EntityTransaction entityTransaction = null;
        try {
            entityManager = factory.openSession();
            entityTransaction = entityManager.getTransaction();
            entityTransaction.begin();
            entityManager.persist(entity);
            entityTransaction.commit();
        } catch (Exception e) {
            if (entityTransaction != null) {
                entityTransaction.rollback();
            }
            throw new DataProcessingException("Can not add smile " + entity + "into DB", e);
        } finally {
            if (entityManager != null) {
                entityManager.close();
            }
        }
        return entity;
    }

    @Override
    public Smile get(Long id) {
        try (EntityManager entityManager = factory.openSession()) {
            return entityManager.find(Smile.class, id);
        } catch (Exception e) {
            throw new DataProcessingException("Can not get smile bt id " + id, e);
        }
    }

    @Override
    public List<Smile> getAll() {
        try (EntityManager entityManager = factory.openSession()) {
            Query query = entityManager.createQuery("from Smile", Smile.class);
            return query.getResultList();
        } catch (Exception e) {
            throw new DataProcessingException("Can not get all smiles", e);
        }
    }
}
