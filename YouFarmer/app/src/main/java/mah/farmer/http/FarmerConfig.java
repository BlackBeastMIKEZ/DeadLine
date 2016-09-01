package mah.farmer.http;

import android.content.Context;
import android.content.SharedPreferences.Editor;

/**系统缓存
 * Created by 黑色野兽迈特祖 on 2016/4/28.
 */
public class FarmerConfig {


    public static final String APP_ID = "com.quanta.farmerfarmer";
    public static final String FARMER_NIKENAME = "nickname";
    public static final String FARMER_TRUENAME = "truename";
    public static final String FARMER_PHONE = "phone";
    public static final String FARMER_PASSWORD = "password";
    public static final String FARMER_IMAGE = "avatar";
    public static final String ISFIRSTRUN = "isfirstrun";
    public static final String FARMER_ADDRESS = "address";




    public static String getCachedFarmerAddress(Context context){
        return context.getSharedPreferences(APP_ID, Context.MODE_PRIVATE).getString(FARMER_ADDRESS, null);
    }

    //缓存地址
    public static void cacheFarmerAddress(Context context,String token){
        Editor e = context.getSharedPreferences(APP_ID, Context.MODE_PRIVATE).edit();
        e.putString(FARMER_ADDRESS, token);
        e.commit();
    }


    public static String getCachedFarmerImage(Context context){
        return context.getSharedPreferences(APP_ID, Context.MODE_PRIVATE).getString(FARMER_IMAGE, null);
    }

    //缓存用户头像url
    public static void cacheFarmerImage(Context context,String token){
        Editor e = context.getSharedPreferences(APP_ID, Context.MODE_PRIVATE).edit();
        e.putString(FARMER_IMAGE, token);
        e.commit();
    }


    public static String getCachedFarmerPassword(Context context){
        return context.getSharedPreferences(APP_ID, Context.MODE_PRIVATE).getString(FARMER_PASSWORD, null);
    }

    //缓存用户密码
    public static void cacheFarmerPassword(Context context,String token){
        Editor e = context.getSharedPreferences(APP_ID, Context.MODE_PRIVATE).edit();
        e.putString(FARMER_PASSWORD, token);
        e.commit();
    }


    public static Boolean getCachedISfirstRun(Context context){
        return context.getSharedPreferences(APP_ID, Context.MODE_PRIVATE).getBoolean(ISFIRSTRUN,true);
    }

    //缓存是否第一次使用
    public static void cacheFarmerISfirstRun(Context context,Boolean token){
        Editor e = context.getSharedPreferences(APP_ID, Context.MODE_PRIVATE).edit();
        e.putBoolean(ISFIRSTRUN, token);
        e.commit();
    }

    public static String getCachedFarmerNickname(Context context){
        return context.getSharedPreferences(APP_ID, Context.MODE_PRIVATE).getString(FARMER_NIKENAME, null);
    }

    //缓存用户账号名
    public static void cacheFarmerNickname(Context context,String token){
        Editor e = context.getSharedPreferences(APP_ID, Context.MODE_PRIVATE).edit();
        e.putString(FARMER_NIKENAME, token);
        e.commit();
    }
    public static String getCachedFarmerPhone(Context context){
        return context.getSharedPreferences(APP_ID, Context.MODE_PRIVATE).getString(FARMER_PHONE, null);
    }

    //缓存用户联系方式
    public static void cacheFarmerPhone(Context context,String phoneNum){
        Editor e = context.getSharedPreferences(APP_ID, Context.MODE_PRIVATE).edit();
        e.putString(FARMER_PHONE, phoneNum);
        e.commit();
    }
    public static String getCachedFarmerPhoTruename(Context context){
        return context.getSharedPreferences(APP_ID, Context.MODE_PRIVATE).getString(FARMER_TRUENAME, null);
    }

    //缓存用户真实姓名
    public static void cacheFarmerTruename(Context context,String phoneNum){
        Editor e = context.getSharedPreferences(APP_ID, Context.MODE_PRIVATE).edit();
        e.putString(FARMER_TRUENAME, phoneNum);
        e.commit();
    }
}
