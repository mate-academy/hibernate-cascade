package core.basesyntax.dao.impl;

import core.basesyntax.DataProcessingException;
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
    public Smile create(Smile smile) {
        if (smile == null) {
            throw new RuntimeException("unacceptable data");
        }

        if (smile.getId() != null && get(smile.getId()) != null) {
            throw new RuntimeException(smile + "already existed in database");
        }

        Session session = null;
        Transaction transaction = null;

        try {
            session = factory.openSession();
            transaction = session.beginTransaction();
            session.persist(smile);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new DataProcessingException("adding " + smile + " into database failed");
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return smile;
    }

    @Override
    public Smile get(Long id) {
        Session session = null;
        Smile smile = null;
        try {
            session = factory.openSession();
            smile = session.get(Smile.class, id);
        } catch (Exception e) {

            throw new DataProcessingException("getting smile with "
                    + id + " from database failed");
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return smile;
    }

    @Override
    public List<Smile> getAll() {
        try (Session session = factory.openSession()) {
            return session.createQuery("from Smile", Smile.class).getResultList();
        }
    }
}
