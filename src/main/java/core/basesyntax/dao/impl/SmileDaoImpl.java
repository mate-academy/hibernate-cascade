package core.basesyntax.dao.impl;

import core.basesyntax.HibernateUtil;
import core.basesyntax.dao.SmileDao;
import core.basesyntax.model.Comment;
import core.basesyntax.model.Smile;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

public class SmileDaoImpl extends AbstractDao implements SmileDao {
    public SmileDaoImpl(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    @Override
    public Smile create(Smile entity) {
        Transaction transaction = null;
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            transaction = session.beginTransaction();
            session.save(entity);
            transaction.commit();
            return entity;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException("Can't insert Smile entity", e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    @Override
    public Smile get(Long id) {
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            return session.get(Smile.class, id);
        } catch (Exception e) {
            throw new RuntimeException("Can't insert Comment entity", e);
        }
    }

    @Override
    public List<Smile> getAll() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<Smile> getAllSmile = session.createQuery("from Smile", Smile.class);
            return getAllSmile.getResultList();
        } catch (Exception e) {
            throw new RuntimeException("Can't show all comments", e);
        }
    }
}
