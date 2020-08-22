package pl.sda.jobOfferAplication;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import pl.sda.jobOfferAplication.user.entity.UserEntity;
import pl.sda.jobOfferAplication.user.exception.UserException;
import pl.sda.jobOfferAplication.user.model.UserOutput;
import pl.sda.jobOfferAplication.user.repository.UserRepository;
import pl.sda.jobOfferAplication.user.service.UserService;
import java.time.LocalDate;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
    @SpringBootTest
    class UserServiceTest {

        @Autowired
        UserService userService;

        @Autowired
        UserRepository userRepository;

        @AfterEach
        void tearDown ( ){
            userRepository.deleteAll ( );
        }


        @Test
        public void isPossibleToFindUserById ( ) throws UserException{

            //given
            UserEntity userEntity = new UserEntity ( "adada" , "adada" , LocalDate.now ( ) , "222d2" );
            userRepository.save ( userEntity );

            //when
            UserOutput userById = userService.getUserById (1L);

            //then
            assertTrue (!(userById == null));
            UserOutput userOutput = userEntity.toOutput ();
            assertEquals ( userOutput.getName () , userById.getName () );
            assertEquals ( userOutput.getLogin () , userById.getLogin () );
        }
    }
