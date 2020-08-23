package pl.sda.jobOfferAplication.user.controller;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import pl.sda.jobOfferAplication.JobOfferAplicationApplication;
import pl.sda.jobOfferAplication.user.model.UserInput;
import pl.sda.jobOfferAplication.user.repository.UserRepository;
import pl.sda.jobOfferAplication.user.service.UserService;

import static pl.sda.jobOfferAplication.user.controller.UserController.USERS_MAPPING;


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
    void tearDown() {
        userRepository.deleteAll();
    }

    @Test
    public void shouldFindUserById() throws Exception {
        //given
        UserInput userInput = new UserInput("Jan", "Janek210321", "Janko2103@");
        userService.createUser(userInput);
        final MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .get(USERS_MAPPING)
                .contentType(MediaType.APPLICATION_JSON_VALUE);

        //when
        final ResultActions resultActions = mockMvc.perform(requestBuilder);

        //then
        resultActions.andExpect(MockMvcResultMatchers.status().isOk());

    }
}
