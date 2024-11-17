package core.basesyntax.dao.impl;

import static core.basesyntax.HibernateUtil.getSessionFactory;

import core.basesyntax.dao.MessageDetailsDao;
import core.basesyntax.model.MessageDetails;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

public class MessageDetailsDaoImpl extends AbstractDao implements MessageDetailsDao {
    public MessageDetailsDaoImpl(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    @Override
    public MessageDetails create(MessageDetails entity) {
        try (Session session = getSessionFactory().openSession()) {
            session.beginTransaction();
            session.save(entity);
            session.getTransaction().commit();
            return entity;
        } catch (Exception e) {
            throw new RuntimeException("Can not create MassageDetails: " + entity, e);
        }
    }

    @Override
    public MessageDetails get(Long id) {
        try (Session session = getSessionFactory().openSession()) {
            return session.get(MessageDetails.class, id);
        } catch (Exception e) {
            throw new RuntimeException("Can not get MessageDetails by id: " + id, e);
        }
    }
}
