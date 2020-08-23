package pl.sda.jobOfferAplication.jobOffer.entity;


import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.apache.catalina.User;
import pl.sda.jobOfferAplication.jobOffer.model.JobOfferCategory;
import pl.sda.jobOfferAplication.user.model.UserOutput;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Table(name = "JOB OFFERS")
public class JobOfferEntity {

}


