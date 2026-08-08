# Hibernate Cascades

Oops... It seems we initiated this task with the TDD approach but didn't finish it. 
Your primary task is to add the necessary annotations for entities and implement all DAO methods.

Moreover, we expect you to gain some practice with cascades. Please fulfill the following requirements:

1. There are two classes: Comment and Smile. One comment can have multiple smiles.
2. Implement the `remove(Comment comment)` method. 
3. Smiles used in this comment should NOT be removed. Add the correct cascades. 
4. When creating a new comment, use existing smiles (DO NOT create a smile if it does not exist in the DB).
5. There are two classes: User and Comment. One user can have multiple comments.
6. Implement the `remove(User user)` method. Remove the user from the DB.
7. Comments left by this user should NOT be removed. Add the correct cascades.
8. When creating a new user, you should also create comments (save each new comment in the DB).

9. There are two classes: Message and MessageDetails. One message can have only one message detail. 
10. Implement the `remove(Message message)` method.
11. MessageDetails should also be created and removed along with the parent Message entity.

12. Implement all DAO interfaces (except GenericDao).

#### [Try to avoid these common mistakes while solving task](./checklist.md)
