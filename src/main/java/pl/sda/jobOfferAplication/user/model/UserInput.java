package pl.sda.jobOfferAplication.user.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import java.time.LocalDate;
import java.util.UUID;

@Getter
@ToString
public class UserInput {

    private String name;
    private String login;
    @JsonIgnore
    private LocalDate creationDate = LocalDate.now();
    private String password;

    public UserInput(String name, String login, String password) {
        this.name = name;
        this.login = login;
        this.password = password;
    }
}
