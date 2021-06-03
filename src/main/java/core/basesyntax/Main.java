package core.basesyntax;

import core.basesyntax.dao.impl.CommentDaoImpl;
import core.basesyntax.model.Comment;
import org.hibernate.SessionFactory;

public class Main {
    public static void main(String[] args) {
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Comment comment = new Comment();
        CommentDaoImpl cdi = new CommentDaoImpl(sessionFactory);
        comment = cdi.get(2L);
        cdi.remove(comment);
        System.out.println(cdi.getAll());
    }
}
