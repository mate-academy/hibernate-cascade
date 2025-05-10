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
        Session session = factory.openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            session.save(entity);
            transaction.commit();
            return entity;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException("Can't insert smile entity", e);
        } finally {
            session.close();
        }
    }

    @Override
    public Smile get(Long id) {
        Session session = factory.openSession();
        try {
            return session.get(Smile.class, id);
        } catch (Exception e) {
            throw new RuntimeException("Can't get smail by id " + id, e);
        } finally {
            session.close();
        }
    }

    @Override
    public List<Smile> getAll() {
        Session session = factory.openSession();
        try {
            return session.createQuery("FROM Smile", Smile.class).getResultList();
        } catch (Exception e) {
            throw new RuntimeException("Can't get list of comments", e);
        } finally {
            session.close();
        }
    }
}
