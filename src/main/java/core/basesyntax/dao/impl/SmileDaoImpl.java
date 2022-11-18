package core.basesyntax.dao.impl;

import core.basesyntax.dao.SmileDao;
import core.basesyntax.model.Smile;
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
            throw new RuntimeException("Can't add smile to db: "
                    + entity.getValue(), e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return entity;
    }

    @Override
    public Smile get(Long id) {
        Session session = null;
        Smile smile;
        try {
            session = factory.openSession();
            smile = session.get(Smile.class, id);
        } catch (Exception e) {
            throw new RuntimeException("Can't get smile from db: "
                    + id, e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return smile;
    }

    @Override
    public List<Smile> getAll() {
        Session session = null;
        List<Smile> smiles;
        try {
            session = factory.openSession();
            Query<Smile> fromSmile = session.createQuery("FROM Smile", Smile.class);
            smiles = fromSmile.getResultList();
        } catch (Exception e) {
            throw new RuntimeException("Can't get all from smile db", e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return smiles;
    }
}
