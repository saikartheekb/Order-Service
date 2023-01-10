package com.assignment.orderservice.Controller;

import com.assignment.orderservice.Dto.OfferDto;
import com.assignment.orderservice.Model.Exceptions.OfferEntityException;
import com.assignment.orderservice.Model.Exceptions.OrderEntityException;
import com.assignment.orderservice.Model.Offer;
import com.assignment.orderservice.Service.Implementation.OfferServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/offers")
public class OfferController {

    @Autowired
    OfferServiceImpl offerService;

    @PostMapping()
    public ResponseEntity<String> createOffer(@RequestBody OfferDto offerDto) throws OfferEntityException {
        try{
            Offer offer = offerService.createOffer(offerDto);
            return new ResponseEntity<>(offer.getName() + " is created", HttpStatus.CREATED);
        } catch (Exception e){
            throw new OfferEntityException(e);
        }

    }

    @GetMapping()
    public ResponseEntity<List<Offer>> getAllOffers() throws OfferEntityException {
        try{
            return new ResponseEntity<>(offerService.getAllOffers(), HttpStatus.FOUND);
        } catch (Exception e){
            throw new OfferEntityException(e);
        }

    }

    @GetMapping("/{id}")
    public ResponseEntity<Offer> getOfferById(@PathVariable int id) throws OfferEntityException {
        try{
            Offer offer = offerService.getOfferById(id);
            return new ResponseEntity<>(offer, HttpStatus.FOUND);
        } catch (Exception e){
            throw new OfferEntityException(e);
        }

    }
}
