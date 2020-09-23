package core.basesyntax.dao.impl;

import core.basesyntax.AbstractTest;
import core.basesyntax.dao.CommentDao;
import core.basesyntax.dao.UserDao;
import core.basesyntax.model.Comment;
import core.basesyntax.model.Smile;
import core.basesyntax.model.User;
import java.util.List;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class UserDaoImplTest extends AbstractTest {
    private UserDao userDao;

    @Before
    public void setup() {
        userDao = new UserDaoImpl(getSessionFactory());
    }

    @Override
    protected Class<?>[] entities() {
        return new Class[] {
                User.class,
                Comment.class,
                Smile.class
        };
    }

    @Test
    public void createUser_NoComments_Ok() {
        User user = new User();
        user.setUsername("bob");
        User actual = userDao.create(user);
        Assert.assertNotNull(actual);
        Assert.assertNotNull(actual.getId());
        Assert.assertNotNull(actual.getUsername());
        Assert.assertEquals(1L, actual.getId().longValue());
        Assert.assertEquals("bob", actual.getUsername());
    }

    @Test
    public void getById_NoComments_Ok() {
        User user = new User();
        user.setUsername("bob");
        userDao.create(user);

        User actual = userDao.get(1L);
        Assert.assertNotNull(actual);
        Assert.assertNotNull(actual.getId());
        Assert.assertNotNull(actual.getUsername());
        Assert.assertEquals(1L, actual.getId().longValue());
        Assert.assertEquals("bob", actual.getUsername());
    }

    @Test
    public void getById_WithComments_Ok() {
        Comment okComment = new Comment();
        okComment.setContent("This comment is ok!");

        User user = new User();
        user.setUsername("bob");
        user.setComments(List.of(okComment));
        userDao.create(user);

        // Verify all data from DB
        User actual = userDao.get(1L);
        Assert.assertNotNull(actual);
        Assert.assertNotNull(actual.getId());
        Assert.assertNotNull(actual.getUsername());
        Assert.assertEquals(1L, actual.getId().longValue());
        Assert.assertEquals("bob", actual.getUsername());
        Assert.assertNotNull(actual.getComments());
        Assert.assertNotNull(actual.getComments().get(0));
        Assert.assertNotNull(actual.getComments().get(0).getId());
        Assert.assertEquals(1L, actual.getComments().get(0).getId().longValue());
        Assert.assertEquals("This comment is ok!", actual.getComments().get(0).getContent());
    }

    @Test
    public void getAll_NoComments_Ok() {
        User bob = new User();
        bob.setUsername("Bob");
        userDao.create(bob);

        User alice = new User();
        alice.setUsername("Alice");
        userDao.create(alice);

        List<User> allUsers = userDao.getAll();
        Assert.assertNotNull("Check you have implemented the `getAll()` method " +
                "in the UserDaoImpl class", allUsers);
        Assert.assertFalse(allUsers.isEmpty());
        Assert.assertEquals(2, allUsers.size());

        // Verify Bob
        Assert.assertNotNull(allUsers.get(0));
        Assert.assertNotNull(allUsers.get(0).getId());
        Assert.assertEquals(1L, allUsers.get(0).getId().longValue());
        Assert.assertEquals("Bob", allUsers.get(0).getUsername());

        // Verify Alice
        Assert.assertNotNull(allUsers.get(1));
        Assert.assertNotNull(allUsers.get(1).getId());
        Assert.assertEquals(2L, allUsers.get(1).getId().longValue());
        Assert.assertEquals("Alice", allUsers.get(1).getUsername());
    }

    @Test
    public void remove_NoComments_Ok() {
        User bob = new User();
        bob.setUsername("Bob");
        userDao.create(bob);

        User actualBob = userDao.get(1L);
        Assert.assertNotNull(actualBob);
        Assert.assertNotNull(actualBob.getId());
        Assert.assertNotNull(actualBob.getUsername());
        Assert.assertEquals(1L, actualBob.getId().longValue());
        Assert.assertEquals("Bob", actualBob.getUsername());

        User alice = new User();
        alice.setUsername("Alice");
        userDao.create(alice);

        User actualAlice = userDao.get(2L);
        Assert.assertNotNull(actualAlice);
        Assert.assertNotNull(actualAlice.getId());
        Assert.assertNotNull(actualAlice.getUsername());
        Assert.assertEquals(2L, actualAlice.getId().longValue());
        Assert.assertEquals("Alice", actualAlice.getUsername());

        List<User> allUsersBeforeRemove = userDao.getAll();
        Assert.assertNotNull("Check you have implemented the `getAll()` method " +
                "in the UserDaoImpl class", allUsersBeforeRemove);
        Assert.assertFalse(allUsersBeforeRemove.isEmpty());
        Assert.assertEquals("There are should be two users in the DB " +
                "before one user will be deleted", 2, allUsersBeforeRemove.size());

        // verify user's usernames
        Assert.assertEquals("Bob", allUsersBeforeRemove.get(0).getUsername());
        Assert.assertEquals("Alice", allUsersBeforeRemove.get(1).getUsername());

        // Remove Bob user from DB
        userDao.remove(bob);

        List<User> allUsers = userDao.getAll();
        Assert.assertNotNull("Check you have implemented the `getAll()` method " +
                "in the UserDaoImpl class", allUsers);
        Assert.assertFalse(allUsers.isEmpty());
        Assert.assertEquals("There are should be only one user in the DB " +
                "after one user was deleted. Verify you have implemented `remove` method " +
                "in the UserDaoImpl class",1, allUsers.size());

        // Verify Alice are in DB
        Assert.assertNotNull(allUsers.get(0));
        Assert.assertNotNull(allUsers.get(0).getId());
        Assert.assertEquals(2L, allUsers.get(0).getId().longValue());
        Assert.assertEquals("Alice", allUsers.get(0).getUsername());
    }

    @Test
    public void remove_WithComments_Ok() {
        CommentDao commentDao = new CommentDaoImpl(getSessionFactory());

        // verify no comments in the DB
        List<Comment> initialCommentsInDB = commentDao.getAll();
        Assert.assertNotNull(initialCommentsInDB);
        Assert.assertTrue(initialCommentsInDB.isEmpty());

        User bob = new User();
        bob.setUsername("Bob");

        Comment okCommentByBob = new Comment();
        okCommentByBob.setContent("This comment is ok!");

        Comment goodCommentByBob = new Comment();
        goodCommentByBob.setContent("This comment is good!");

        bob.setComments(List.of(okCommentByBob, goodCommentByBob));
        userDao.create(bob);

        User alice = new User();
        alice.setUsername("Alice");

        Comment notBadCommentByAlice = new Comment();
        notBadCommentByAlice.setContent("This comment is not bad!");

        Comment awesomeCommentByAlice = new Comment();
        awesomeCommentByAlice.setContent("This comment is awesome!");

        alice.setComments(List.of(notBadCommentByAlice, awesomeCommentByAlice));
        userDao.create(alice);

        // Verify users were created along with comments
        List<User> allUsersBeforeRemove = userDao.getAll();
        Assert.assertNotNull("Check you have implemented the `getAll()` method " +
                "in the UserDaoImpl class", allUsersBeforeRemove);
        Assert.assertFalse(allUsersBeforeRemove.isEmpty());
        Assert.assertEquals("There are should be two users in the DB " +
                "before one user will be deleted", 2, allUsersBeforeRemove.size());

        // verify user's usernames
        Assert.assertEquals("Bob", allUsersBeforeRemove.get(0).getUsername());
        Assert.assertEquals("Alice", allUsersBeforeRemove.get(1).getUsername());

        // verify comments

        List<Comment> allCommentsBeforeRemove = commentDao.getAll();
        Assert.assertNotNull(allCommentsBeforeRemove);
        Assert.assertEquals(4, allCommentsBeforeRemove.size());

        Assert.assertEquals(1L, allUsersBeforeRemove.get(0).getComments().get(0).getId().longValue());
        Assert.assertEquals("This comment is ok!", allUsersBeforeRemove.get(0).getComments().get(0).getContent());
        Assert.assertEquals(2L, allUsersBeforeRemove.get(0).getComments().get(1).getId().longValue());
        Assert.assertEquals("This comment is good!", allUsersBeforeRemove.get(0).getComments().get(1).getContent());

        Assert.assertEquals(3L, allUsersBeforeRemove.get(1).getComments().get(0).getId().longValue());
        Assert.assertEquals("This comment is not bad!", allUsersBeforeRemove.get(1).getComments().get(0).getContent());
        Assert.assertEquals(4L, allUsersBeforeRemove.get(1).getComments().get(1).getId().longValue());
        Assert.assertEquals("This comment is awesome!", allUsersBeforeRemove.get(1).getComments().get(1).getContent());

        userDao.remove(bob);
        userDao.remove(alice);

        // Verify both users were removed
        List<User> usersAfterRemove = userDao.getAll();
        Assert.assertNotNull(usersAfterRemove);
        Assert.assertTrue(usersAfterRemove.isEmpty());

        // Verify comments are still in DB after users were removed
        List<Comment> actualComments = commentDao.getAll();
        Assert.assertNotNull(actualComments);
        Assert.assertEquals(4, actualComments.size());

        Assert.assertEquals(1L, actualComments.get(0).getId().longValue());
        Assert.assertEquals("This comment is ok!", actualComments.get(0).getContent());
        Assert.assertEquals(2L, actualComments.get(1).getId().longValue());
        Assert.assertEquals("This comment is good!", actualComments.get(1).getContent());

        Assert.assertEquals(3L, actualComments.get(2).getId().longValue());
        Assert.assertEquals("This comment is not bad!", actualComments.get(2).getContent());
        Assert.assertEquals(4L, actualComments.get(3).getId().longValue());
        Assert.assertEquals("This comment is awesome!", actualComments.get(3).getContent());
    }
}
