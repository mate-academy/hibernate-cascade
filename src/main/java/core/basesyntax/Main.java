package core.basesyntax;

import core.basesyntax.dao.*;
import core.basesyntax.dao.impl.*;
import core.basesyntax.model.Comment;
import core.basesyntax.model.MessageDetails;
import core.basesyntax.model.Smile;
import core.basesyntax.model.User;

import java.time.LocalDateTime;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        SmileDao smileDao = new SmileDaoImpl(HibernateUtil.getSessionFactory());
        CommentDao commentDao = new CommentDaoImpl(HibernateUtil.getSessionFactory());
        UserDao userDao = new UserDaoImpl(HibernateUtil.getSessionFactory());
        MessageDetailsDao messageDetailsDao = new MessageDetailsDaoImpl(HibernateUtil.getSessionFactory());

//        Smile smile = new Smile("fun");
//        Smile smile2 = new Smile("sad");
//
//        smileDao.create(smile);
//        smileDao.create(smile2);
//
//        Smile funSmileFromDb = smileDao.get(1L);
//        Smile sadSmileFromDb = smileDao.get(2L);
//
//        Comment comment = new Comment();
//        comment.setContent("Cool");
//        comment.setSmiles(List.of(funSmileFromDb));
//
//        Comment comment2 = new Comment();
//        comment2.setContent("This is amazing");
//        comment2.setSmiles(List.of(sadSmileFromDb));
//
//        commentDao.create(comment);
//        commentDao.create(comment2);
//
//        Comment commentNotFunny = new Comment();
//        commentNotFunny.setContent("not funny");
//
//        User user = new User();
//        user.setUsername("Max");
//        user.setComments(List.of(commentNotFunny));
//
//        userDao.create(user);

        MessageDetails messageDetails = messageDetailsDao.get(1L);
        System.out.println(messageDetails);

        commentDao.getAll()

    }
}
