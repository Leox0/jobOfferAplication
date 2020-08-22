package pl.sda.jobOfferAplication.user.service;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import pl.sda.jobOfferAplication.user.entity.UserEntity;
import pl.sda.jobOfferAplication.user.exeption.UserException;
import pl.sda.jobOfferAplication.user.model.UserInput;
import pl.sda.jobOfferAplication.user.model.UserOutput;
import pl.sda.jobOfferAplication.user.repository.UserRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserServiceImplTest {

    @Autowired
    UserService userService;

    @Autowired
    UserRepository userRepository;

    @AfterEach
    void tearDown ( ){
        userRepository.deleteAll ( );
    }

    @Test
    public void shouldCreateUserCorrectly ( ) throws UserException{

        //given
        UserInput userInput = new UserInput ( "asdasd" , "dasdasd" , "asdasdasdasd" );
        //when
        userService.createUser ( userInput );
        //then
        final List < UserEntity > all = userRepository.findAll ( );
        assertTrue ( all.size ( ) == 1 );
        final UserOutput userOutput = all.get ( 0 ).toOutput ( );
        userInput.equals ( userOutput );
        assertEquals ( userOutput.getLogin ( ) , userInput.getLogin ( ) );


    }

    @Test
    public void isPossibleToFindUserById ( ) throws UserException{

        //given
        UserEntity userEntity = new UserEntity ( "adada" , "adada" , LocalDate.now ( ) , "222d2" );
        userRepository.save ( userEntity );

        //when
        UserOutput userById = userService.getUserById ( 1L );

        //then
        assertTrue ( ! (userById == null) );
        UserOutput userOutput = userEntity.toOutput ( );
        assertEquals ( userOutput.getName ( ) , userById.getName ( ) );
        assertEquals ( userOutput.getLogin ( ) , userById.getLogin ( ) );
        LocalDate creationDate = userOutput.getCreationDate ( );
    }
}

