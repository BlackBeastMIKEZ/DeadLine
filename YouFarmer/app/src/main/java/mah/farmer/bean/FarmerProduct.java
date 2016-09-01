package mah.farmer.bean;

import java.io.Serializable;
import java.util.Comparator;

import cn.bmob.v3.BmobObject;

/**用户产品类
 * Created by 黑色野兽迈特祖 on 2016/4/21.
 */
public class FarmerProduct  implements Serializable{
    String name;

    String location;
    String commonprice;
    String myprice;
    String avata_md5;
    String tagOne;
    String tagTwo;
    String num;
    String sales;
    String salesNum;
    String salesTotalPrice;
    String description;
    String supplier;
    String supplierPhone;
    String supplierAddress;



    public FarmerProduct(String name, String location, String commonprice, String myprice, String avata_md5, String tagOne, String tagTwo, String num, String sales, String salesNum, String salesTotalPrice, String description, String supplier, String supplierPhone, String supplierAddress) {
        this.name = name;
        this.location = location;
        this.commonprice = commonprice;
        this.myprice = myprice;
        this.avata_md5 = avata_md5;
        this.tagOne = tagOne;
        this.tagTwo = tagTwo;
        this.num = num;
        this.sales = sales;
        this.salesNum = salesNum;
        this.salesTotalPrice = salesTotalPrice;
        this.description = description;
        this.supplier = supplier;
        this.supplierPhone = supplierPhone;
        this.supplierAddress = supplierAddress;
    }

    public String getAvata_md5() {
        return avata_md5;
    }

    public void setAvata_md5(String avata_md5) {
        this.avata_md5 = avata_md5;
    }

    public String getTagOne() {
        return tagOne;
    }

    public void setTagOne(String tagOne) {
        this.tagOne = tagOne;
    }

    public String getTagTwo() {
        return tagTwo;
    }

    public void setTagTwo(String tagTwo) {
        this.tagTwo = tagTwo;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public String getSales() {
        return sales;
    }

    public void setSales(String sales) {
        this.sales = sales;
    }

    public String getSalesNum() {
        return salesNum;
    }

    public void setSalesNum(String salesNum) {
        this.salesNum = salesNum;
    }

    public String getSalesTotalPrice() {
        return salesTotalPrice;
    }

    public void setSalesTotalPrice(String salesTotalPrice) {
        this.salesTotalPrice = salesTotalPrice;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSupplier() {
        return supplier;
    }

    public void setSupplier(String supplier) {
        this.supplier = supplier;
    }

    public String getSupplierPhone() {
        return supplierPhone;
    }

    public void setSupplierPhone(String supplierPhone) {
        this.supplierPhone = supplierPhone;
    }

    public String getSupplierAddress() {
        return supplierAddress;
    }

    public void setSupplierAddress(String supplierAddress) {
        this.supplierAddress = supplierAddress;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getCommonprice() {
        return commonprice;
    }

    public void setCommonprice(String commonprice) {
        this.commonprice = commonprice;
    }

    public String getMyprice() {
        return myprice;
    }

    public void setMyprice(String myprice) {
        this.myprice = myprice;
    }

}
