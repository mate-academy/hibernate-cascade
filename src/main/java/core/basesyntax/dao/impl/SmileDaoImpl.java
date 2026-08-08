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
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException("Can't add comment to database.", e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return entity;
    }

    @Override
    public Smile get(Long id) {
        Session session = factory.openSession();
        try {
            Smile smile = session.get(Smile.class, id);
            return smile;
        } catch (Exception e) {
            throw new RuntimeException("Can not get movie", e);
        } finally {
            session.close();
        }
    }

    @Override
    public List<Smile> getAll() {
        Session session = factory.openSession();
        try {
            return session.createQuery("From Smile", Smile.class).getResultList();
        } catch (Exception e) {
            throw new RuntimeException("Can't get list of comments", e);
        } finally {
            session.close();
        }
    }
}
