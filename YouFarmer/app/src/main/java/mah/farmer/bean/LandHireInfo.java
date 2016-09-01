package mah.farmer.bean;

import java.io.Serializable;

/**土地租用类
 * Created by 黑色野兽迈特祖 on 2016/4/26.
 */
public class LandHireInfo implements Serializable{
    String hirer;
    String hirerImage;
    String hirerPhoneNum;
    String landLocation;
    String hireNum;
    String description;

    public LandHireInfo(String hirer, String hirerImage, String hirerPhoneNum, String landLocation, String hireNum, String description) {
        this.hirer = hirer;
        this.hirerImage = hirerImage;
        this.hirerPhoneNum = hirerPhoneNum;
        this.landLocation = landLocation;
        this.hireNum = hireNum;
        this.description = description;
    }

    public String getHirer() {
        return hirer;
    }

    public void setHirer(String hirer) {
        this.hirer = hirer;
    }

    public String getHirerImage() {
        return hirerImage;
    }

    public void setHirerImage(String hirerImage) {
        this.hirerImage = hirerImage;
    }

    public String getHirerPhoneNum() {
        return hirerPhoneNum;
    }

    public void setHirerPhoneNum(String hirerPhoneNum) {
        this.hirerPhoneNum = hirerPhoneNum;
    }

    public String getLandLocation() {
        return landLocation;
    }

    public void setLandLocation(String landLocation) {
        this.landLocation = landLocation;
    }

    public String getHireNum() {
        return hireNum;
    }

    public void setHireNum(String hireNum) {
        this.hireNum = hireNum;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
