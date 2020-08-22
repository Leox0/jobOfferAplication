package pl.sda.jobOfferAplication.user.service;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import pl.sda.jobOfferAplication.user.entity.UserEntity;
import pl.sda.jobOfferAplication.user.exception.UserException;
import pl.sda.jobOfferAplication.user.model.UserInput;
import pl.sda.jobOfferAplication.user.model.UserOutput;
import pl.sda.jobOfferAplication.user.repository.UserRepository;


import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class shouldCreateUserCorrectly {

    @Autowired
    UserService userService;

    @Autowired
    UserRepository userRepository;

    @AfterEach
    void tearDown() {
        userRepository.deleteAll();
    }


    @Test
    public void shouldCreateUserCorrectly() throws UserException {

        //given
        UserInput userInput = new UserInput("Tomek", "Tomek12345", "Tomek231@");

        //when
        userService.createUser(userInput);
        List<UserEntity> allUsers = userRepository.findAll();

        //then
        assertTrue(allUsers.size() == 1);
    }

}