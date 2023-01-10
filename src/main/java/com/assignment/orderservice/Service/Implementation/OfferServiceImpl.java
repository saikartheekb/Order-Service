package com.assignment.orderservice.Service.Implementation;

import com.assignment.orderservice.Dto.OfferDto;
import com.assignment.orderservice.Model.Offer;
import com.assignment.orderservice.Repository.OfferRepository;
import com.assignment.orderservice.Service.OfferService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class OfferServiceImpl implements OfferService {

    @Autowired
    OfferRepository offerRepository;

    @Override
    public List<Offer> getAllOffers() {
        return offerRepository.findAll();
    }

    @Override
    public Offer createOffer(OfferDto offerDto) {

        //Setting default values to the optional fields of Offer entity
        LocalDate startDate = offerDto.getStartDate() == null ? LocalDate.parse("1970-01-01") : offerDto.getStartDate();
        LocalDate endDate = offerDto.getEndDate() == null ? LocalDate.parse("2030-12-31") : offerDto.getEndDate();
        int maximumOrder = offerDto.getMaximumOrders() == 0 ? Integer.MAX_VALUE : offerDto.getMaximumOrders();

        Offer offer = Offer.builder()
                            .name(offerDto.getName())
                            .endDate(endDate)
                            .startDate(startDate)
                            .minimumOrders(offerDto.getMinimumOrders())
                            .maximumOrders(maximumOrder)
                            .discountPercentage(offerDto.getDiscountPercentage())
                            .enabled(true)
                            .build();
        return offerRepository.save(offer);
    }

    @Override
    public Offer getOfferById(int id) {
        Optional<Offer> offerObj = offerRepository.findById(id);
        if (offerObj.isPresent()) {
            return offerObj.get();
        } else {
            throw new EntityNotFoundException("Offer not found with ID: " + id);
        }
    }
}
