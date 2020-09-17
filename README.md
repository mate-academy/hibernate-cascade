# hibernate-cascade

1. There are two classes: Comment and Smile. One comment can have multiple smiles. 
Implement the `remove(Comment comment)` method. Smiles used in this comment should NOT be removed.

1. There are two classes: User and Comment. One user can have multiple comments. 
Implement the `remove(User user)` method. Do not remove the user from DB. Mark him as removed. 
Comments left by this user should NOT be removed.

1. There are two classes: Message and MessageDetails. One message can have one message details.
Implement the `remove(Message message)` method. MessageDetails also should be removed.

1. Implement all DAO interfaces (except GenericDao)
