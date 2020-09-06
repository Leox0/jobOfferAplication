package pl.sda.jobOfferAplication.user.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.sda.jobOfferAplication.user.exception.UserException;
import pl.sda.jobOfferAplication.user.model.*;
import pl.sda.jobOfferAplication.user.service.UserService;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping(UserController.USERS_MAPPING)
public class UserController {
    public static final String USERS_MAPPING = "/users";

    //CONTROLLER <-> SERVICE <-> REPOSITORY <-> DB

    @Autowired //można przez konstruktor
    private UserService userService;

    @GetMapping
    public ResponseEntity<List<UserOutput>> getAllUser() {
        final List<UserOutput> allUsers = userService.getAllUsers();
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(allUsers);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserOutput> getUserById(@PathVariable(value = "id") Long id) throws UserException {
        UserOutput userById = userService.getUserById(id);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(userById);
    }

    @PostMapping
    public ResponseEntity<Void> postUser(@RequestBody UserInput userInput) throws UserException {
        userService.createUser(userInput);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUserById(@PathVariable(value = "id") Long id) throws UserException {
        userService.deleteUserById(id);
        return ResponseEntity
                .status(HttpStatus.OK)
                .build();
    }
}
