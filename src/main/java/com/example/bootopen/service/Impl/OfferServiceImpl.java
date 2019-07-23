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

    /**
     * @param vehicleId 车辆编号
     * @return offer
     * 返回一个报价最高的offer对象
     */
    @Override
    public Offer getOfferByVehicleId(int vehicleId) {
        Offer offer = new Offer();
        offer.setVehicleId(vehicleId);
        QueryWrapper<Offer> offerQueryWrapper = new QueryWrapper<>(offer).orderByDesc("offer_price");    //查找结果按报价降序排列
        List<Offer> offerList = offerMapper.selectList(offerQueryWrapper);
        if (offerList.isEmpty()){
            return null;
        }
        return offerList.get(0);      //返回报价最高的offer
    }

    /**
     * @param vehicleId 车辆编号
     * @return offerList
     * 返回offerList对象
     */
    @Override
    public List<Offer> getOffersByVehicleId(int vehicleId) {
        Offer offer = new Offer();
        offer.setVehicleId(vehicleId);
        QueryWrapper<Offer> offerQueryWrapper = new QueryWrapper<>(offer);
        return offerMapper.selectList(offerQueryWrapper);          //返回车辆的所有报价
    }

    /**
     * @param buyer 买家对象
     * @return offerList
     * 返回买家的offerList对象
     */
    @Override
    public List<Offer> getOffersByBuyer(User buyer) {
        Offer offer = new Offer();
        offer.setBuyerName(buyer.getUsername());
        QueryWrapper<Offer> offerQueryWrapper = new QueryWrapper<>(offer).orderByAsc("vehicle_id");   //买家的报价按车辆分类
        return offerMapper.selectList(offerQueryWrapper);
    }

    /**
     * @param seller 卖家
     * @return offerList
     * 返回卖家的offerList对象
     */
    @Override
    public List<Offer> getOffersBySeller(User seller) {
        Offer offer = new Offer();
        offer.setSellerName(seller.getUsername());
        QueryWrapper<Offer> offerQueryWrapper = new QueryWrapper<>(offer).orderByAsc("vehicle_id"); //卖家的报价按车辆分类
        return offerMapper.selectList(offerQueryWrapper);
    }

    /**
     * @param  offer
     * @return null
     * 添加offer数据，已存在则更新数据
     */
    @Override
    public void addOffer(Offer offer) {
        Offer offer1 = new Offer();
        offer1.setVehicleId(offer.getVehicleId());
        offer1.setBuyerName(offer.getBuyerName());
        QueryWrapper<Offer> offerQueryWrapper = new QueryWrapper<>(offer1);
        List<Offer> offerList = offerMapper.selectList(offerQueryWrapper);
        //以当前offer的车辆编号和买家用户名对数据表进行查询，若已存在报价则覆盖原有报价，若不存在则在数据库中添加报价
        if (offerList.isEmpty()){
            offerMapper.insert(offer);
            return ;
        }
        int offer_id = offerList.get(0).getOfferId();   //获取已存在的offer_id用于更新数据表的记录
        offer.setOfferId(offer_id);
        offerMapper.updateById(offer);
    }

    /**
     * @param offer 报价
     * @return null
     * 更新offer报价
     */
    @Override
    public void updateOffer(Offer offer) {
        offerMapper.updateById(offer);
    }
}
