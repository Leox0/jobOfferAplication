package pl.sda.jobOfferAplication.user.service;

import net.bytebuddy.implementation.bytecode.Throw;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import pl.sda.jobOfferAplication.user.entity.UserEntity;
import pl.sda.jobOfferAplication.user.exception.UserException;
import pl.sda.jobOfferAplication.user.model.UserInput;
import pl.sda.jobOfferAplication.user.model.UserOutput;
import pl.sda.jobOfferAplication.user.repository.UserRepository;


import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static pl.sda.jobOfferAplication.user.service.UserServiceImpl.*;

@SpringBootTest
class UserServiceTest {

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

        //then
        List<UserEntity> allUsers = userRepository.findAll();
        assertTrue(allUsers.size() == 1);
    }

    @Test
    public void shouldThrowExceptionWhenCreateUsersWithTheSameLogin() throws UserException {
        //given
        UserInput userInput = new UserInput("Tomek", "Tomek12345", "Tomek231@");
        UserInput userInput2 = new UserInput("ArekTomas", "Tomek12345", "Tomek231@");
        userService.createUser(userInput);

        //when
        Executable executable = () -> userService.createUser(userInput2);

        //then
        List<UserEntity> allUsers = userRepository.findAll();
        assertTrue(allUsers.size() == 1);

        UserException userException = assertThrows(UserException.class, executable);
        assertEquals(USER_FOR_GIVEN_LOGIN_IS_EXIST, userException.getMessage());


    }

    @Test
    public void shouldThrowExceptionWhenLoginIsTooShort() throws UserException {
        //given
        UserInput userInput = new UserInput("Tomek", "Tome2", "Tomek231@");

        //when
        Executable executable = () -> userService.createUser(userInput);

        //then
        List<UserEntity> allUsers = userRepository.findAll();
        assertTrue(allUsers.size() == 0);

        UserException userException = assertThrows(UserException.class, executable);
        assertEquals(USER_LOGIN_IS_TOO_SHORT, userException.getMessage());
    }


    @Test
    public void shouldThrowExceptionWhenUserPasswordIsIncorrect() throws UserException {
        //given
        UserInput userInput = new UserInput("Tomek", "Tomek12345", "Tomek231");

        //when
        Executable executable = () -> userService.createUser(userInput);

        //then
        List<UserEntity> allUsers = userRepository.findAll();
        assertTrue(allUsers.size() == 0);

        UserException userException = assertThrows(UserException.class, executable);
        assertEquals(USER_PASSWORD_IS_INCORRECT, userException.getMessage());
    }

    @Test
    public void withPossibilityToDeleteUser() throws UserException{
        //given
        UserInput userInput = new UserInput ( "Tomek", "Tomek12345", "Tomek231@");
        userService.createUser ( userInput );

        //when
        userService.deleteUserById ( 1L );

        //then
        List<UserEntity> allUsers = userRepository.findAll();
        assertTrue(allUsers.size() == 0);
    }
    @Test
    public void shouldIDeleteUserWhenThereIsNoUser() throws UserException{
        //given
        UserInput userInput = new UserInput ( "Tomek", "Tomek12345", "Tomek231@");
        userService.createUser ( userInput );
        //when
        Executable executable = () -> userService.deleteUserById ( 1L );

        //then
        UserException userException = assertThrows ( UserException.class,executable );
        assertEquals ( NO_USER_FOUND_FOR_GIVEN_ID,userException.getMessage () );
    }
}
