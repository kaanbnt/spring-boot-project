package com.example.application.backend.service;

import com.example.application.backend.entity.User;
import com.example.application.backend.repository.UserRepository;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * UserService
 *
 * @author Kaan BİNAT
 * @since 5.230.0
 */
@Service
public class UserService {
    private static final Logger LOGGER = Logger.getLogger(ContactService.class.getName());
    private UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public User findUserByUserNameAndPassword(String userName, String password) {
        return userRepository.findUserByUserNameAndPassword(userName, password);
    }

    public long count() {
        return userRepository.count();
    }

    public void delete(User user) {
        userRepository.delete(user);
    }

    public void save(User user) {
        if (user == null) {
            LOGGER.log(Level.SEVERE,
                    "User is null. Are you sure you have connected your form to the application?");
            return;
        }
        userRepository.save(user);
    }

    @PostConstruct
    public void populateTestData() {
        if (userRepository.count() == 0) {
            List<User> userList = userRepository.findAll();
            userRepository.saveAll(
                    Stream.of("kbinat 123", "egursoy 321")
                            .map(name -> {
                                String[] split = name.split(" ");
                                User user = new User();
                                user.setUserName(split[0]);
                                user.setPassWord(split[1]);
                                return user;
                            }).collect(Collectors.toList()));
        }
    }
}
