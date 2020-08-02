package pl.sda.jobOfferAplication.user.entity;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import pl.sda.jobOfferAplication.user.model.UserOutput;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Table(name = "USERS")
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String login;
    private LocalDate creationDate;
    private String password;

    public UserEntity(String name, String login, LocalDate creationDate, String password) {
        this.name = name;
        this.login = login;
        this.creationDate = creationDate;
        this.password = password;
    }

    public UserOutput toOutput(){
        return new UserOutput(id, name, login, creationDate);
    }

}

