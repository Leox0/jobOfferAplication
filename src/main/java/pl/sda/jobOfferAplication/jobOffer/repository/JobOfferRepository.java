package pl.sda.jobOfferAplication.jobOffer.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.sda.jobOfferAplication.jobOffer.entity.JobOfferEntity;

@Repository
public interface JobOfferRepository extends JpaRepository<JobOfferEntity, Long>  {


}
