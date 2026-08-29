package core.basesyntax.dao.impl;

import core.basesyntax.dao.SmileDao;
import core.basesyntax.model.Smile;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

public class SmileDaoImpl extends AbstractDao implements SmileDao {
    public SmileDaoImpl(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    @Override
    public Smile create(Smile entity) {
        Transaction transaction = null;

        try (Session session = factory.openSession()) {
            transaction = session.beginTransaction();

            session.persist(entity);
            transaction.commit();
            return entity;
        } catch (Exception e) {
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
            throw new RuntimeException("Cannot create smile", e);
        }
    }

    @Override
    public Smile get(Long id) {
        Transaction transaction = null;

        try (Session session = factory.openSession()) {
            transaction = session.beginTransaction();

            Smile smile = session.get(Smile.class, id);
            transaction.commit();
            return smile;
        } catch (Exception e) {
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
            throw new RuntimeException("Cannot get smile by id", e);
        }
    }

    @Override
    public List<Smile> getAll() {
        Transaction transaction = null;

        try (Session session = factory.openSession()) {
            transaction = session.beginTransaction();

            List<Smile> smiles = session
                    .createQuery("FROM Smile", Smile.class)
                    .getResultList();
            transaction.commit();
            return smiles;
        } catch (Exception e) {
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
            throw new RuntimeException("Cannot get all smiles", e);
        }
    }
}
