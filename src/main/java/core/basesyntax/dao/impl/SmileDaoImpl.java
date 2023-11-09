package core.basesyntax.dao.impl;

import core.basesyntax.HibernateUtil;
import core.basesyntax.dao.SmileDao;
import core.basesyntax.model.Smile;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

public class SmileDaoImpl extends AbstractDao implements SmileDao {
    private SessionFactory sessionFactory = HibernateUtil.getSessionFactory();

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
            throw new RuntimeException("Can`t create smile to DB", e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return entity;
    }

    @Override
    public Smile get(Long id) {
        Session session = sessionFactory.openSession();
        return session.get(Smile.class, id);
    }

    @Override
    public List<Smile> getAll() {
        Session session = sessionFactory.openSession();
        List<Smile> smileList;
        try {
            smileList = session.createQuery("FROM Smile", Smile.class).list();
        } finally {
            session.close();
        }

        return smileList;
    }
}
