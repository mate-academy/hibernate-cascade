package core.basesyntax.dao.impl;

import core.basesyntax.dao.SmileDao;
import core.basesyntax.model.Smile;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;
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

            throw new RuntimeException("Can't save smile to DB: " + entity, e);
        } finally {
            if (entityManager != null) {
                entityManager.close();
            }
        }

        return entity;
    }

    @Override
    public Smile get(Long id) {
        Smile smile;

        EntityManager entityManager = null;

        try {
            entityManager = factory.createEntityManager();
            smile = entityManager.find(Smile.class, id);
        } catch (Exception e) {
            throw new RuntimeException("Could not retrieve smile from DB with id: " + id, e);
        } finally {
            if (entityManager != null) {
                entityManager.close();
            }
        }

        return smile;
    }

    @Override
    public List<Smile> getAll() {
        EntityManager entityManager = null;
        List<Smile> list = new ArrayList<>();

        try {
            entityManager = factory.createEntityManager();
            String hql = "FROM Smile";
            Query query = entityManager.createQuery(hql);
            list.addAll((List<Smile>) query.getResultList());
        } catch (Exception e) {
            throw new RuntimeException("Could not get all smiles from DB", e);
        } finally {
            if (entityManager != null) {
                entityManager.close();
            }
        }

        return list;
    }
}
