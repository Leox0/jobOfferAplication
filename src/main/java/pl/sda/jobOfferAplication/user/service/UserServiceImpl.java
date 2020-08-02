package pl.sda.jobOfferAplication.user.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pl.sda.jobOfferAplication.user.entity.UserEntity;
import pl.sda.jobOfferAplication.user.exeption.UserException;
import pl.sda.jobOfferAplication.user.model.UserInput;
import pl.sda.jobOfferAplication.user.model.UserOutput;
import pl.sda.jobOfferAplication.user.repository.UserRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
public class UserServiceImpl implements UserService {

    public static final String NO_USER_FOUND_FOR_GIVEN_ID = "No user found for given id";
    public static final String USER_FOR_GIVEN_LOGIN_IS_EXIST = "User for given login is exist";
    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;

    //można też @Autowire zamiast tworzenia w konstruktorze
    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void createUser(UserInput userInput) throws UserException {
        validateUserIntput(userInput);
        final String encode = passwordEncoder.encode(userInput.getPassword());
        final UserEntity userEntity = new UserEntity(userInput.getName(), userInput.getLogin(), userInput.getCreationDate(), encode);
        userRepository.save(userEntity);
    }

    @Override
    public List<UserOutput> getAllUsers() {

        return userRepository.findAll()
                .stream()
                .map(UserEntity::toOutput)
                .collect(Collectors.toList());
    }

    @Override
    public UserOutput getUserById(Long id) throws UserException {
        Optional<UserEntity> optionalUserEntity = userRepository.findById(id);
        if (!optionalUserEntity.isPresent()) {
            throw new UserException(NO_USER_FOUND_FOR_GIVEN_ID);
        }
        return optionalUserEntity.get().toOutput();
    }

    private void validateUserIntput(UserInput userInput) throws UserException{
        // czy login i hasło mają prawidłową długość
        // login 6 znaków
        // przynajmniej 8 znaków jedną litere mała i jedna duza i jedna cyfra i jeden znak specjalny
        if (userRepository.existsUserEntitieByLogin(userInput.getLogin())){
            throw new UserException(USER_FOR_GIVEN_LOGIN_IS_EXIST);
        }
    }
}
