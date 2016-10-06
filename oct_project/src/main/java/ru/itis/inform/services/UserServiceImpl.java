package ru.itis.inform.services;

import ru.itis.inform.dao.UserDao;
import ru.itis.inform.dao.UserDaoImpl;
import ru.itis.inform.errors.Error;
import ru.itis.inform.messages.Message;
import ru.itis.inform.models.User;
import ru.itis.inform.utils.Hash;
import ru.itis.inform.verifiers.UserVerify;

import java.sql.SQLException;
import java.util.List;

public class UserServiceImpl implements UserService {
    private UserDao userDao = null;
    private Error error = null;
    private Message message = null;

    public UserServiceImpl() {
        this.userDao = new UserDaoImpl();
    }

    public void add(String name, String login, String password, String passwordAgain, boolean is_admin) {
        error = null;
        message = null;
        //Check to size (2<x<30)
        if (defaultSize(name) && defaultSize(login) && defaultSize(password) && defaultSize(passwordAgain)) {
            if (password.equals(passwordAgain)) {
                User newUser = null;
                try {
                    password = Hash.getMd5Apache(password);
                    newUser = new User(name, login, password, is_admin);
                    if (UserVerify.checkUserInBD(userDao, login) != null) {
                        userDao.addUser(newUser);
                        message = new Message("user_registration", " is registered.");
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            } else {
                error = new Error("wrong_password", "Passwords isnt equals!");
            }
        } else {
            error = new Error("wrong_size","Wrong word size.");
        }
    }

    public List<User> findAll() {
        return null;
    }

    public void delete(String id) {

    }

    public User find(String login) {
        error = null;
        message = null;
        if (UserVerify.checkUserInBD(userDao,login)!=null) {
            error = new Error("user_not_found", "User not found");
            return null;
        } else {
            message = new Message("user", "User is found");
            return userDao.findUser(login);
        }
    }

    public void changeRule(String id) {

    }

    public Error getErrors() {
        return error;
    }

    public Message getMessage() {
        return message;
    }

    private boolean defaultSize(String value) {
        return value.length() >= 2 && value.length() <= 30;
    }
}
