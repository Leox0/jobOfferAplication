package pl.sda.jobOfferAplication.jobOffer.model;


import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Getter;
import pl.sda.jobOfferAplication.user.model.UserOutput;

@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class JobOfferOutput {

    private String category;
    private String startDate;
    private String endDate;
    private UserOutput user;

}
