package core.basesyntax.dao.impl;

import core.basesyntax.dao.SmileDao;
import core.basesyntax.exception.DataProcessingException;
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
            throw new DataProcessingException("Could not create new "
                    + entity.getClass().getSimpleName()
                    + ", "
                    + entity, e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    @Override
    public Smile get(Long id) {
        Session session = null;
        try {
            session = factory.openSession();
            return session.get(Smile.class, id);
        } catch (DataProcessingException e) {
            throw new DataProcessingException("Can't get "
                    + Smile.class.getSimpleName()
                    + " by id: " + id);
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    @Override
    public List<Smile> getAll() {
        Session session = null;
        try {
            session = factory.openSession();
            return session.createQuery("from Smile", Smile.class).list();
        } catch (Exception e) {
            throw new DataProcessingException("Can't get any "
                    + Smile.class.getSimpleName());
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }
}
