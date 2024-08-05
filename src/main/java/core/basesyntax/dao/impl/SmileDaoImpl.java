package core.basesyntax.dao.impl;

import core.basesyntax.dao.SmileDao;
import core.basesyntax.model.Smile;
import java.util.ArrayList;
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
            throw new RuntimeException("Can't add Smile '" + entity + "' to DB", e);
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
            return session.get(Smile.class, id);
        } catch (Exception e) {
            throw new RuntimeException("Can't get Smile by id '" + id + "' from DB", e);
        }
    }

    @Override
    public List<Smile> getAll() {
        List<Smile> commentList = new ArrayList<>();
        try (Session session = factory.openSession()) {
            Query<Smile> query = session.createQuery("FROM smiles", Smile.class);
            List<Smile> list = query.getResultList();
            commentList.addAll(list);
        } catch (Exception e) {
            throw new RuntimeException("Can't get Smiles from DB", e);
        }
        return commentList;
    }
}
