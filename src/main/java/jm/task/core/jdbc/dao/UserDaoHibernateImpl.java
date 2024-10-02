package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.ArrayList;
import java.util.List;

public class UserDaoHibernateImpl implements UserDao {
    private final SessionFactory sf = Util.getSessionFactory();

    public UserDaoHibernateImpl() {

    }


    @Override
    public void createUsersTable() {
        try (Session session = sf.openSession()) {
            session.beginTransaction();
            session.createSQLQuery("create table if not exists users (id int auto_increment primary key, " +
                    "name  varchar(45), lastname varchar(45), age tinyint)").executeUpdate();
            session.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("Failed to create users table");
        }
    }

    @Override
    public void dropUsersTable() {
        try (Session session = sf.openSession()) {
            session.beginTransaction();
            session.createSQLQuery("drop table if exists users").executeUpdate();
            session.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("Failed to drop users table");
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        Transaction trans = null;
        try (Session session = sf.openSession()) {
            trans = session.beginTransaction();
            session.createSQLQuery("insert into users (name, lastname, age) values (:name, :lastname, :age)")
                    .setParameter("name", name).setParameter("lastname", lastName)
                    .setParameter("age", age).executeUpdate();
            trans.commit();
            System.out.println("user " + name + " created successfully");
        } catch (Exception e) {
            if (trans != null) {
                trans.rollback();
            }
            System.out.println("Failed to save user, rolling back");
        }
    }

    @Override
    public void removeUserById(long id) {
        Transaction trans = null;
        try (Session session = sf.openSession()) {
            trans = session.beginTransaction();
            session.createSQLQuery("delete from users where id = :id").setParameter("id", id).executeUpdate();
            trans.commit();
        } catch (Exception e) {
            if (trans != null) {
                trans.rollback();
            }
            System.out.println("Failed to remove user, rolling back");
        }
    }

    @Override
    public List<User> getAllUsers() {
        List<User> users = new ArrayList<User>();
        try (Session session = sf.openSession()) {
            session.beginTransaction();
            List<Object[]> list = session.createSQLQuery("select * from users").list();
            for (Object[] row : list) {
                User user = new User();
                user.setId(((Integer) row[0]).longValue());
                user.setName((String) row[1]);
                user.setLastName((String) row[2]);
                user.setAge((Byte) row[3]);
                users.add(user);
            }
        } catch (Exception e) {
            System.out.println("Failed to get all users");
        }
        return users;
    }

    @Override
    public void cleanUsersTable() {
        try (Session session = sf.openSession()) {
            session.beginTransaction();
            session.createSQLQuery("delete from users").executeUpdate();
            session.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("Failed to clean users table");
        }
    }
}
