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

    private Session getSession() {
        return factory.openSession();
    }

    @Override
    public Smile create(Smile entity) {
        Transaction tx = null;
        try (Session session = getSession()) {
            tx = session.beginTransaction();
            session.persist(entity);
            tx.commit();
            return entity;
        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
            }
            throw new RuntimeException("Failed to create Message", e);
        }
    }

    @Override
    public Smile get(Long id) {
        return getSession().get(Smile.class, id);
    }

    @Override
    public List<Smile> getAll() {
        try (Session session = getSession()) {
            return session.createQuery("FROM Smile", Smile.class).list();
        }
    }
}
