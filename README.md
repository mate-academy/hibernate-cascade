# Hibernate cascades

Oops... Looks like we started this task with TDD approach, but haven't finished. 
Your main task is to add required annotations for entities and implement all DAO methods.

Also, we are expecting you will have some practice with cascades. Please complete the following requirements:

1. There are two classes: Comment and Smile. One comment can have multiple smiles. 
Implement the `remove(Comment comment)` method. Smiles used in this comment should NOT be removed.
Add correct cascades. When you are creating a new comment you should use existed smiles 
(DON'T create a smile if it does not exist in the DB).

1. There are two classes: User and Comment. One user can have multiple comments. 
Implement the `remove(User user)` method. Remove user from the DB.
Comments left by this user should NOT be removed.
Add correct cascades. When you are creating a new user you should use also create comments 
(save each new comment in the DB).

1. There are two classes: Message and MessageDetails. One message can have many message details.
Implement the `remove(Message message)` method. MessageDetails also should be created and removed along with parent 
Message entity.

1. Implement all DAO interfaces (except GenericDao).

You can see how to implement the `getAll()` methods [here](https://www.tutorialspoint.com/hibernate/hibernate_query_language.htm)

#### [Try to avoid these common mistakes while solving task](https://mate-academy.github.io/jv-program-common-mistakes/hibernate/cascades/cascades_checklist)
