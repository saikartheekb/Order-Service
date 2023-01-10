package com.assignment.orderservice.Service;

import com.assignment.orderservice.Dto.OfferDto;
import com.assignment.orderservice.Model.Offer;

import java.util.List;

public interface OfferService {
    List<Offer> getAllOffers();

    Offer createOffer(OfferDto offerDto);

    Offer getOfferById(int id);
}
