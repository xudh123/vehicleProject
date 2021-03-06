package com.example.bootopen.service;

import com.example.bootopen.pojo.Offer;
import com.example.bootopen.pojo.User;
import com.example.bootopen.pojo.Vehicle;

import java.util.List;

public interface IOfferService {
    Offer getOfferByVehicleId(int vehicleId);
    Offer getOfferByOfferId(int offerId);
    List<Offer> getOffersByVehicleId(int vehicleId);
    List<Offer> getOffersByBuyer(User buyer);
    List<Offer> getOffersBySeller(User seller);
    void addOffer(Offer offer);
    void updateOffer(Offer offer);
}
