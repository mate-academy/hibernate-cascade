package core.basesyntax.dao.impl;

import static core.basesyntax.HibernateUtil.getSessionFactory;

import core.basesyntax.dao.SmileDao;
import core.basesyntax.model.Smile;
import java.util.List;

import core.basesyntax.model.User;
import jakarta.persistence.EntityNotFoundException;
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
            throw new RuntimeException("Can't note save Smile to the DB" + entity, e);
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
            return session.get(Smile.class,id);
        } catch (Exception e) {
            throw new EntityNotFoundException("Can't get Smile by id: "
                    + id, e);
        }
    }

    @Override
    public List<Smile> getAll() {
        Session session = null;
        try {
            session = factory.openSession();
            return session.createQuery("from Smile", Smile.class).list();
        } catch (Exception e) {
            throw new RuntimeException("Failed to get all Smile", e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }
}
