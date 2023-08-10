package core.basesyntax;

import core.basesyntax.dao.CommentDao;
import core.basesyntax.dao.impl.CommentDaoImpl;
import core.basesyntax.model.Comment;
import core.basesyntax.model.Smile;
import org.hibernate.SessionFactory;
import java.util.ArrayList;
import java.util.List;

public class MainSave {
  public static void main(String[] args) {
    SessionFactory sessionFactory = HibernateUtil.getSessionFactory();

    Smile smile01 = new Smile();
    Smile smile02 = new Smile();
    smile01.setValue(":)");
    smile02.setValue(":(");

    Comment comment = new Comment();
    List<Smile> smiles = new ArrayList<>();
    smiles.add(smile01);
    smiles.add(smile02);
    comment.setSmiles(smiles);

    CommentDao commentDao = new CommentDaoImpl(sessionFactory);
    commentDao.create(comment);
  }
}
