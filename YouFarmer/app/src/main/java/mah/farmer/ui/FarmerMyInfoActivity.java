package mah.farmer.ui;


import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.baidu.locTest.R;
import com.nostra13.universalimageloader.core.ImageLoader;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import cn.bmob.im.util.BmobLog;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UploadFileListener;
import cn.pedant.SweetAlert.SweetAlertDialog;
import mah.farmer.FamerConstants;
import mah.farmer.util.ImageLoadOptions;
import mah.farmer.util.PhotoUtil;
import mah.farmer.bean.Farmer;
import mah.farmer.http.FarmerConfig;

/**我的优农界面
 * Created by 黑色野兽迈特祖 on 2016/4/19.
 */
public class FarmerMyInfoActivity extends ActivityBase implements View.OnClickListener{
    private ImageView myphoto,mysettintg;
    private TextView myname;
    private LinearLayout shop_car,shop_buy,shop_sale,about_us,contact_us;

    /**
     * 调用摄像头还有相册以及裁剪头像所需要的控件
     */
    LinearLayout layout_all;
    RelativeLayout layout_choose;
    RelativeLayout layout_photo;
    PopupWindow avatorPop;
    public String filePath = "";
    String path;
    boolean isFromCamera = false;// 区分拍照旋转
    int degree = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_farmer_myinfo);
        initViews();
    }

    /**
     * 初始化控件
     */
    private void initViews() {
        swipeBackLayout.setEdgeTrackingEnabled(100);//设置该界面不能够侧滑退出
        myphoto = (ImageView) findViewById(R.id.myinfo_portrait);
        mysettintg = (ImageView) findViewById(R.id.myinfo_setting);
        myname = (TextView) findViewById(R.id.myinfo_name);
        shop_car = (LinearLayout) findViewById(R.id.myinfo_shop_cart);
        shop_buy = (LinearLayout) findViewById(R.id.myinfo_buy);
        shop_sale = (LinearLayout) findViewById(R.id.myinfo_sale);
        about_us = (LinearLayout) findViewById(R.id.myinfo_about_us);
        contact_us = (LinearLayout) findViewById(R.id.myinfo_contact_us);
        layout_all = (LinearLayout) findViewById(R.id.layout_all);

        getdata();//网络请求用户数据

        //设置监听
        myphoto.setOnClickListener(this);
        mysettintg.setOnClickListener(this);
        shop_car.setOnClickListener(this);
        shop_buy.setOnClickListener(this);
        shop_sale.setOnClickListener(this);
        about_us.setOnClickListener(this);
        contact_us.setOnClickListener(this);

        if (FarmerConfig.getCachedFarmerNickname(this) != null)
            myname.setText(FarmerConfig.getCachedFarmerNickname(this));



    }


    /**
     * 获取用户个人信息的网络请求
     */
    public void getdata() {

        RequestQueue requestQueue = Volley.newRequestQueue(FarmerMyInfoActivity.this);
        JSONObject json = new JSONObject();//创建json对象
        try {
            //把数据put进json对象
            json.put("userNickName", FarmerConfig.getCachedFarmerNickname(FarmerMyInfoActivity.this));
        }
        catch (Exception e){

        }

        //开始网络请求
        JsonObjectRequest jsonObjectRequest=new JsonObjectRequest(Request.Method.POST,"http://192.168.235.22:8080/showPersonality/",

                json,new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {



                try {
                    /**
                     * 成功回调，获取数据
                     */
                    Toast.makeText(FarmerMyInfoActivity.this, "myinfo"+jsonObject.getString("reason"), Toast.LENGTH_LONG).show();
                    JSONObject json=jsonObject.getJSONObject("data");

                    //将获取到的用户数据存入手机的数据库中
                    FarmerConfig.cacheFarmerTruename(FarmerMyInfoActivity.this,json.getString("userTrueName"));
                    FarmerConfig.cacheFarmerNickname(FarmerMyInfoActivity.this, json.getString("userNickName"));
                    FarmerConfig.cacheFarmerPassword(FarmerMyInfoActivity.this, json.getString("userPassword"));
                    FarmerConfig.cacheFarmerImage(FarmerMyInfoActivity.this, json.getString("userImage"));
                    FarmerConfig.cacheFarmerAddress(FarmerMyInfoActivity.this,json.getString("userAddress"));
                    FarmerConfig.cacheFarmerPhone(FarmerMyInfoActivity.this,json.getString("userPhoneNum"));

                    //加载用户头像
                    if (FarmerConfig.getCachedFarmerImage(FarmerMyInfoActivity.this) != null && !FarmerConfig.getCachedFarmerImage(FarmerMyInfoActivity.this).equals("")) {
                        ImageLoader.getInstance().displayImage(FarmerConfig.getCachedFarmerImage(FarmerMyInfoActivity.this), myphoto,
                                ImageLoadOptions.getOptions());
                    } else {
                        myphoto.setImageResource(R.drawable.default_default_head);
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }




            }
        },new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                /**
                 * 失败回调，提示用户失败请求
                 */
                new SweetAlertDialog(FarmerMyInfoActivity.this, SweetAlertDialog.ERROR_TYPE)
                        .setTitleText("网络错误")
                        .setContentText("获取我的优农信息失败")
                        .show();


            }
        });
        requestQueue.add(jsonObjectRequest);

    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.myinfo_portrait:
                showAvatarPop();
                break;
            case R.id.myinfo_setting:
                startActivity(new Intent(FarmerMyInfoActivity.this,FarmerSettingActivity.class));
                break;
            case R.id.myinfo_shop_cart:
                break;
            case R.id.myinfo_buy:
                startActivity(new Intent(FarmerMyInfoActivity.this,MyInfoDeliveryActivity.class));
                break;
            case R.id.myinfo_sale:
                startActivity(new Intent(FarmerMyInfoActivity.this,MyInfoSaleActivity.class));
                break;
            case R.id.myinfo_about_us:
                break;
            case R.id.myinfo_contact_us:
                startActivity(new Intent(FarmerMyInfoActivity.this,FarmerContactUsActivity.class));

                break;
            default:
                break;
        }

    }


    /**
     * 点击用户头像后弹出的界面选择框，选择是拍照修改头像还是相册修改头像
     */
    @SuppressWarnings("deprecation")
    private void showAvatarPop() {
        //加载布局到选择界面
        View view = LayoutInflater.from(this).inflate(R.layout.pop_showavator,
                null);
        //初始化选择界面的空间
        layout_choose = (RelativeLayout) view.findViewById(R.id.layout_choose);//选择相册
        layout_photo = (RelativeLayout) view.findViewById(R.id.layout_photo);//选择拍照

        //设置拍照监听
        layout_photo.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                ShowLog("点击拍照");
                // TODO Auto-generated method stub
                //设置空间背景
                layout_choose.setBackgroundColor(getResources().getColor(
                        R.color.base_color_text_white));
                layout_photo.setBackgroundDrawable(getResources().getDrawable(
                        R.drawable.pop_bg_press));

                //新建文件类，存储图片文件路径
                File dir = new File(FamerConstants.MyAvatarDir);
                if (!dir.exists()) {
                    dir.mkdirs();
                }
                // 原图，以时间作为图片命名
                File file = new File(dir, new SimpleDateFormat("yyMMddHHmmss")
                        .format(new Date()));
                filePath = file.getAbsolutePath();// 获取相片的保存路径

                Uri imageUri = Uri.fromFile(file);//生成相片的uri

                //选择成功后，调用手机自带的照相程序，在onactivityResult方法中处理
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
                startActivityForResult(intent,
                        FamerConstants.REQUESTCODE_UPLOADAVATAR_CAMERA);
            }
        });

        //设置相册监听
        layout_choose.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                ShowLog("点击相册");
                //初始化界面背景
                layout_photo.setBackgroundColor(getResources().getColor(
                        R.color.base_color_text_white));
                layout_choose.setBackgroundDrawable(getResources().getDrawable(
                        R.drawable.pop_bg_press));

                //直接跳转到手机自带的相册程序
                Intent intent = new Intent(Intent.ACTION_PICK, null);
                intent.setDataAndType(
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
                startActivityForResult(intent,
                        FamerConstants.REQUESTCODE_UPLOADAVATAR_LOCATION);
            }
        });

        //选择界面上方的黑暗遮罩体现动画效果
        avatorPop = new PopupWindow(view, mScreenWidth, 600);
        avatorPop.setTouchInterceptor(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_OUTSIDE) {
                    avatorPop.dismiss();
                    return true;
                }
                return false;
            }
        });

        avatorPop.setWidth(WindowManager.LayoutParams.MATCH_PARENT);
        avatorPop.setHeight(WindowManager.LayoutParams.WRAP_CONTENT);
        avatorPop.setTouchable(true);
        avatorPop.setFocusable(true);
        avatorPop.setOutsideTouchable(true);
        avatorPop.setBackgroundDrawable(new BitmapDrawable());
        // 动画效果 从底部弹起
        avatorPop.setAnimationStyle(R.style.Animations_GrowFromBottom);
        avatorPop.showAtLocation(layout_all, Gravity.BOTTOM, 0, 0);
    }


    /**
     * 返回界面时所调用的方法
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            /**
             * 判断来源是什么，是拍照，相册还是裁剪程序传来的数据
             */
            case FamerConstants.REQUESTCODE_UPLOADAVATAR_CAMERA:// 拍照修改头像
                if (resultCode == RESULT_OK) {
                    if (!Environment.getExternalStorageState().equals(
                            Environment.MEDIA_MOUNTED)) {
                        ShowToast("SD不可用");
                        return;
                    }


                    isFromCamera = true;//表示来源于照相所得的图片
                    File file = new File(filePath);//获取当前路径的文件
                    degree = PhotoUtil.readPictureDegree(file.getAbsolutePath());//得到拍照后的角度
                    Log.i("life", "拍照后的角度：" + degree);

                    /**
                     * 跳转到裁剪程序
                     */
                    startImageAction(Uri.fromFile(file), 300, 300,
                            FamerConstants.REQUESTCODE_UPLOADAVATAR_CROP, true);
                }
                break;
            case FamerConstants.REQUESTCODE_UPLOADAVATAR_LOCATION:// 本地相册修改头像
                if (avatorPop != null) {
                    avatorPop.dismiss();
                }
                Uri uri = null;//新建uri，存储相册裁剪后的意图
                if (data == null) {//判断用户是否选择了图片，没有则返回
                    return;
                }
                if (resultCode == RESULT_OK) {
                    if (!Environment.getExternalStorageState().equals(
                            Environment.MEDIA_MOUNTED)) {
                        ShowToast("SD不可用");
                        return;
                    }
                    isFromCamera = false;
                    uri = data.getData();//得到数据
                    startImageAction(uri, 300, 300,  //跳转到裁剪程序
                            FamerConstants.REQUESTCODE_UPLOADAVATAR_CROP, true);
                } else {
                    ShowToast("照片获取失败");
                }

                break;
            case FamerConstants.REQUESTCODE_UPLOADAVATAR_CROP:// 裁剪头像返回
                // TODO sent to crop
                if (avatorPop != null) {
                    avatorPop.dismiss();
                }
                if (data == null) {
                    // Toast.makeText(this, "取消选择", Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    saveCropAvator(data);
                }
                // 初始化文件路径
                filePath = "";
                // 上传头像
                uploadAvatar();
                break;
            default:
                break;

        }
    }

    /**
     * 调用手机裁剪程序后进行的裁剪操作
     * @param uri
     * @param outputX
     * @param outputY
     * @param requestCode
     * @param isCrop
     */
    private void startImageAction(Uri uri, int outputX, int outputY,
                                  int requestCode, boolean isCrop) {
        Intent intent = null;//初始化回调intent
        if (isCrop) { //判断是否裁剪，是则裁剪，不是则直接是原来图片
            intent = new Intent("com.android.camera.action.CROP");
        } else {
            intent = new Intent(Intent.ACTION_GET_CONTENT, null);
        }

        /**
         * 对回调的intent进行赋值
         */
        intent.setDataAndType(uri, "image/*");//设置图片网络地址和路径
        intent.putExtra("crop", "true");//设置图片圆角
//        intent.putExtra("circleCrop",false);
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        intent.putExtra("outputX", outputX); //设置裁剪框的x值
        intent.putExtra("outputY", outputY);//设置裁剪框的y值
        intent.putExtra("scale", true);  //设置是否放大
        intent.putExtra(MediaStore.EXTRA_OUTPUT, uri); //设置媒体文件输出
        intent.putExtra("return-data", true);
        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());//设置文件输出格式
        intent.putExtra("noFaceDetection", true);
        startActivityForResult(intent, requestCode);//执行回调，跳转到onActivityResult方法
    }

    /**
     * 保存裁剪的头像
     *
     * @param data
     */
    private void saveCropAvator(Intent data) {
        Bundle extras = data.getExtras();
        if (extras != null) {
            Bitmap bitmap = extras.getParcelable("data");
            Log.i("life", "avatar - bitmap = " + bitmap);
            if (bitmap != null) {
                bitmap = PhotoUtil.toRoundCorner(bitmap, 0);
                if (isFromCamera && degree != 0) {
                    bitmap = PhotoUtil.rotaingImageView(degree, bitmap);
                }
                myphoto.setImageBitmap(bitmap);
                // 保存图片
                String filename = new SimpleDateFormat("yyMMddHHmmss")
                        .format(new Date())+".png";
                path = FamerConstants.MyAvatarDir + filename;
                PhotoUtil.saveBitmap(FamerConstants.MyAvatarDir, filename,
                        bitmap, true);
                // 上传头像
                if (bitmap != null && bitmap.isRecycled()) {
                    bitmap.recycle();
                }
            }
        }
    }

    private void uploadAvatar() {

        /**
         * 调用服务上传头像
         */
        final BmobFile bmobFile = new BmobFile(new File(path));
        bmobFile.upload(this, new UploadFileListener() {

            @Override
            public void onSuccess() {
                // TODO Auto-generated method stub

                String url = bmobFile.getFileUrl(FarmerMyInfoActivity.this);//成功回调后，生成头像的文件网络地址
                // 更新BmobUser对象
                updateProductAvatar(url);
            }

            @Override
            public void onProgress(Integer arg0) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onFailure(int arg0, String msg) {
                // TODO Auto-generated method stub
                ShowToast("头像上传失败：" + msg);
            }
        });
    }

    /**
     * 将url上传至后台，更改用户数据
     */
    private void updateProductAvatar(final String url) {

        FarmerConfig.cacheFarmerImage(FarmerMyInfoActivity.this,url);
        final ProgressDialog progress = new ProgressDialog(FarmerMyInfoActivity.this);
        progress.setMessage("正在上传...");
        progress.setCanceledOnTouchOutside(false);
        progress.show();

        /**
         * 执行更改用户信息的网络请求
         */
        RequestQueue requestQueue = Volley.newRequestQueue(FarmerMyInfoActivity.this);
        JSONObject json = new JSONObject();//创建json对象
        try {
            //把数据put进json对象中
            json.put("userNickName",  FarmerConfig.getCachedFarmerNickname(FarmerMyInfoActivity.this) );
            json.put("userImage", url );
        }catch (Exception e){

        }

        //开始执行
        JsonObjectRequest jsonObjectRequest=new JsonObjectRequest(Request.Method.POST,"http://192.168.235.22:8080/upLoadUserImage/",

                json,new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {

                try {
                    //成功回调后，更新当前界面的头像
                    refreshAvatar(url);
                    Toast.makeText(FarmerMyInfoActivity.this,"上传头像:"+jsonObject.getString("reason"),Toast.LENGTH_LONG).show();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                progress.dismiss();


            }
        },new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                //失败回掉，提示用户错误
                new SweetAlertDialog(FarmerMyInfoActivity.this, SweetAlertDialog.ERROR_TYPE)
                        .setTitleText("网络错误")
                        .setContentText("上传失败")
                        .show();
                progress.dismiss();
            }
        });
        requestQueue.add(jsonObjectRequest);



    }

    /**
     * 对用户头像进行当前界面的更新
     * @param avatar
     */
    private void refreshAvatar(String avatar) {
        if (avatar != null && !avatar.equals("")) {
            ImageLoader.getInstance().displayImage(avatar, myphoto,
                    ImageLoadOptions.getOptions());
        } else {
            myphoto.setImageResource(R.drawable.default_default_head);
        }
    }


}
