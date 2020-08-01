package pl.sda.jobOfferAplication.user.model;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class UserOutput {

    private Long uuid;
    private String login;
    private String name;
    private String creationDate;

}
