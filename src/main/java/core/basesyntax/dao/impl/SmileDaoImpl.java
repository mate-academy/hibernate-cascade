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
            throw new RuntimeException("Cannot create Smile: " + entity.toString(), e);
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
        Smile entity = null;

        try {
            session = factory.openSession();
            entity = (Smile) session.get(Smile.class, id);
        } catch (Exception e) {
            throw new RuntimeException("Cannot get Smile: " + id, e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return entity;
    }

    @Override
    public List<Smile> getAll() {
        List<Smile> entityList = null;
        Session session = null;

        try {
            session = factory.openSession();
            entityList = session.createQuery("FROM Smile", Smile.class).getResultList();
        } catch (Exception e) {
            throw new RuntimeException("Cannot get Smile: " + e, e);
        } finally {
            if (session != null) {
                session.close();
            }
        }

        return entityList;
    }
}
