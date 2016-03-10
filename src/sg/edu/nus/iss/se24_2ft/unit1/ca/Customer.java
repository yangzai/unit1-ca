package sg.edu.nus.iss.se24_2ft.unit1.ca;
/**
 * @author: Tran Ngoc Hieu
 */
public abstract class Customer {
	protected int loyaltyPoint, redeemPoint, addPoint;
	
	public abstract String getMemberIDasString();
	
	public abstract int setRedeemPoint(int point);
	
	public abstract float getRedeemPrice();
	
	public abstract int setAddPoint(float amountPaid);
	
	public abstract int updateLoyaltyPoint();

	public int getLoyaltyPoint() {
		return loyaltyPoint;
	}

	public int getRedeemPoint() {
		return redeemPoint;
	}

	public int getAddPoint() {
		return addPoint;
	}
	
}
