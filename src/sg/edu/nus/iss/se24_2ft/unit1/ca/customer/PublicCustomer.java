package sg.edu.nus.iss.se24_2ft.unit1.ca.customer;

/**
 * @author: Tran Ngoc Hieu
 */
public class PublicCustomer implements Customer {
    private static PublicCustomer instance = new PublicCustomer();

    private PublicCustomer() {
    }

    public static PublicCustomer getInstance() {
        return instance;
    }

    @Override
    public String getId() {
        return "PUBLIC";
    }

//	@Override
//	public int setRedeemPoint(int point) {
//		return 0;
//	}
//
//	@Override
//	public float getRedeemPrice() {
//		return 0;
//	}
//
//	@Override
//	public int setAddPoint(float amountPaid) {
//		return 0;
//	}
//
//	@Override
//	public int updateLoyaltyPoint() {
//		return 0;
//	}
}
