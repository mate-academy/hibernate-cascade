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
        Transaction transaction = null;
        try (Session session = factory.openSession()) {
            transaction = session.beginTransaction();
            session.persist(entity);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException(
                    "Can't create Smile entity: " + entity + " , error: " + e);
        }
        return entity;
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
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException("Can't get Smile by id: " + id + " , error: " + e);
        }
    }

    @Override
    public List<Smile> getAll() {
        String hql = "FROM Smile";
        try (Session session = factory.openSession()) {
            Query<Smile> query = session.createQuery(hql, Smile.class);
            return query.getResultList();
        } catch (Exception e) {
            throw new RuntimeException("SmileDaoImpl.getAll() failed to fetch users", e);
        }
    }
}
