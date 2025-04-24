package core.basesyntax.dao.impl;

import core.basesyntax.HibernateUtil;
import core.basesyntax.dao.SmileDao;
import core.basesyntax.model.Message;
import core.basesyntax.model.Smile;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

public class SmileDaoImpl extends AbstractDao implements SmileDao {
    private Session session = null;
    private Transaction transaction = null;

    public SmileDaoImpl(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    @Override
    public Smile create(Smile entity) {
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            transaction = session.beginTransaction();
            transaction.begin();
            session.save(entity);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException("Can not save entity... :",e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return entity;
    }

    @Override
    public Smile get(Long id) {
        Smile smile = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            smile = session.get(Smile.class, id);
        }
        return smile;
    }

    @Override
    public List<Smile> getAll() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<Smile> query = session.createQuery("FROM Smile", Smile.class);
            return query.list();
        }
    }
}
