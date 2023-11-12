package core.basesyntax.dao.impl;

import core.basesyntax.dao.SmileDao;
import core.basesyntax.model.Smile;
import java.util.ArrayList;
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
        } catch (RuntimeException e) {
            if (transaction != null) {
                transaction.rollback();
            }
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
            Smile smile = session.get(Smile.class, id);
            return smile;
        } catch (RuntimeException e) {
            throw new RuntimeException("Can't get smile with id: " + id, e);
        }
    }

    @Override
    public List<Smile> getAll() {
        List<Smile> allSmile = new ArrayList<>();
        try (Session session = factory.openSession()) {
            Long id = 1L;
            while (session.get(Smile.class, id) != null) {
                allSmile.add(session.get(Smile.class, id));
                id++;
            }
        }
        return allSmile;
    }
}
