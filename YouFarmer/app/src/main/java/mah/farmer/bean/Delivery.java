package mah.farmer.bean;

import cn.bmob.v3.BmobObject;

/**订单类
 * Created by 黑色野兽迈特祖 on 2016/4/27.
 */
public class Delivery  extends BmobObject{
    String userNick;
    String masterNick;
    String type;
    String time;
    String productName;
    String tradeNum;
    String tradeMoney;
    String productDanjia;

    public Delivery(String userNick, String masterNick, String type, String time, String productName, String tradeNum, String tradeMoney, String productDanjia) {

        this.userNick = userNick;
        this.masterNick = masterNick;
        this.type = type;
        this.time = time;
        this.productName = productName;
        this.tradeNum = tradeNum;
        this.tradeMoney = tradeMoney;
        this.productDanjia = productDanjia;
    }

    public String getUserNick() {
        return userNick;
    }

    public void setUserNick(String userNick) {
        this.userNick = userNick;
    }

    public String getMasterNick() {
        return masterNick;
    }

    public void setMasterNick(String masterNick) {
        this.masterNick = masterNick;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getTradeNum() {
        return tradeNum;
    }

    public void setTradeNum(String tradeNum) {
        this.tradeNum = tradeNum;
    }

    public String getTradeMoney() {
        return tradeMoney;
    }

    public void setTradeMoney(String tradeMoney) {
        this.tradeMoney = tradeMoney;
    }

    public String getProductDanjia() {
        return productDanjia;
    }

    public void setProductDanjia(String productDanjia) {
        this.productDanjia = productDanjia;
    }
}
