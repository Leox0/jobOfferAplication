package pl.sda.jobOfferAplication.user.service;

import pl.sda.jobOfferAplication.user.exception.UserException;
import pl.sda.jobOfferAplication.user.model.UserInput;
import pl.sda.jobOfferAplication.user.model.UserOutput;

import java.util.List;

public interface UserService {
    void createUser(UserInput userInput) throws UserException;
    List<UserOutput> getAllUsers();
    UserOutput getUserById(Long id) throws UserException;


}
