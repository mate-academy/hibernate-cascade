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

            session.persist(entity);
            transaction.commit();
            return entity;

        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException("Can't add Smile object in database", e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    @Override
    public Smile get(Long id) {
        Smile smile = null;

        try (Session session = factory.openSession()) {
            smile = session.get(Smile.class, id);
        } catch (Exception e) {
            throw new RuntimeException("Can't retrieve Smile object by id:" + id, e);
        }

        return smile;
    }

    @Override
    public List<Smile> getAll() {
        List<Smile> smileList = null;

        try (Session session = factory.openSession()) {
            smileList = session.createQuery("FROM Smile", Smile.class).list();
        } catch (Exception e) {
            throw new RuntimeException("Can't retrieve all Smile objects:", e);
        }

        return smileList;
    }
}
