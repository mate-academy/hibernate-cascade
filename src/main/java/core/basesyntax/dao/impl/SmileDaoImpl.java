package core.basesyntax.dao.impl;

import core.basesyntax.dao.SmileDao;
import core.basesyntax.model.Smile;
import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

public class SmileDaoImpl extends AbstractDao implements SmileDao {
    private final SessionFactory sessionFactory;

    public SmileDaoImpl(SessionFactory sessionFactory) {
        super(sessionFactory);
        this.sessionFactory = sessionFactory;
    }

    @Override
    public Smile create(Smile entity) {
        Session session = null;
        Transaction transaction = null;
        try {
            session = sessionFactory.openSession();
            transaction = session.beginTransaction();
            session.persist(entity);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException("Error creating Smile", e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return entity;
    }

    @Override
    public Smile get(Long id) {
        Smile smile;
        try (Session session = sessionFactory.openSession()) {
            smile = session.get(Smile.class, id);
        } catch (HibernateException e) {
            throw new RuntimeException("Error getting smile", e);
        }
        return smile;
    }

    @Override
    public List<Smile> getAll() {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("from Smile", Smile.class).list();
        } catch (Exception e) {
            throw new RuntimeException("Error getting all smiles", e);
        }
    }
}
