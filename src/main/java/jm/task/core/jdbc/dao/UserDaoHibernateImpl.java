package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.List;

public class UserDaoHibernateImpl implements UserDao {
    private final static SessionFactory sessionFactory = Util.getSessionFactory();
    public UserDaoHibernateImpl() {

    }


    @Override
    public void createUsersTable() {
        try (Session session = sessionFactory.openSession()){
            Transaction transaction = session.beginTransaction();
            Query query = session
                    .createSQLQuery("CREATE TABLE IF NOT EXISTS users (id BIGINT NOT NULL PRIMARY KEY AUTO_INCREMENT, name VARCHAR(255), last_name VARCHAR(255), age INT)")
                    .addEntity(User.class);
            query.executeUpdate();
            transaction.commit();
        }
    }

    @Override
    public void dropUsersTable() {
        try (Session session = sessionFactory.openSession()){
            Transaction transaction = session.beginTransaction();
            Query query = session
                    .createSQLQuery("DROP TABLE IF EXISTS Users")
                    .addEntity(User.class);
            query.executeUpdate();
            transaction.commit();
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        try (Session session = sessionFactory.openSession()){
            Transaction transaction = session.beginTransaction();
            User user = new User(name, lastName, age);
            session.save(user);
            transaction.commit();
        }
    }

    @Override
    public void removeUserById(long id) {
        try (Session session = sessionFactory.openSession()){
            Transaction transaction = session.beginTransaction();
            Query query = session.createSQLQuery("DELETE FROM users WHERE id = :id");
            query.setLong("id", id);
            query.executeUpdate();
            transaction.commit();
        }
    }

    @Override
    public List<User> getAllUsers() {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("from User", User.class)
                    .getResultList();
        }
    }

    @Override
    public void cleanUsersTable() {
        try (Session session = sessionFactory.openSession()){
            Transaction transaction = session.beginTransaction();
            Query query = session.createSQLQuery("Truncate table users");
            query.executeUpdate();
            transaction.commit();
        }
    }
}
