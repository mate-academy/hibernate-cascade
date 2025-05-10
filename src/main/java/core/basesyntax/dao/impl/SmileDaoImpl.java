package core.basesyntax.dao.impl;

import core.basesyntax.dao.SmileDao;
import core.basesyntax.model.Comment;
import core.basesyntax.model.Smile;
import jakarta.persistence.Table;
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
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException("Cant create smile: " + entity);
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
            throw new RuntimeException("Cant get smile with id: " + id);
        }
    }

    @Override
    public List<Smile> getAll() {
        String tableName = null;
        try (Session session = factory.openSession()) {
            Table tableAnnotation = Comment.class.getAnnotation(Table.class);
            tableName = tableAnnotation != null ? tableAnnotation.name()
                    : Comment.class.getSimpleName();
            return session.createNativeQuery("SELECT * FROM smiles", Smile.class)
                    .getResultList();
        } catch (Exception e) {
            throw new RuntimeException("Cant get all data from " + tableName);
        }
    }
}
