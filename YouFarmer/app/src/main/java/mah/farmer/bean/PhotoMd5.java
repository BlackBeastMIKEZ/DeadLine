package mah.farmer.bean;

import cn.bmob.v3.BmobObject;

/**存储图片类
 * Created by 黑色野兽迈特祖 on 2016/4/29.
 */
public class PhotoMd5 extends BmobObject {
    String avatar_md5;
    String avatar;

    public String getAvatar_md5() {
        return avatar_md5;
    }

    public void setAvatar_md5(String avatar_md5) {
        this.avatar_md5 = avatar_md5;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }
}
