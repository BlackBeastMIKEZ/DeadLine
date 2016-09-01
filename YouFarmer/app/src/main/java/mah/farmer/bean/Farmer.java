package mah.farmer.bean;

import cn.bmob.v3.BmobObject;

/**用户信息类
 * Created by 黑色野兽迈特祖 on 2016/5/2.
 */
public class Farmer extends BmobObject {
    String userNickName;
    String avatar;

    public String getUserNickName() {
        return userNickName;
    }

    public void setUserNickName(String userNickName) {
        this.userNickName = userNickName;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }
}
