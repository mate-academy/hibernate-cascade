package core.basesyntax.dao.impl;

import core.basesyntax.dao.SmileDao;
import core.basesyntax.exceptions.DataProcessingException;
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
        if (getByValue(entity.getValue()) == null) {
            Session session = null;
            Transaction transaction = null;
            try {
                session = factory.openSession();
                transaction = session.beginTransaction();
                session.save(entity);
                transaction.commit();
            } catch (Exception e) {
                if (transaction != null) {
                    transaction.rollback();
                }
                throw new DataProcessingException("Can't create smile", e);
            } finally {
                if (session != null) {
                    session.close();
                }
            }
        }
        return entity;
    }

    @Override
    public Smile get(Long id) {
        try (Session session = factory.openSession()) {
            return session.get(Smile.class, id);
        }
    }

    @Override
    public List<Smile> getAll() {
        try (Session session = factory.openSession()) {
            return session.createQuery("from Smile", Smile.class).list();
        } catch (Exception e) {
            throw new DataProcessingException("Can't get smiles", e);
        }
    }

    @Override
    public void remove(Smile entity) {
        Session session = null;
        Transaction transaction = null;
        try {
            session = factory.openSession();
            transaction = session.beginTransaction();
            session.remove(entity);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    private Smile getByValue(String value) {
        try (Session session = factory.openSession()) {
            return session.createQuery("from Smile where value=:value", Smile.class)
                    .setParameter("value", value)
                    .uniqueResult();
        }
    }
}
