# Hibernate Cascades

Oops... It seems we initiated this task with the TDD approach but didn't finish it. Your primary task is to add the necessary annotations for entities and implement all DAO methods.

Moreover, we expect you to gain some practice with cascades. Please fulfill the following requirements:

1. There are two classes: Comment and Smile. One comment can have multiple smiles. Implement the `remove(Comment comment)` method. Smiles used in this comment should NOT be removed. Add the correct cascades. When creating a new comment, use existing smiles (DO NOT create a smile if it does not exist in the DB).

2. There are two classes: User and Comment. One user can have multiple comments. Implement the `remove(User user)` method. Remove the user from the DB. Comments left by this user should NOT be removed. Add the correct cascades. When creating a new user, you should also create comments (save each new comment in the DB).

3. There are two classes: Message and MessageDetails. One message can have only one message detail. Implement the `remove(Message message)` method. MessageDetails should also be created and removed along with the parent Message entity.

4. Implement all DAO interfaces (except GenericDao).

#### [Try to avoid these common mistakes while solving task](./checklist.md)
