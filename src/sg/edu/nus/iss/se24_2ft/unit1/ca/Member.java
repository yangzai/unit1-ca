package sg.edu.nus.iss.se24_2ft.unit1.ca;

/**
 * @author: Tran Ngoc Hieu
 */
public class Member extends Customer{
    private String memberID, name;
//    private int loyaltyPoint, redeemPoint, addPoint;

    public Member(String memberID, String name) {
    	this.memberID = memberID;
    	this.name = name;
        loyaltyPoint = -1;
    }
    
    public Member(String memberID, String name, int loyaltyPoint){
    	this.memberID = memberID;
    	this.name = name;
    	this.loyaltyPoint = loyaltyPoint;
    }
    
	public String getMemberIDasString(){
		return memberID;
	}
	
	public int setRedeemPoint(int point){
		if (loyaltyPoint < 0){
			redeemPoint = 0;
		} else if (point > loyaltyPoint) {
			//cannot redeem more than your current Loyalty Point
			redeemPoint = loyaltyPoint;
		} else {
			redeemPoint = point;
		}
		//Redeem base on multiple of 100 points
		redeemPoint = (redeemPoint/100) * 100;		
		return redeemPoint;
	}
	
	public float getRedeemPrice(){
		//Price redeem $5 for every 100 points
		float price = (redeemPoint/100)*5;
		return price;
	}
	
	public int setAddPoint(float amountPaid){
		//dollar-to-point value: $10 spent to get 1 additional point
		addPoint = (int) (amountPaid / 10);
		return addPoint;
	}
	
	public int updateLoyaltyPoint(){
		loyaltyPoint = loyaltyPoint - redeemPoint + addPoint;
		return loyaltyPoint;
	}
	
	

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		String str = name + ',' + memberID + ',' + String.valueOf(loyaltyPoint);
		return str;
	}

	public String getMemberID() {
		return memberID;
	}

	public String getName() {
		return name;
	}
}
