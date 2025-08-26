package jm.task.core.jdbc.service;

import jm.task.core.jdbc.dao.UserDao;
import jm.task.core.jdbc.dao.UserDaoHibernateImpl;
import jm.task.core.jdbc.model.User;

import java.sql.SQLException;
import java.util.List;

public class UserServiceImpl implements UserService {
    private UserDao userDao;

    public UserServiceImpl(UserDao userDao) {
        this.userDao = userDao;
    }

    public UserServiceImpl() {
        this.userDao = new UserDaoHibernateImpl();
    }

    @Override
    public void createUsersTable() throws SQLException {
        try {
            userDao.createUsersTable();
        } catch (Exception e) {
            throw new SQLException("Error creating users table", e);
        }
    }

    @Override
    public void dropUsersTable() throws SQLException {
        try {
            userDao.dropUsersTable();
        } catch (Exception e) {
            throw new SQLException("Error dropping users table", e);
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) throws SQLException {
        try {
            userDao.saveUser(name, lastName, age);
        } catch (Exception e) {
            throw new SQLException("Error saving user", e);
        }
    }

    @Override
    public void removeUserById(long id) throws SQLException {
        try {
            userDao.removeUserById(id);
        } catch (Exception e) {
            throw new SQLException("Error removing user by id", e);
        }
    }

    @Override
    public List<User> getAllUsers() throws SQLException {
        try {
            return userDao.getAllUsers();
        } catch (Exception e) {
            throw new SQLException("Error getting all users", e);
        }
    }

    @Override
    public void cleanUsersTable() throws SQLException {
        try {
            userDao.cleanUsersTable();
        } catch (Exception e) {
            throw new SQLException("Error cleaning users table", e);
        }
    }
}