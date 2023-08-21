# Common mistakes

* Think if you need to use JPA-specific or Hibernate-specific methods to make cascades work.
* In all of your dao classes you have `sessionFactory` object (which is passed via constructor). 
You should use it for the tests to pass. Use `HibernateUtil.getSessionFactory()`
ONLY if you want to run your solution in the main method.
* You don't need to create your own exception, let's throw `RuntimeException` in the catch block. 
* Remember to add `catch` blocks for operations of all types on DAO layer.  
* You need to open a transaction not only when creating entities but when removing as well.
* Don't just copy-paste code from other dao (make sure your exception messages and variable names are correct for particular class.
* Better use specific CascadeType (PERSIST / MERGE / REMOVE / REFRESH) instead of general (ALL)
