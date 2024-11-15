package core.basesyntax.dao.impl;

import core.basesyntax.dao.SmileDao;
import core.basesyntax.model.Comment;
import core.basesyntax.model.Smile;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

import static core.basesyntax.HibernateUtil.getSessionFactory;

public class SmileDaoImpl extends AbstractDao implements SmileDao {
    public SmileDaoImpl(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    @Override
    public Smile create(Smile entity) {
        try (Session session = getSessionFactory().openSession()) {
            session.beginTransaction();
            session.save(entity);
            session.getTransaction().commit();
            return entity;
        } catch (Exception e) {
            throw new RuntimeException("Can not create Smile: " + entity, e);
        }
    }

    @Override
    public Smile get(Long id) {
        try (Session session = getSessionFactory().openSession()) {
            return session.get(Smile.class, id);
        } catch (Exception e) {
            throw new RuntimeException("Can not get Smile by id: " + id, e);
        }
    }

    @Override
    public List<Smile> getAll() {
        try (Session session = getSessionFactory().openSession()) {
            String sql = "FROM Smile";
            return session.createQuery(sql, Smile.class).list();
        } catch (Exception e) {
            throw new RuntimeException("Can not get all Smile", e);
        }
    }
}
