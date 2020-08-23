package pl.sda.jobOfferAplication.user.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hibernate.boot.spi.InFlightMetadataCollector;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import pl.sda.jobOfferAplication.JobOfferAplicationApplication;
import pl.sda.jobOfferAplication.user.entity.UserEntity;
import pl.sda.jobOfferAplication.user.exception.UserException;
import pl.sda.jobOfferAplication.user.model.UserInput;
import pl.sda.jobOfferAplication.user.repository.UserRepository;
import pl.sda.jobOfferAplication.user.service.UserService;

import javax.transaction.Transactional;
import java.lang.reflect.Executable;
import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static pl.sda.jobOfferAplication.user.controller.UserController.USERS_MAPPING;
import static pl.sda.jobOfferAplication.user.service.UserServiceImpl.USER_PASSWORD_IS_INCORRECT;

@AutoConfigureMockMvc
@SpringBootTest(classes = JobOfferAplicationApplication.class)
class UserControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    UserRepository userRepository;
    @Autowired
    UserService userService;

    @AfterEach
    void tearDown ( ){
        userRepository.deleteAll ( );
    }

    @Test
    @Transactional
    public void shouldCreateUserCorrectly ( ) throws Exception{
        //given
        final String USER_PATH = USERS_MAPPING;
        String name = "Adam";
        String login = "adam123";
        String password = "Burak123@";
        //String userInput = "{\"name\": \"" + name + "\",\"login\": \"" + login + "\",\"password\": \"" + password + "\"}";
        UserInput userInput = new UserInput ( name , login , password );
        final MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .post ( USER_PATH )
                .contentType ( MediaType.APPLICATION_JSON_VALUE )
                .content ( toJson ( userInput ) );
        // When
        final ResultActions resultActions = mockMvc.perform ( requestBuilder );
        //then
        assertTrue ( userRepository.findAll ( ).size ( ) == 1 );
    /*    userRepository.findAll()
                .stream()
                .findFirst()
                .get()
                .toOutput()
                .getUuid();*/
        assertTrue ( userRepository.existsUserEntitieByLogin ( login ) );
        resultActions.andExpect ( status ( ).isCreated ( ) );
    }


    @Test
    @Transactional
    public void shouldDeleteUserCorrectly ( ) throws Exception{
        //given
        String name = "Adam";
        String login = "adam123";
        String password = "Burak123@";
        UserEntity userEntity = new UserEntity ( name , login , LocalDate.now ( ) , password );
        userRepository.save ( userEntity );
        int idUserToDelete = 1;
        final String USER_PATH = USERS_MAPPING + "/" + idUserToDelete;
        final MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .delete ( USER_PATH )
                .contentType ( MediaType.APPLICATION_JSON_VALUE );
        // When
        final ResultActions resultActions = mockMvc.perform ( requestBuilder );
        //then
        resultActions.andExpect ( status ( ).isOk ( ) );
        assertThat ( userRepository.findAll ( ) ).isEmpty ( );
    }

    @Test
    public void shouldThrowExceptionDeletingUserWhenThereIsNoUser ( ) throws Exception{
        //given
        int idUserToDelete = 1;
        final String USER_PATH = USERS_MAPPING + "/" + idUserToDelete;
        final MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .delete ( USER_PATH )
                .contentType ( MediaType.APPLICATION_JSON_VALUE );
        //when
        final ResultActions resultActions = mockMvc.perform ( requestBuilder );
        //then
        resultActions.andExpect ( status ( ).isNotFound ( ) );
        assertThat ( userRepository.findAll ( ) ).isEmpty ( );

    }

    public static String toJson ( final Object object ){
        try {
            return new ObjectMapper ( ).writeValueAsString ( object );
        } catch ( JsonProcessingException e ) {
            throw new RuntimeException ( e );
        }
    }

    @Test
    public void shouldFindUserById ( ) throws Exception{
        //given

        UserInput userInput = new UserInput("Jan", "Janek210321", "Janko2103@");
        userService.createUser(userInput);
        Long uuid = userRepository.findAll()
                .stream()
                .findFirst()
                .get()
                .toOutput()
                .getUuid();

        final MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .get(USERS_MAPPING + "/" + uuid)
                .contentType(MediaType.APPLICATION_JSON_VALUE);


        //when
        final ResultActions resultActions = mockMvc.perform ( requestBuilder );

        //then
        resultActions.andExpect ( MockMvcResultMatchers.status ( ).isOk ( ) );

    }

    @Test
    public void shouldThrowExceptionWhenUserPasswordIsIncorrect ( ) throws Exception{

        //given
        final String USER_PATH = USERS_MAPPING;
        UserInput userInput = new UserInput ( "ADam" , "Adam2222" , "123" );
        final MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .post ( USER_PATH )
                .contentType ( MediaType.APPLICATION_JSON_VALUE )
                .content ( toJson ( userInput ) );

        //when
        final ResultActions resultActions = mockMvc.perform ( requestBuilder );

        //then
        resultActions.andExpect ( MockMvcResultMatchers.status ( ).isNotFound ( ) );
    }

    @Test
    public void shouldThrowExceptionWhenCreateUsersWithTheSameLogin() throws Exception {
        //given
        UserInput userInput = new UserInput("Jan", "Janek210321", "Janko2103@");
        userService.createUser(userInput);
        UserInput userInput2 = new UserInput("Jan2", "Janek210321", "Janko21032@");
        final MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .post(USERS_MAPPING)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(toJson(userInput2));

        //when
        final ResultActions resultActions = mockMvc.perform(requestBuilder);

        //then
        resultActions.andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    public void shouldThrowExceptionWhenLoginIsTooShort() throws Exception {
        //given
        UserInput userInput = new UserInput("Jan", "Jan1", "Janko2103@");
        final MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .post(USERS_MAPPING)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(toJson(userInput));

        //when
        final ResultActions resultActions = mockMvc.perform(requestBuilder);

        //then
        resultActions.andExpect(MockMvcResultMatchers.status().isNotFound());
    }
}