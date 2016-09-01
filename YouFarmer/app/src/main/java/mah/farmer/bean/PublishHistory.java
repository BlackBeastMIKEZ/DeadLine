package mah.farmer.bean;

import java.io.Serializable;

/**发布历史类
 * Created by 黑色野兽迈特祖 on 2016/5/1.
 */
public class PublishHistory implements Serializable {
    String productName;
    String productImage;
    String productTagOne;
    String productTagTwo;
    String youNongPrice;
    String productNum;
    String productLocation;
    String description;

    String supplierPhone;
    String datetime;

    public PublishHistory(String productName, String productImage, String productTagOne, String productTagTwo, String youNongPrice, String productNum, String productLocation, String description,String supplierPhone, String datetime) {
        this.productName = productName;
        this.productImage = productImage;
        this.productTagOne = productTagOne;
        this.productTagTwo = productTagTwo;
        this.youNongPrice = youNongPrice;
        this.productNum = productNum;
        this.productLocation = productLocation;
        this.description = description;

        this.supplierPhone = supplierPhone;
        this.datetime = datetime;
    }

    public String getDatetime() {
        return datetime;
    }

    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductImage() {
        return productImage;
    }

    public void setProductImage(String productImage) {
        this.productImage = productImage;
    }

    public String getProductTagOne() {
        return productTagOne;
    }

    public void setProductTagOne(String productTagOne) {
        this.productTagOne = productTagOne;
    }

    public String getProductTagTwo() {
        return productTagTwo;
    }

    public void setProductTagTwo(String productTagTwo) {
        this.productTagTwo = productTagTwo;
    }

    public String getYouNongPrice() {
        return youNongPrice;
    }

    public void setYouNongPrice(String youNongPrice) {
        this.youNongPrice = youNongPrice;
    }

    public String getProductNum() {
        return productNum;
    }

    public void setProductNum(String productNum) {
        this.productNum = productNum;
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



    public String getSupplierPhone() {
        return supplierPhone;
    }

    public void setSupplierPhone(String supplierPhone) {
        this.supplierPhone = supplierPhone;
    }
}
