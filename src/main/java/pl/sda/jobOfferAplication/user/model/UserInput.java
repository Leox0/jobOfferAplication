package pl.sda.jobOfferAplication.user.model;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.UUID;

@Getter
@ToString
public class UserInput {

    private String uuid;
    private String login;
    private String name;
    private String creationDate;
    private String password;

    private UserInput() {
        uuid = UUID.randomUUID().toString();
        creationDate = "now";
    }
}
