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
        Session session = factory.openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            session.save(entity);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException("Can`t save entity:" + entity,e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return entity;
    }

    @Override
    public Smile get(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("ID cannot be null!");
        }
        try (Session session = factory.openSession()) {
            Smile result = session.get(Smile.class, id);
            if (result != null) {
                return result;
            }
            throw new IllegalArgumentException("ID cannot be null!");
        }
    }

    @Override
    public List<Smile> getAll() {
        try (Session session = factory.openSession()) {
            List<Smile> smiles = session.createQuery("from Smile").list();
            return smiles;
        }
    }
}
