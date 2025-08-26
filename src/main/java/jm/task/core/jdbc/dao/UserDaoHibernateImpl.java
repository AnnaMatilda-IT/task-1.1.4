package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.ArrayList;
import java.util.List;

public class UserDaoHibernateImpl implements UserDao {
        public UserDaoHibernateImpl() {}

        @Override
        public void createUsersTable() {
            Session session = Util.openSession();
            Transaction transaction = null;
            try {
                transaction = session.beginTransaction();
                String sql = "CREATE TABLE IF NOT EXISTS users (" +
                        "id BIGINT PRIMARY KEY AUTO_INCREMENT, " +
                        "name VARCHAR(45), " +
                        "lastName VARCHAR(100), " +
                        "age TINYINT)";
                session.createNativeQuery(sql).executeUpdate();
                transaction.commit();
                System.out.println("Таблица создана успешно с помощью Hibernate!");
            } catch (Exception e) {
                if (transaction != null) transaction.rollback();
                System.out.println("Не удалось создать таблицу с помощью Hibernate!");
            } finally {
                session.close();
            }
        }

        @Override
        public void dropUsersTable() {
            Session session = Util.openSession();
            Transaction transaction = null;
            try {
                transaction = session.beginTransaction();
                String sql = "DROP TABLE IF EXISTS users";
                session.createNativeQuery(sql).executeUpdate();
                transaction.commit();
                System.out.println("Таблица удалена успешно с помощью Hibernate!");
            } catch (Exception e) {
                if (transaction != null) transaction.rollback();
                System.out.println("Не удалось удалить таблицу с помощью Hibernate!");
            } finally {
                session.close();
            }
        }

        @Override
        public void saveUser(String name, String lastName, byte age) {
            Session session = Util.openSession();
            Transaction transaction = null;
            try {
                transaction = session.beginTransaction();
                User user = new User(name, lastName, age);
                session.save(user);
                transaction.commit();
                System.out.println("Пользователь добавлен успешно с помощью Hibernate!");
            } catch (Exception e) {
                if (transaction != null) transaction.rollback();
                System.out.println("Не удалось добавить пользователя с помощью Hibernate!");
            } finally {
                session.close();
            }
        }

        @Override
        public void removeUserById(long id) {
            Session session = Util.openSession();
            Transaction transaction = null;
            try {
                transaction = session.beginTransaction();
                User user = session.get(User.class, id);
                if (user != null) {
                    session.delete(user);
                    System.out.println("Пользователь с ID=" + id + " удален успешно с помощью Hibernate!");
                } else {
                    System.out.println("Пользователь с ID=" + id + " не найден!");
                }
                transaction.commit();
            } catch (Exception e) {
                if (transaction != null) transaction.rollback();
                System.out.println("Не удалось удалить пользователя с помощью Hibernate!");
            } finally {
                session.close();
            }
        }

        @Override
        public List<User> getAllUsers() {
            Session session = Util.openSession();
            Transaction transaction = null;
            List<User> users = new ArrayList<>();
            try {
                transaction = session.beginTransaction();
                users = session.createQuery("FROM User", User.class).getResultList();
                transaction.commit();
                System.out.println("Данные получены успешно с помощью Hibernate! Найдено " + users.size() + " пользователей.");
            } catch (Exception e) {
                if (transaction != null) transaction.rollback();
                System.out.println("Не удалось получить данные с помощью Hibernate!");
            } finally {
                session.close();
            }
            return users;
        }

        @Override
        public void cleanUsersTable() {
            Session session = Util.openSession();
            Transaction transaction = null;
            try {
                transaction = session.beginTransaction();
                String sql = "TRUNCATE TABLE users";
                session.createNativeQuery(sql).executeUpdate();
                transaction.commit();
                System.out.println("Таблица очищена успешно с помощью Hibernate!");
            } catch (Exception e) {
                if (transaction != null) transaction.rollback();
                System.out.println("Не удалось очистить таблицу с помощью Hibernate!");
            } finally {
                session.close();
            }
        }
    }

