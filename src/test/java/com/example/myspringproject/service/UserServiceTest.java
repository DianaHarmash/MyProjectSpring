package com.example.myspringproject.service;


import com.example.myspringproject.model.UserEntity;
import com.example.myspringproject.repository.UserRepository;
import org.junit.*;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.postgresql.jdbc.PgConnection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.sql.*;
import java.util.Optional;
import java.util.stream.Stream;

import org.postgresql.Driver;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserServiceTest {
    private static final String url = "jdbc:postgresql://localhost:5432/postgres";
    private static final String user = "postgres";
    private static final String password = "qwerty1234";
    private static Connection connection;

    @Autowired
    private UserRepository usersRepo;
    @Autowired
    private UserService userService;


    private final String TestUserLogin =  "testLogin123456789";

    @BeforeClass
    public static void setUpJDBC(){
        Class<Driver> SQLClass = org.postgresql.Driver.class;
        try {
            connection = DriverManager.getConnection(url, user, password);
        } catch (Exception e){}
    }

    @Before
    public void setUpTest(){
        Assert.assertNotNull(userService);
        Assert.assertNotNull(usersRepo);
        Assert.assertNotNull(connection);
        userService.userRepository = usersRepo;
    }

    @After
    public void tearDown(){
        Optional<UserEntity> foundUser = userService.findUser(TestUserLogin);
        if (foundUser.isPresent()){
            userService.userRepository.delete(foundUser.get());
        }

    }

    private Optional<UserEntity> CreateUser() {
        return userService.saveUser(TestUserLogin, "0987654321");
    }
    private int getCount(ResultSet set){
        int count = 0;
        try {
            while (set.next()) count++;
        }catch (Exception e){
            e.printStackTrace();
        }
        return count;
    }

    @Test
    public void saveUserTest() {
        try {
            Optional<UserEntity> foundUser = CreateUser();
            Assert.assertTrue(foundUser.isPresent());

            PreparedStatement statement = connection.prepareStatement("select * from user_entity where login=?");
            statement.setString(1, TestUserLogin);
            ResultSet set = statement.executeQuery();
            Assert.assertNotNull(set);
            Assert.assertEquals(getCount(set), 1);
        } catch (SQLException e) {
            e.printStackTrace();
            Assert.fail();
        }

    }
    @Test
    public void deleteUserTest() {
        try {
            Optional<UserEntity> foundUser = CreateUser();
            Assert.assertTrue(foundUser.isPresent());

            userService.userRepository.delete(foundUser.get());
            foundUser = userService.findUser(TestUserLogin);
            Assert.assertFalse(foundUser.isPresent());

            PreparedStatement statement = connection.prepareStatement("select * from user_entity where login=?");
            statement.setString(1, TestUserLogin);
            ResultSet set = statement.executeQuery();
            Assert.assertNotNull(set);
            Assert.assertEquals(getCount(set), 0);
        } catch (SQLException e) {
            e.printStackTrace();
            Assert.fail();
        }

    }
}
