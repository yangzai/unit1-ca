package sg.edu.nus.iss.se24_2ft.unit1.ca.customer.member;

import sg.edu.nus.iss.se24_2ft.unit1.ca.customer.Customer;

import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * @author: Tran Ngoc Hieu
 */
public class Member implements Customer {
    private String id, requestedId, name;
    private int loyaltyPoint, redeemPoint, addPoint;

    public Member(String requestedId, String name) {
        //TODO: chk all other list methods, try validation
        id = null;

        this.requestedId = requestedId;
        this.name = name;
        loyaltyPoint = -1;
    }
    
    public String getId() {
        return id;
    }
    public String getRequestedId() {
        return requestedId;
    }

    public String getName() {
        return name;
    }

    public int getLoyaltyPoint() {
        return loyaltyPoint;
    }

    @Override
    public String toString() {
        return Arrays.asList(name, id, loyaltyPoint).stream()
                .map(Object::toString)
                .collect(Collectors.joining(","));
    }

    //protected setters
    protected void setId () { id = requestedId; }

    protected void setLoyaltyPoint(int loyaltyPoint) {
        this.loyaltyPoint = loyaltyPoint;
    }

    //TODO: to be verified with CheckoutPanel
//    public int setRedeemPoint(int point){
//        if (loyaltyPoint < 0){
//            redeemPoint = 0;
//        } else if (point > loyaltyPoint) {
//            //cannot redeem more than your current Loyalty Point
//            redeemPoint = loyaltyPoint;
//        } else {
//            redeemPoint = point;
//        }
//        //Redeem base on multiple of 100 points
//        redeemPoint = (redeemPoint/100) * 100;
//        return redeemPoint;
//    }
//
//    public float getRedeemPrice(){
//        //Price redeem $5 for every 100 points
//        float price = (redeemPoint/100)*5;
//        return price;
//    }
//
//    public int setAddPoint(float amountPaid){
//        //dollar-to-point value: $10 spent to get 1 additional point
//        addPoint = (int) (amountPaid / 10);
//        return addPoint;
//    }
//
//    public int updateLoyaltyPoint(){
//        loyaltyPoint = loyaltyPoint - redeemPoint + addPoint;
//        return loyaltyPoint;
//    }
//
//    public int getRedeemPoint() {
//        return redeemPoint;
//    }
//
//    public int getAddPoint() {
//        return addPoint;
//    }
}