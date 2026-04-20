package core.basesyntax.dao.impl;

import core.basesyntax.dao.MessageDetailsDao;
import core.basesyntax.model.MessageDetails;
import core.basesyntax.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class MessageDetailsDaoImpl implements MessageDetailsDao {

    @Override
    public MessageDetails create(MessageDetails entity) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        session.persist(entity);
        tx.commit();
        session.close();
        return entity;
    }

    @Override
    public MessageDetails get(Long id) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        MessageDetails details = session.get(MessageDetails.class, id);
        session.close();
        return details;
    }
}

