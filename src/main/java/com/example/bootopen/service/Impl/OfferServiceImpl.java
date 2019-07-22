package com.example.bootopen.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.bootopen.mapper.OfferMapper;
import com.example.bootopen.pojo.Offer;
import com.example.bootopen.pojo.User;
import com.example.bootopen.pojo.Vehicle;
import com.example.bootopen.service.IOfferService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class OfferServiceImpl implements IOfferService {

    @Autowired
    private OfferMapper offerMapper;

    @Override
    public Offer getOffersByVehicleId(int vehicleId) {
        Offer offer = new Offer();
        offer.setVehicleId(vehicleId);
        QueryWrapper<Offer> offerQueryWrapper = new QueryWrapper<>(offer);
        List<Offer> offerList = offerMapper.selectList(offerQueryWrapper);
        if (offerList.isEmpty()){
            return null;
        }
        return offerList.get(0);
    }

    @Override
    public List<Offer> getOffersByBuyer(User buyer) {
        Offer offer = new Offer();
        offer.setBuyerName(buyer.getUsername());
        QueryWrapper<Offer> offerQueryWrapper = new QueryWrapper<>(offer);
        return offerMapper.selectList(offerQueryWrapper);
    }

    @Override
    public List<Offer> getOffersBySeller(User seller) {
        Offer offer = new Offer();
        offer.setSellerName(seller.getUsername());
        QueryWrapper<Offer> offerQueryWrapper = new QueryWrapper<>(offer);
        return offerMapper.selectList(offerQueryWrapper);
    }

    @Override
    public void addOffer(Offer offer) {
        offerMapper.insert(offer);
    }
}
