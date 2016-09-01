package mah.farmer.bean;

import java.io.Serializable;

/**求购信息类
 * Created by 黑色野兽迈特祖 on 2016/4/25.
 */
public class PurchaseOrderInfo implements Serializable{
    String purchaser;
    String purchaserPhoneNum;
    String purchaserImage;
    String productName;
    String purchaseNum;
    String productLocation;
    String description;

    public PurchaseOrderInfo(String purchaser, String purchaserPhoneNum, String purchaserImage, String productName, String purchaseNum, String productLocation, String description) {
        this.purchaser = purchaser;
        this.purchaserPhoneNum = purchaserPhoneNum;
        this.purchaserImage = purchaserImage;
        this.productName = productName;
        this.purchaseNum = purchaseNum;
        this.productLocation = productLocation;
        this.description = description;
    }

    public String getPurchaser() {
        return purchaser;
    }

    public void setPurchaser(String purchaser) {
        this.purchaser = purchaser;
    }

    public String getPurchaserPhoneNum() {
        return purchaserPhoneNum;
    }

    public void setPurchaserPhoneNum(String purchaserPhoneNum) {
        this.purchaserPhoneNum = purchaserPhoneNum;
    }

    public String getPurchaserImage() {
        return purchaserImage;
    }

    public void setPurchaserImage(String purchaserImage) {
        this.purchaserImage = purchaserImage;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getPurchaseNum() {
        return purchaseNum;
    }

    public void setPurchaseNum(String purchaseNum) {
        this.purchaseNum = purchaseNum;
    }

    public String getProductLocation() {
        return productLocation;
    }

    public void setProductLocation(String productLocation) {
        this.productLocation = productLocation;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
