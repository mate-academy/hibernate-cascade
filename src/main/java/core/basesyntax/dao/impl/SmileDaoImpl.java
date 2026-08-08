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
            session.persist(entity);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException("Error occurred when we try to save new "
                    + "entity to DB", e);
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
            Query<Smile> query = session.createQuery("from Smile where id = :value",
                    Smile.class);
            query.setParameter("value",id);
            return query.getSingleResult();

        } catch (Exception e) {
            throw new RuntimeException("Error occurred when"
                    + " trying to get entity by id - " + id, e);
        }
    }

    @Override
    public List<Smile> getAll() {
        try (Session session = factory.openSession()) {
            Query<Smile> query = session.createQuery("from Smile",Smile.class);
            return query.getResultList();

        } catch (Exception e) {
            throw new RuntimeException("Error occurred when"
                    + " trying to get list of entities - ", e);
        }
    }
}
