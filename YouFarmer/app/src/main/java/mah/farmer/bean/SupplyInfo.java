package mah.farmer.bean;

import java.io.Serializable;

/**供应信息类
 * Created by 黑色野兽迈特祖 on 2016/4/26.
 */
public class SupplyInfo  implements Serializable{
    String supplier;
    String supplierPhoneNum;
    String supplierImage;
    String productNmae;
    String supplyPrice;
    String supplyNum;
    String productLocation;
    String description;

    public SupplyInfo(String supplier, String supplierPhoneNum, String supplierImage, String productNmae, String supplyPrice, String supplyNum, String productLocation, String description) {
        this.supplier = supplier;
        this.supplierPhoneNum = supplierPhoneNum;
        this.supplierImage = supplierImage;
        this.productNmae = productNmae;
        this.supplyPrice = supplyPrice;
        this.supplyNum = supplyNum;
        this.productLocation = productLocation;
        this.description = description;
    }

    public String getSupplier() {
        return supplier;
    }

    public void setSupplier(String supplier) {
        this.supplier = supplier;
    }

    public String getSupplierPhoneNum() {
        return supplierPhoneNum;
    }

    public void setSupplierPhoneNum(String supplierPhoneNum) {
        this.supplierPhoneNum = supplierPhoneNum;
    }

    public String getSupplierImage() {
        return supplierImage;
    }

    public void setSupplierImage(String supplierImage) {
        this.supplierImage = supplierImage;
    }

    public String getProductNmae() {
        return productNmae;
    }

    public void setProductNmae(String productNmae) {
        this.productNmae = productNmae;
    }

    public String getSupplyPrice() {
        return supplyPrice;
    }

    public void setSupplyPrice(String supplyPrice) {
        this.supplyPrice = supplyPrice;
    }

    public String getSupplyNum() {
        return supplyNum;
    }

    public void setSupplyNum(String supplyNum) {
        this.supplyNum = supplyNum;
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
