package ru.itis.inform;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;


public class UsersDaoImpl implements UsersDao {
    File file = new File("C:\\Users\\Тимур\\Desktop\\11-502_Mardanov_homeworks\\UsersDao\\src\\main\\java\\ru\\itis\\inform\\list.txt");
    Scanner sc = new Scanner(file);
    PrintWriter pw = new PrintWriter(file);

    public UsersDaoImpl() throws FileNotFoundException {
    }

    public List<User> findAll() throws FileNotFoundException {
        List<User> userList = new LinkedList<User>();
        String users = sc.nextLine();
        String user[] = users.split(" ");
        User newUser = new User();
        // guava splitter
        for (int i = 0; i < user.length / 3; i++) {
            newUser.setName(user[i * 3]);
            newUser.setId(user[i * 3 + 1]);
            newUser.setPassword(user[i * 3 + 2]);
            userList.add(newUser);
            newUser = new User();
        }
        return userList;
    }

    public void save(User user) throws FileNotFoundException {
        int hashCode = user.hashCode();
        String information = user.getName() + " " + hashCode + " " + user.getPassword() + " ";
        pw.print(information);
    }

    public User find(String string, int definition) throws FileNotFoundException {
        String users = sc.nextLine();
        String user[] = users.split(" ");
        String id_s;
        id_s = "";
        id_s += string;
        User newUser = null;
        for (int i = 0; i < user.length / 3; i++) {
            if (id_s.equals(user[i * 3 + definition])) {
                newUser = new User(user[i*3],user[i*3+1],user[i*3+2]);
            }
        }
        if (newUser != null) {
            return newUser;
        } else
            return null;
    }

    public void closePW() {
        this.pw.close();
    }

    public void closeSC() {
        this.sc.close();
    }
}


