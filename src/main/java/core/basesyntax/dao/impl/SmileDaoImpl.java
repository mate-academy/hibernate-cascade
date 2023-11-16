package core.basesyntax.dao.impl;

import core.basesyntax.dao.SmileDao;
import core.basesyntax.model.Smile;
import jakarta.persistence.Query;
import java.util.List;
import org.hibernate.HibernateException;
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
            session.persist(entity);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException("Can`t save smile - " + entity + " to BD", e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return entity;
    }

    @Override
    public Smile get(Long id) {
        try (Session session = factory.openSession()) {
            return session.get(Smile.class, id);
        } catch (HibernateException e) {
            throw new RuntimeException("Could not get instance of smile", e);
        }
    }

    @Override
    public List<Smile> getAll() {
        String sql = "SELECT a FROM Smile a";
        try (Session session = factory.openSession()) {
            Query commentQuery = session.createQuery(sql, Smile.class);
            return commentQuery.getResultList();
        } catch (HibernateException e) {
            throw new RuntimeException("request not completed", e);
        }
    }
}
