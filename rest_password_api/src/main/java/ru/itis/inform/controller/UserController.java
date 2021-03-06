package ru.itis.inform.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.itis.inform.converter.UsersConverter;
import ru.itis.inform.dao.UsersDao;
import ru.itis.inform.dto.UserDto;
import ru.itis.inform.model.User;
import ru.itis.inform.model.UserForRegister;
import ru.itis.inform.service.UserService;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.EmptyStackException;

/**
 * Created by timur on 30.03.17.
 */
@RestController
@CrossOrigin(origins = "http://127.0.0.1:7072", maxAge = 3600)
public class UserController {
    @Autowired
    UsersDao usersDao;

    @Autowired
    UserService userService;

    @RequestMapping(value = "/users", method = RequestMethod.GET)
    public ResponseEntity<String> getAllUsers() {
        HttpHeaders httpHeaders = getHeaders();
        ArrayList<UserDto> arrayList = (ArrayList<UserDto>) userService.findAll();
        ObjectMapper objectMapper = new ObjectMapper();
        String listAsJson = null;
        try {
            listAsJson = objectMapper.writeValueAsString(arrayList);
        } catch (JsonProcessingException e) {
            return new ResponseEntity<String>("{\"error\":\"json\"}", httpHeaders, HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<String>(listAsJson, httpHeaders, HttpStatus.FOUND);
    }

    @PostMapping(value = "/users")
    public ResponseEntity<String> registration(@RequestBody String userValue) {
        HttpHeaders headers = getHeaders();
        ObjectMapper objectMapper = new ObjectMapper();
        String userAsJSON = null;
        try {
            UserForRegister user = objectMapper.readValue(userValue, UserForRegister.class);
            if (user.getPassword().equals(user.getPassword_again())) {
                User userA = usersDao.save(new User(user.getName(), user.getUsername(), user.getPassword(), "", new ArrayList<>(), new ArrayList<>()));
                UserDto userDto = new UserDto(userA.getId(), userA.getName(), userA.getUsername());
                userAsJSON = objectMapper.writeValueAsString(userDto);
            } else {
                int i = 1 / 0;
            }
        } catch (Exception e) {
            return new ResponseEntity<String>("{\"operation\":\"error\"}", headers, HttpStatus.OK);
        }
        return new ResponseEntity<String>(userAsJSON, headers, HttpStatus.OK);
    }

    @RequestMapping(value = "/users", method = RequestMethod.PUT)
    public ResponseEntity<String> update(@RequestBody String userValue) {
        HttpHeaders headers = getHeaders();
        ObjectMapper objectMapper = new ObjectMapper();
        String userAsJSON;
        try {
            User user = objectMapper.readValue(userValue, User.class);
            UserDto userDto = userService.update(user);
            userAsJSON = objectMapper.writeValueAsString(userDto);
        } catch (IOException e) {
            return new ResponseEntity<String>("{\"operation\":\"error\"}", headers, HttpStatus.CONFLICT);
        }
        return new ResponseEntity<String>(userAsJSON, headers, HttpStatus.OK);
    }

    @RequestMapping(value = "/users/{user_id}", method = RequestMethod.DELETE)
    public ResponseEntity<String> delete(@PathVariable("user_id") int userId) {
        HttpHeaders headers = getHeaders();
        ObjectMapper objectMapper = new ObjectMapper();
        String userAsJSON;
        userService.delete(userId);
        return new ResponseEntity<String>("{\"status\":\"" + userId + " deleted\"}", headers, HttpStatus.OK);
    }


    @RequestMapping(value = "/users/{username}", method = RequestMethod.GET)
    public ResponseEntity<UserDto> getUserByName(@PathVariable("username") String username, @RequestParam("parameter") String parameter) {
        HttpHeaders httpHeaders = getHeaders();
        if (parameter.equals("username")) {
            String postAsJSON = null;
            UserDto user = UsersConverter.convertToUserDto(usersDao.findByLogin(username));
            return new ResponseEntity<UserDto>(user, httpHeaders, HttpStatus.OK);
        } else {
            if (parameter.equals("id")) {
                String postAsJSON = null;
                UserDto user = userService.get(Integer.parseInt(username));
                return new ResponseEntity<UserDto>(user, httpHeaders, HttpStatus.OK);
            }
        }
        return new ResponseEntity<UserDto>(null, httpHeaders, HttpStatus.NOT_FOUND);
    }


    @PostMapping(value = "/users/login")
    public ResponseEntity<String> login(@RequestHeader("login") String login, @RequestHeader("password") String password,
                                        HttpServletResponse response) {
        User user = usersDao.findByUsername(login);
        HttpHeaders headers = getHeaders();
        if (user != null) {
            String token = "";
            try {
                token = userService.login(password, login);
            } catch (Exception e) {
                return new ResponseEntity<String>("{\"login\":\"failed\"}", headers, HttpStatus.OK);
            }
            headers.add("Auth-Token", token);
            return new ResponseEntity<String>("{\"login\":\"success\"}", headers, HttpStatus.OK);
        } else {
            return new ResponseEntity<String>("{\"login\":\"failed\"}", headers, HttpStatus.OK);
        }
    }

    public static HttpHeaders getHeaders() {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Access-Control-Allow-Headers", "x-requested-with, Content-Type, x-requested-with,accept,Origin,Access-Control-Request-Method,Access-Control-Request-Headers");
        httpHeaders.add("Access-Control-Allow-Origin", "*");
        httpHeaders.add("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE, PUT");
        httpHeaders.add("Access-Control-Max-Age", "3600");
        httpHeaders.add("Access-Control-Allow-Headers", "x-requested-with");
        httpHeaders.add("Content-Type", "application/json");
        return httpHeaders;
    }
}