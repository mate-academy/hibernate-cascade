package core.basesyntax.dao.impl;

import core.basesyntax.dao.SmileDao;
import core.basesyntax.model.Smile;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

public class SmileDaoImpl extends AbstractDao implements SmileDao {
    public SmileDaoImpl(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    @Override
    public Smile create(Smile entity) {
        Session session = null;
        Transaction transaction = null;
        try {
            session = factory.openSession();
            transaction = session.beginTransaction();
            session.persist(entity);
            transaction.commit();
        } catch (RuntimeException e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException("Can't create smile: " + entity, e);
        } finally {
            if (session != null) {
                session.close();
            }

        }
        return entity;
    }

    @Override
    public Smile get(Long id) {
        Smile smile = null;
        try (Session session = factory.openSession()) {
            smile = session.get(Smile.class, id);
        } catch (RuntimeException e) {
            throw new RuntimeException("Can't get smile by id: " + id, e);
        }
        return smile;
    }

    @Override
    public List<Smile> getAll() {
        List<Smile> smiles = new ArrayList<>();
        try (Session session = factory.openSession()) {
            Transaction transaction = session.beginTransaction();
            String sql = "FROM Smile";
            Query<Smile> query = session.createQuery(sql, Smile.class);
            smiles = query.list();
            transaction.commit();
        } catch (RuntimeException e) {
            throw new RuntimeException("Can't get all smiles from DB", e);
        }
        return smiles;
    }
}
