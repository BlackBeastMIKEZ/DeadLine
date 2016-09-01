package mah.farmer.bean;

import java.io.Serializable;

/**土地出租类
 * Created by 黑色野兽迈特祖 on 2016/4/26.
 */
public class LandRentInfo implements Serializable{
    String renter;
    String renterPhoneNum;
    String renterImage;
    String landLocation;
    String landImag;
    String rentPrice;
    String rentNum;

    public LandRentInfo(String renter, String renterPhoneNum, String renterImage, String landLocation, String landImag, String rentPrice, String rentNum) {
        this.renter = renter;
        this.renterPhoneNum = renterPhoneNum;
        this.renterImage = renterImage;
        this.landLocation = landLocation;
        this.landImag = landImag;
        this.rentPrice = rentPrice;
        this.rentNum = rentNum;
    }

    public String getRenter() {
        return renter;
    }

    public void setRenter(String renter) {
        this.renter = renter;
    }

    public String getRenterPhoneNum() {
        return renterPhoneNum;
    }

    public void setRenterPhoneNum(String renterPhoneNum) {
        this.renterPhoneNum = renterPhoneNum;
    }

    public String getRenterImage() {
        return renterImage;
    }

    public void setRenterImage(String renterImage) {
        this.renterImage = renterImage;
    }

    public String getLandLocation() {
        return landLocation;
    }

    public void setLandLocation(String landLocation) {
        this.landLocation = landLocation;
    }

    public String getLandImag() {
        return landImag;
    }

    public void setLandImag(String landImag) {
        this.landImag = landImag;
    }

    public String getRentPrice() {
        return rentPrice;
    }

    public void setRentPrice(String rentPrice) {
        this.rentPrice = rentPrice;
    }

    public String getRentNum() {
        return rentNum;
    }

    public void setRentNum(String rentNum) {
        this.rentNum = rentNum;
    }
}
