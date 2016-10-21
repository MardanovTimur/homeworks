package ru.itis.inform.services;

import ru.itis.inform.dao.RoleDao;
import ru.itis.inform.dao.RoleDaoImpl;
import ru.itis.inform.models.Role;

import java.util.List;

/**
 * Created by Тимур on 16.10.2016.
 */
public class RoleServicesImpl implements RoleServices {
    private RoleDao roleDao;

    public RoleServicesImpl() {
        roleDao = new RoleDaoImpl();
    }

    public Role getRole(String name) {
       return roleDao.getRole(name);
    }

    public List<Role> getAllRoles(){
        return null;
    }
}