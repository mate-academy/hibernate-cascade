package core.basesyntax;

import core.basesyntax.dao.impl.CommentDaoImpl;
import core.basesyntax.dao.impl.MessageDaoImpl;
import core.basesyntax.dao.impl.MessageDetailsDaoImpl;
import core.basesyntax.dao.impl.SmileDaoImpl;
import core.basesyntax.dao.impl.UserDaoImpl;
import core.basesyntax.model.Comment;
import core.basesyntax.model.Message;
import core.basesyntax.model.MessageDetails;
import core.basesyntax.model.Smile;
import core.basesyntax.model.User;
import java.time.LocalDateTime;
import java.util.List;
// HashSet більше не потрібен

public class Main {
    private static final UserDaoImpl userDao = new UserDaoImpl(
            core.basesyntax.HibernateUtil.getSessionFactory());
    private static final CommentDaoImpl commentDao = new CommentDaoImpl(
            core.basesyntax.HibernateUtil.getSessionFactory());
    private static final MessageDaoImpl messageDao = new MessageDaoImpl(
            core.basesyntax.HibernateUtil.getSessionFactory());
    private static final SmileDaoImpl smileDao = new SmileDaoImpl(
            core.basesyntax.HibernateUtil.getSessionFactory());
    private static final MessageDetailsDaoImpl messageDetailsDao = new MessageDetailsDaoImpl(
            core.basesyntax.HibernateUtil.getSessionFactory());

    public static void main(String[] args) {
        System.out.println("--- Запуск тестування Hibernate Cascades ---");

        testUserCommentCascade();

        testCommentSmileCascade();

        testMessageDetailsCascade();

        System.out.println("--- Тестування завершено. Перевірте виведення SQL та стан БД ---");
    }

    private static void testUserCommentCascade() {
        System.out.println("\n--- Сценарій 1: User та Comment (PERSIST, NO REMOVE) ---");

        User newUser = new User();
        newUser.setUsername("JohnDoe");

        Comment c1 = new Comment();
        c1.setContent("Чудовий пост!");
        c1.setUser(newUser);

        Comment c2 = new Comment();
        c2.setContent("Дякую за інформацію.");
        c2.setUser(newUser);

        newUser.getComments().add(c1);
        newUser.getComments().add(c2);

        userDao.create(newUser);
        System.out.println("1. Створено User та 2 Comment (через PERSIST). User ID: "
                + newUser.getId());
        System.out.println("   Comment IDs: " + c1.getId() + ", " + c2.getId());

        userDao.remove(newUser);
        System.out.println("2. Видалено User. Перевірка коментарів...");

        Comment foundC1 = commentDao.get(c1.getId());
        Comment foundC2 = commentDao.get(c2.getId());

        String c1User = foundC1 != null && foundC1.getUser() == null
                ? "NULL (OK)" : "N/A";
        String c2User = foundC2 != null && foundC2.getUser() == null
                ? "NULL (OK)" : "N/A";

        System.out.println("   C1 існує? " + (foundC1 != null) + ". User: " + c1User);
        System.out.println("   C2 існує? " + (foundC2 != null) + ". User: " + c2User);
    }

    private static void testCommentSmileCascade() {
        System.out.println("\n--- Сценарій 2: Comment та Smile (MERGE, NO PERSIST, NO REMOVE) ---");

        Smile existingSmile = new Smile("😊");
        smileDao.create(existingSmile);
        System.out.println("1. Створено Smile: " + existingSmile.getValue()
                + " (ID: " + existingSmile.getId() + ")");

        Comment newComment = new Comment();
        newComment.setContent("Коментар зі смайлом.");

        newComment.setSmiles(List.of(existingSmile));

        commentDao.create(newComment);
        System.out.println("2. Створено Comment: " + newComment.getId() + " з існуючим Smile.");

        commentDao.remove(newComment);
        System.out.println("3. Видалено Comment. Перевірка Smile...");

        Smile foundSmile = smileDao.get(existingSmile.getId());
        System.out.println("   Smile існує? " + (foundSmile != null) + ". Value: "
                + (foundSmile != null ? foundSmile.getValue() : "N/A"));

        if (foundSmile != null) {
            smileDao.remove(foundSmile);
        }
    }

    private static void testMessageDetailsCascade() {
        System.out.println("\n--- Сценарій 3: Message та MessageDetails (CascadeType.ALL) ---");

        MessageDetails details = new MessageDetails();
        details.setSender("Alice");
        details.setSentTime(LocalDateTime.now());

        Message message = new Message();
        message.setContent("Важливе повідомлення.");

        message.setMessageDetails(details);
        details.setMessage(message);

        messageDao.create(message);
        Long detailsId = details.getId();
        System.out.println("1. Створено Message та Details (через ALL). Message ID: "
                + message.getId() + ", Details ID: " + detailsId);

        messageDao.remove(message);
        System.out.println("2. Видалено Message. Перевірка Details...");

        MessageDetails foundDetails = messageDetailsDao.get(detailsId);
        System.out.println("   MessageDetails існує? " + (foundDetails != null)
                + (foundDetails == null ? " (OK)" : " (FAIL)"));
    }
}
