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
    public Smile create(Smile smile) {
        Session session = null;
        Transaction transaction = null;
        try {
            session = factory.openSession();
            transaction = session.beginTransaction();
            session.persist(smile);
            transaction.commit();
        } catch (RuntimeException e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException("Can't add smile " + smile, e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return smile;
    }

    @Override
    public Smile get(Long id) {
        try (Session session = factory.openSession()) {
            return session.get(Smile.class, id);
        } catch (RuntimeException e) {
            throw new RuntimeException("Can't get smile id:" + id + " from DB", e);
        }
    }

    @Override
    public List<Smile> getAll() {
        try (Session session = factory.openSession()) {
            Query<Smile> getAllSmile = session.createQuery("from Smile", Smile.class);
            return getAllSmile.getResultList();
        } catch (RuntimeException e) {
            throw new RuntimeException("Can't get all smile from DB", e);
        }
    }
}
