package sg.edu.nus.iss.se24_2ft.unit1.ca;
/**
 * @author: Tran Ngoc Hieu
 */
public class PublicCustomer extends Customer{
	private static PublicCustomer instance = new PublicCustomer();
	
	private PublicCustomer() {
		// TODO Auto-generated constructor stub
	}
	
	public static PublicCustomer getInstance() {
		return instance;
	}

	@Override
	public String getMemberIDasString() {
		// TODO Auto-generated method stub
		return "PUBLIC";
	}

	@Override
	public int setRedeemPoint(int point) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public float getRedeemPrice() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int setAddPoint(float amountPaid) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int updateLoyaltyPoint() {
		// TODO Auto-generated method stub
		return 0;
	}
}
