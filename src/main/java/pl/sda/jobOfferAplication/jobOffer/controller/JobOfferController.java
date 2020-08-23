package pl.sda.jobOfferAplication.jobOffer.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(JobOfferController.JOB_OFFER_MAPPING)
public class JobOfferController {
    public static final String JOB_OFFER_MAPPING = "/jobOffer";

}
