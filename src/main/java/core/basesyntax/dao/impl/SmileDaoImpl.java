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
            session.save(entity);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException("Cannot create entity " + entity);
        } finally {
            if (transaction != null) {
                session.close();
            }
        }
        return entity;
    }

    @Override
    public Smile get(Long id) {
        try (Session session = factory.openSession()) {
            try {
                return session.get(Smile.class, id);
            } catch (Exception e) {
                throw new RuntimeException("cannot getting Smile by id " + id, e);
            }
        } catch (Exception e) {
            throw new RuntimeException("cannot closing session", e);
        }
    }

    @Override
    public List<Smile> getAll() {
        try (Session session = factory.openSession()) {
            String hql = "FROM Smile";
            Query<Smile> query = session.createQuery(hql, Smile.class);
            List<Smile> resultList = query.getResultList();
            return new ArrayList<>(resultList);
        } catch (Exception e) {
            throw new RuntimeException("Error getting all smiles", e);
        }
    }
}
