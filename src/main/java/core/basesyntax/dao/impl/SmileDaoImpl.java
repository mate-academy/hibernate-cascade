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
        Transaction tx = null;
        try {
            session = factory.openSession();
            tx = session.beginTransaction();
            session.persist(entity);
            tx.commit();
        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
            }
            throw new RuntimeException("Can't create comment ");
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
        Transaction tx = null;
        Smile entity = null;
        try {
            session = factory.openSession();
            tx = session.beginTransaction();
            entity = (Smile) session.get(Smile.class, id);
        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
            }
            throw new RuntimeException("Can't get comment ");
        }
        return entity;
    }

    @Override
    public List<Smile> getAll() {
        Session session = null;
        Transaction tx = null;
        List<Smile> entityList = null;
        String sql = "SELECT * FROM smiles";
        try {
            session = factory.openSession();
            tx = session.beginTransaction();
            entityList = session.createNativeQuery(sql, Smile.class).list();
        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
            }
            throw new RuntimeException("Can't get comment ");
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return entityList;
    }
}
