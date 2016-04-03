package util;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

/**
 * Created by yangzai on 3/4/16.
 */
public final class TestUtil {
    public static final String DATA_PATH = "test",
            CATEGORY_FILENAME = DATA_PATH + "/Category.dat",
            PRODUCT_FILENAME = DATA_PATH + "/Products.dat",
            MEMBER_FILENAME = DATA_PATH + "/Members.dat",
            DISCOUNT_FILENAME = DATA_PATH + "/Discounts.dat",
            TRANSACTION_FILENAME = DATA_PATH + "/Transactions.dat",
            STOREKEEPER_FILENAME = DATA_PATH + "/Storekeepers.dat",
            VENDOR_MUG_FILENAME = DATA_PATH + "/VendorsMUG.dat",
            VENDOR_CLO_FILENAME = DATA_PATH + "/VendorsCLO.dat";
    public static final List<String> TRANSACTION_STRING_LIST = Arrays.asList(
            "1,CLO/1,F42563743156,2,2013-09-28",
            "1,MUG/1,F42563743156,3,2013-09-28",
            "2,STA/1,PUBLIC,1,2013-09-29",
            "3,STA/2,R64565FG4,2,2013-09-30"
    ), MEMBER_STRING_LIST = Arrays.asList(
            "Yan Martel,F42563743156,150",
            "Suraj Sharma,X437F356,250",
            "Ang Lee,R64565FG4,-1"
    ), PRODUCT_STRING_LIST = Arrays.asList(
            "BOK/1,Book,expensive,50,20.0,178,10,50",
            "BOK/2,Book,expensive,50,20.0,179,10,50",
            "CLO/1,Centenary Jumper,A really nice momento,315,21.45,1234,10,100",
            "MUG/1,Centenary Mug,A really nice mug this time,525,10.25,9876,25,150",
            "PEN/1/1,Pen,cheap,100,1.5,123454,5,20",
            "STA/1,NUS Pen,A really cute blue pen,768,5.75,123459876,50,250",
            "STA/2,NUS Notepad,Great notepad for those lectures,1000,3.15,6789,25,75"
    ), DISCOUNT_STRING_LIST = Arrays.asList(
            "MEMBER_FIRST,First purchase by member,ALWAYS,ALWAYS,20,M",
            "MEMBER_SUBSEQ,Subsequent purchase by member,ALWAYS,ALWAYS,10,M",
            "CENTENARY,Centenary Celebration in 2014,2014-01-01,365,15,A",
            "PRESIDENT_BDAY,University President’s birthday,2014-02-01,7,20,A",
            "ORIENTATION_DAY,Orientation Day,2014-02-02,3,50,A",
            "PUBLIC_DISCOUNT,Public Discount for all customer,ALWAYS,ALWAYS,5,A"
    ), CATEGORY_STRING_LIST = Arrays.asList(
            "CLO,Clothing", "MUG,Mugs", "STA,Stationary", "DIA,Diary"
    ), STOREKEEPER_STRING_LIST = Arrays.asList(
            "Stacy,Dean56s", "Johny,A12ysd45"
    ), VENDOR_MUG_STRING_LIST = Arrays.asList(
            "Nancy’s Gifts,Best of the best gifts from Nancy’s",
            "Office Sovenirs,One and only Office Sovenirs",
            "Pen’s and Such,Sovenirs and gifts for all occasions",
            "ArtWorks Stationary Store,All kinds of Stationary and Gifts"
    ), VENDOR_CLO_STRING_LIST = Arrays.asList(
            "Nancy’s Gifts,Best of the best gifts from Nancy’s",
            "Pen’s and Such Clothes,Clothing for all occasions",
            "Clothing Store,All kinds of Clothing"
    );

    private TestUtil() {}

    public static void putData(String fileName, List<String> stringList) {
        try {
            Files.write(Paths.get(fileName), stringList);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void deleteData(String fileName) {
        try {
            Files.deleteIfExists(Paths.get(fileName));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
