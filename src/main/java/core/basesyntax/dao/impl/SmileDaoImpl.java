package core.basesyntax.dao.impl;

import core.basesyntax.dao.SmileDao;
import core.basesyntax.exception.DataProcessingException;
import core.basesyntax.model.Smile;
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
            session = this.factory.openSession();
            transaction = session.beginTransaction();
            session.persist(entity);
            transaction.commit();
        } catch (HibernateException e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new DataProcessingException("Can't create/add " + entity
                    + " in database", e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return entity;
    }

    @Override
    public Smile get(Long id) {
        try (Session session = this.factory.openSession()) {
            return session.get(Smile.class, id);
        } catch (HibernateException e) {
            throw new DataProcessingException("Can't get smile by id=" + id
                    + " from database", e);
        }
    }

    @Override
    public List<Smile> getAll() {
        try (Session session = this.factory.openSession()) {
            return session.createQuery(
                    "from Smile", Smile.class).getResultList();
        } catch (HibernateException e) {
            throw new DataProcessingException("Can't get all smiles from database", e);
        }
    }
}
