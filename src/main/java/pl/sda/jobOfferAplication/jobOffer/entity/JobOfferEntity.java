package pl.sda.jobOfferAplication.jobOffer.entity;


import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import pl.sda.jobOfferAplication.jobOffer.model.JobOfferCategory;

import javax.persistence.*;

@Entity
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Table(name = "JOB_OFFERS")
public class JobOfferEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private JobOfferCategory category;
    private String startDate;
    private String endDate;

    public JobOfferEntity(Long id, JobOfferCategory category, String startDate, String endDate) {
        this.id = id;
        this.category = category;
        this.startDate = startDate;
        this.endDate = endDate;
    }

}


