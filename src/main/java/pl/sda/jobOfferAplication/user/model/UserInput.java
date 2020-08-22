package pl.sda.jobOfferAplication.user.model;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDate;
import java.util.UUID;

@Getter
@ToString
public class UserInput {

    private String name;
    private String login;
    private LocalDate creationDate;
    private String password;

    public UserInput ( String asdasd , String dasdasd , String asdasdasdasd ) {
        creationDate = LocalDate.now();
    }

    public UserInput ( String name , String login , LocalDate creationDate , String password ){
        this.name = name;
        this.login = login;
        this.password = password;
    }
}
