package com.example.myspringproject.service;

import com.example.myspringproject.config.Encoder;
import com.example.myspringproject.model.Role;
import com.example.myspringproject.model.UserEntity;
import com.example.myspringproject.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    /**
     * Method which gets a login and a password
     * of some user, trying to sign in, and checks
     * if the user is in a database or not.
     *
     * @param login of a user
     * @param password of a user
     * @return user or null
     */

    public Optional<UserEntity> findUser(String login, String password) {
        return userRepository.findUserEntityByLoginAndPassword(login, password);
    }

    /**
     * Method which gets a login of some user
     * and checks if the user is in a database.
     * Override of
     * @see #findUser(String, String).
     *
     * @param login of some user
     * @return user or null.
     */

    public Optional<UserEntity> findUser(String login) {
        return userRepository.findUserEntityByLogin(login);
    }

    /**
     * Method wich checks if there is any user in a database. If it is,
     * the user is saved as a user, otherwise it is saved as an admin.
     *
     * @param login of a user
     * @param password of a user
     * @return a user or null
     */

    @Transactional
    public Optional<UserEntity> saveUser(String login, String password) {
        long quantityOfUsers = getNumberOfRows();
        UserEntity user = new UserEntity(login, new Encoder().encode(password), (quantityOfUsers == 0 ? Role.ROLE_ADMIN : Role.ROLE_USER));
        userRepository.save(user);
        return Optional.ofNullable(user);
    }

    /**
     * Method which gets a number of users in
     * database.
     *
     * @return number of users in database.
     */

    public long getNumberOfRows() {
        return userRepository.count();
    }

    /**
     * Method which looks for a user with the id.
     *
     * @param id of a user.
     * @return a user or throw an exception
     */
    public UserEntity findUserById(long id) {
        return userRepository.findById(id).orElseGet(() -> null);
    }

}
