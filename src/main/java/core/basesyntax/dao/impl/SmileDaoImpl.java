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
        EntityTransaction transaction = null;

        try (EntityManager session = this.factory.createEntityManager()) {
            transaction = session.getTransaction();
            transaction.begin();
            session.persist(entity);
            transaction.commit();

            return entity;
        } catch (Exception e) {
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }

            throw new RuntimeException("Can't create new Smile " + entity, e);
        }
    }

    @Override
    public Smile get(Long id) {
        EntityTransaction transaction = null;

        try (EntityManager session = this.factory.createEntityManager()) {
            transaction = session.getTransaction();
            transaction.begin();
            Smile smile = session.find(Smile.class, id);
            transaction.commit();

            return smile;
        } catch (Exception e) {
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }

            throw new RuntimeException("Can't find Smile by id: " + id, e);
        }
    }

    @Override
    public List<Smile> getAll() {
        EntityTransaction transaction = null;

        try (EntityManager session = this.factory.createEntityManager()) {
            transaction = session.getTransaction();
            transaction.begin();
            List<Smile> smiles = session
                    .createQuery("from Smile", Smile.class)
                    .getResultList();
            transaction.commit();

            return smiles;
        } catch (Exception e) {
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }

            throw new RuntimeException("Can't get Smiles", e);
        }
    }
}
