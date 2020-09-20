package core.basesyntax.dao.impl;

import core.basesyntax.dao.CommentDao;
import core.basesyntax.model.Comment;
import java.util.List;
import javax.persistence.criteria.CriteriaQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

public class CommentDaoImpl extends AbstractDao implements CommentDao {
    public CommentDaoImpl(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    @Override
    public Comment create(Comment entity) {
        return null;
    }

    @Override
    public Comment get(Long id) {
        return null;
    }

    @Override
    public List<Comment> getAll() {
        return null;
    }

    @Override
    public void remove(Comment entity) {
        Session session = factory.openSession();
        Transaction transaction = session.beginTransaction(); // Think we we do need it?
        session.remove(entity);
        transaction.commit();
        session.close();
    }
}
