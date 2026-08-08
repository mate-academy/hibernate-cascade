package core.basesyntax.dao.impl;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import core.basesyntax.HibernateUtil;
import core.basesyntax.dao.SmileDao;
import core.basesyntax.model.Smile;

public class SmileDaoImpl implements SmileDao {
    private final SessionFactory sessionFactory;

    public SmileDaoImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public SmileDaoImpl() {
        this(HibernateUtil.getSessionFactory());
    }

    @Override
    public Smile create(Smile smile) {
        Transaction tx = null;
        try (Session session = sessionFactory.openSession()) {
            tx = session.beginTransaction();
            session.persist(smile);
            tx.commit();
            return smile;
        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
            }
            throw new RuntimeException("Can't create smile", e);
        }
    }

    @Override
    public Smile get(Long id) {
        try (Session session = sessionFactory.openSession()) {
            return session.get(Smile.class, id);
        }
    }

    @Override
    public List<Smile> getAll() {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("FROM Smile", Smile.class).getResultList();
        }
    }
}


