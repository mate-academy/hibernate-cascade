package core.basesyntax.dao.impl;

import core.basesyntax.dao.SmileDao;
import core.basesyntax.model.Smile;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
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
            throw new RuntimeException("Couldn't create smile " + smile, e);
        } finally {
            if (entityManager != null) {
                entityManager.close();
            }
        }
        return smile;
    }

    @Override
    public Smile get(Long id) {
        EntityManager entityManager = null;
        try {
            entityManager = factory.openSession();
            return entityManager.find(Smile.class, id);
        } catch (Exception e) {
            throw new RuntimeException("Couldn't get user by ID " + id, e);
        } finally {
            if (entityManager != null) {
                entityManager.close();
            }
        }
    }

    @Override
    public List<Smile> getAll() {
        EntityManager entityManager = null;
        try {
            entityManager = factory.openSession();
            return entityManager.createQuery("FROM Smile e", Smile.class).getResultList();
        } catch (Exception e) {
            throw new RuntimeException("Couldn't get all smiles", e);
        } finally {
            if (entityManager != null) {
                entityManager.close();
            }
        }
    }
}
