package ru.timur.itis.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.timur.itis.converter.UsersConverter;
import ru.timur.itis.dao.DataDao;
import ru.timur.itis.dao.UsersDao;
import ru.timur.itis.dto.UserDto;
import ru.timur.itis.model.Data;
import ru.timur.itis.model.User;
import ru.timur.itis.service.UserService;

import java.util.List;

/**
 * Created by timur on 30.03.17.
 */
@Component
public class UserServiceImpl implements UserService {
    @Autowired
    UsersDao usersDao;
    @Autowired
    DataDao dataDao;

    @Override
    public List<UserDto> findAll() {
        return usersDao.findAll();
    }

    @Override
    public UserDto get(int id) {
        return UsersConverter.convertToUserDto(usersDao.get(id));
    }

    @Override
    public int saveObject(User object) {
        return usersDao.saveObject(object);
    }

    @Override
    public void delete(int id) {
        usersDao.delete(id);
    }

    @Override
    public UserDto update(User object) {
        return UsersConverter.convertToUserDto(usersDao.update(object));
    }

    @Override
    public UserDto addData(User user, Data data) {
        return UsersConverter.convertToUserDto(usersDao.addData(user,data));
    }


    @Override
    public UserDto findByUsername(String name) {
        return UsersConverter.convertToUserDto(usersDao.findByUsername(name));
    }

    public UserDto getUserByName(String username) {
        return UsersConverter.convertToUserDto(usersDao.getUserByName(username));
    }

    public UserDto addDataForUser(User user, Data data) {
        user.getDataList().add(data);
        data.setUser(user);
        dataDao.saveObject(data);
        usersDao.update(user);
        return UsersConverter.convertToUserDto(user);
    }
}
