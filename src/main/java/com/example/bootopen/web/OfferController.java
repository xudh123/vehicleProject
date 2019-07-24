package com.example.bootopen.web;

import com.example.bootopen.Consts.UserConsts;
import com.example.bootopen.pojo.Offer;
import com.example.bootopen.pojo.Order;
import com.example.bootopen.pojo.User;
import com.example.bootopen.pojo.Vehicle;
import com.example.bootopen.redis.RedisService;
import com.example.bootopen.service.IOfferService;
import com.example.bootopen.service.IOrderService;
import com.example.bootopen.service.IUserService;
import com.example.bootopen.service.IVehicleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class OfferController {
    @Autowired
    private RedisService redisService;
    @Autowired
    private IUserService userService;
    @Autowired
    private IVehicleService vehicleService;
    @Autowired
    private IOrderService orderService;
    @Autowired
    private IOfferService offerService;

    /**
     * @return 返回一个用户数据对象
     * redis中不存在就返回一个空对象
     */
    private User getUser(){
        if(UserConsts.userLogined == 1){       //登录标志为1，
            return redisService.get(UserConsts.userName, User.class);
        }else {
            return (new User());
        }
    }

    /**
     * @param offer 报价
     * @return test
     */
    @PostMapping("sendOffer")
    public String sendOffer(Model model, Offer offer){
        User buyer = getUser();
        offer.setBuyerName(buyer.getUsername());

        Vehicle vehicle = new Vehicle();
        vehicle.setVehicleId(offer.getVehicleId());
        Vehicle vehicle1 = vehicleService.getVehicleById(vehicle);
        User seller = userService.selectUserById(vehicle1.getVehicleOwner());
        offer.setSellerName(seller.getUsername());
        vehicle1.setVehicleOwnerName(seller.getUsername());

        offerService.addOffer(offer);

        model.addAttribute("user", getUser());
        model.addAttribute("vehicle_see", vehicle1);
        model.addAttribute("is_login", UserConsts.userLogined);
        return "vehicle";

    }

    @PostMapping("accept_offer")
    public String acceptOffer(int vehicleId, int offerId, Model model){

        List<Offer> offerList = offerService.getOffersByVehicleId(vehicleId);
        for (Offer offer : offerList){
            if (offerId == offer.getOfferId()){
                offer.setOfferStatus("已成交");
                offerService.updateOffer(offer);
                continue;
            }
            offer.setOfferStatus("已拒绝");
            offerService.updateOffer(offer);
        }

        User user = getUser();
        model.addAttribute("user", user);

        List<Vehicle> MyVehicleList = vehicleService.getVehiclesByOwner(user.getUserId());
        model.addAttribute("MyVehicleList", MyVehicleList);
        model.addAttribute("is_login", UserConsts.userLogined);

        return "user_manage";
    }

    /**
     * @param offerId 报价编号
     * @return finishOffer 完成整个报价流程
     */
    @PostMapping("finishOffer")
    public String finishOffer(Model model, int offerId){
        Offer offer = offerService.getOfferByOfferId(offerId);

        Vehicle vehicle = new Vehicle();
        vehicle.setVehicleId(offer.getVehicleId());
        vehicle = vehicleService.getVehicleById(vehicle);

        model.addAttribute("offerPrice", offer.getOfferPrice());
        model.addAttribute("vehicle", vehicle);
        model.addAttribute("is_login", UserConsts.userLogined);
        model.addAttribute("user", getUser());
        return "finishOffer";
    }

    /**
     * @param order 订单
     * @param seller_id 卖家id
     * @return vehicle 浏览车辆
     */
    @PostMapping("finish_offer")
    public String finishAcution(Model model, Order order, int seller_id){
        User seller = userService.selectUserById(seller_id);
        order.setSeller(seller.getUsername());
        order.setBuyer(getUser().getUsername());
        orderService.saveOrder(order);

        Vehicle vehicle = new Vehicle();
        vehicle.setVehicleId(order.getVehicleId());
        vehicle = vehicleService.getVehicleById(vehicle);
        vehicle.setVehicleOwner(getUser().getUserId());
        vehicle.setVehicleOwnerName(getUser().getUsername());
        vehicle.setVehicleOnsale(0);
        vehicleService.updateVehicleById(vehicle);

        model.addAttribute("vehicle_see", vehicle);
        model.addAttribute("is_login", UserConsts.userLogined);
        model.addAttribute("user", getUser());

        return "vehicle";
    }

    @RequestMapping("/User_info/offer_sent.html")
    public String getBuyerOffers(Model model){
        User buyer = getUser();
        List<Offer> offerList = offerService.getOffersByBuyer(buyer);
        offerList = getOfferList(offerList);

        model.addAttribute("user", buyer);
        model.addAttribute("offerList", offerList);
        model.addAttribute("is_login", UserConsts.userLogined);
        return "User_info/offer_sent";
    }

    @RequestMapping("/User_info/offer_received.html")
    public String getSellerOffers(Model model){
        User seller = getUser();
        List<Offer> offerList = offerService.getOffersBySeller(seller);

        offerList = getOfferList(offerList);

        model.addAttribute("user", seller);
        model.addAttribute("offerList", offerList);
        model.addAttribute("is_login", UserConsts.userLogined);
        return "User_info/offer_received";
    }

    public List<Offer> getOfferList(List<Offer> offerList){
        int size = offerList.size();
        while (size>0){
            Vehicle vehicle = new Vehicle();
            vehicle.setVehicleId(offerList.get(size-1).getVehicleId());
            vehicle = vehicleService.getVehicleById(vehicle);
            offerList.get(size-1).setVehicleBrand(vehicle.getVehicleBrand());
            offerList.get(size-1).setVehicleType(vehicle.getVehicleType());
            offerList.get(size-1).setVehiclePrice(vehicle.getVehiclePrice());
            offerList.get(size-1).setVehicleOnSale(vehicle.getVehicleOnsale());
            size--;
        }
        return offerList;
    }

}
