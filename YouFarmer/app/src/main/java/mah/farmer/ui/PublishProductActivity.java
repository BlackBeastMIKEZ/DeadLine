package mah.farmer.ui;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
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
import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.GeofenceClient;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.nostra13.universalimageloader.core.ImageLoader;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import cn.bmob.im.util.BmobLog;
import cn.bmob.v3.Bmob;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UploadFileListener;
import cn.pedant.SweetAlert.SweetAlertDialog;
import mah.farmer.FamerConstants;
import mah.farmer.util.CommonUtils;
import mah.farmer.util.ImageLoadOptions;
import mah.farmer.util.PhotoUtil;
import mah.farmer.adapter.SpinnerAdapter;
import mah.farmer.bean.PhotoMd5;
import mah.farmer.http.FarmerConfig;
import mah.farmer.tools.MD5Tool;
import mah.farmer.view.MyPopupWindow;

/**发布产品的界面
 * Created by 黑色野兽迈特祖 on 2016/4/27.
 */
public class PublishProductActivity extends ActivityBase implements View.OnClickListener{
    //相关控件
    private ImageView pro_avatar;
    private EditText pro_name,pro_danjia,pro_num ,pro_tel,pro_desc;
    private TextView pro_location,publish,tag1_content,tag2_content;
    private RelativeLayout tag1,tag2;

    /**
     * 调用摄像头还有相册以及裁剪头像所需要的控件
     */
    LinearLayout layout_all;
    RelativeLayout layout_choose;
    RelativeLayout layout_photo;
    PopupWindow avatorPop;
    public String filePath = "";
    String path;
    Bitmap newBitmap;
    boolean isFromCamera = false;// 区分拍照旋转
    int degree = 0;

    String avatar_md5;
    private List<String> mainItem;
    private List<String> shuiguoItem;
    private List<String> shucaiItem;

    private LocationClient mLocClient;
    public static String TAG = "LocTestDemo";
    public LocationClient mLocationClient = null;
    public GeofenceClient mGeofenceClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_publish_product);
        Bmob.initialize(this, "42c944ae0d564538cff35f55e0e45d5c");
        initView();
        initLocal();
    }


    /**
     * 初始化控件
     */
    private void initView() {
        pro_avatar= (ImageView) findViewById(R.id.product_avatar);
        pro_name= (EditText) findViewById(R.id.product_name);
        pro_danjia= (EditText) findViewById(R.id.product_danjia);
        pro_num= (EditText) findViewById(R.id.product_num);
        pro_location= (TextView) findViewById(R.id.product_location);
        pro_tel= (EditText) findViewById(R.id.product_tel);
        pro_desc= (EditText) findViewById(R.id.product_desc);
        publish= (TextView) findViewById(R.id.publish);
        layout_all = (LinearLayout) findViewById(R.id.layout_all);
        tag1= (RelativeLayout) findViewById(R.id.tag1);
        tag2= (RelativeLayout) findViewById(R.id.tag2);

        tag1_content= (TextView) findViewById(R.id.tag1_content);
        tag2_content= (TextView) findViewById(R.id.tag2_content);
        mainItem =new ArrayList<>();
        shucaiItem=new ArrayList<>();
        shuiguoItem=new ArrayList<>();
        mainItem.add("水果");
        mainItem.add("蔬菜");
        shuiguoItem.add("浆果类");
        shuiguoItem.add("瓜果类");
        shuiguoItem.add("橘果类");
        shucaiItem.add("瓜类");
        shucaiItem.add("叶菜类");
        shucaiItem.add("茄果类");


        //设置监听
        pro_avatar.setOnClickListener(this);
        publish.setOnClickListener(this);
        tag1.setOnClickListener(this);
        tag2.setOnClickListener(this);
        findViewById(R.id.farmer_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    public void initLocal(){
        mLocationClient = new LocationClient( this );//相关的定位http类
        mLocationClient.setAK("697f50541f8d4779124896681cb6584d");//设置申请到的百度API KEY
        mLocationClient.registerLocationListener( new MyLocationListenner() );//注册网络监听器
        mGeofenceClient = new GeofenceClient(this);
        mLocClient = mLocationClient;
        LocationClientOption option = new LocationClientOption();//得到地址选择器
        option.setServiceName("com.baidu.location.service_v2.9");//设置服务名
        option.setPoiExtraInfo(true);
        option.setPriority(LocationClientOption.NetWorkFirst);//设置网络优先
        option.setAddrType("all");//设置加载所有地址类型
        option.setPoiNumber(10);
        option.disableCache(true);
        mLocClient.setLocOption(option);//将定位的http类加载地址选择器
        mLocClient.start();//开始定位请求
        mLocClient.requestLocation();
    }

    /**
     * 网络定位的监听器，将结果显示到界面上
     */
    public class MyLocationListenner implements BDLocationListener {
        @Override
        public void onReceiveLocation(BDLocation location) {
            if (location == null)
                return ;
            pro_location.setText(location.getAddrStr());//将得到的地址数据显示在界面
        }
        @Override
        public void onReceivePoi(BDLocation arg0) {
            // TODO Auto-generated method stub

        }


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.product_avatar:
                changAvatar();
                break;
            case R.id.publish:
                farmerPublish();
                break;
            case R.id.tag1:
                changeTag1Content();
                break;
            case R.id.tag2:
                changeTag2Content();
                break;
        }
    }


    /**
     * 下拉选择框，将选择的类别显示到界面上
     */
    private void changeTag1Content() {

        //加载下拉选择框的布局
        MyPopupWindow window=new MyPopupWindow(this,tag1.getWidth(), mainItem);
        window.showAsDropDown(tag1, 0, 0);
        window.setOnItemClickListener(new SpinnerAdapter.onItemClickListener() {
            public void click(int position, View view) {
                tag1_content.setText(mainItem.get(position));
                if (position==1)
                    tag2_content.setText("瓜类");
                if (position==0)
                    tag2_content.setText("浆果类");
                Toast.makeText(PublishProductActivity.this, "你点击了第"+(position+1)+"项", Toast.LENGTH_SHORT).show();
            }
        });

    }

    /**
     * 下拉选择框，将选择的类别显示到界面上
     */
    private void changeTag2Content() {

        /**
         * 针对tag1进行tag2内容选择的识别
         */
        if (tag1_content.getText().toString().equals("水果")) {
            MyPopupWindow window = new MyPopupWindow(this, tag2.getWidth(), shuiguoItem);
            window.showAsDropDown(tag2, 0, 0);
            window.setOnItemClickListener(new SpinnerAdapter.onItemClickListener() {
                public void click(int position, View view) {
                    tag2_content.setText(shuiguoItem.get(position));
                    Toast.makeText(PublishProductActivity.this, "你点击了第" + (position + 1) + "项", Toast.LENGTH_SHORT).show();
                }
            });
        }
        else{
            MyPopupWindow window = new MyPopupWindow(this, tag2.getWidth(), shucaiItem);
            window.showAsDropDown(tag2, 0, 0);
            window.setOnItemClickListener(new SpinnerAdapter.onItemClickListener() {
                public void click(int position, View view) {
                    tag2_content.setText(shucaiItem.get(position));
                    Toast.makeText(PublishProductActivity.this, "你点击了第" + (position + 1) + "项", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }


    private void changAvatar() {
        showAvatarPop();
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


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case FamerConstants.REQUESTCODE_UPLOADAVATAR_CAMERA:// 拍照修改头像
                if (resultCode == RESULT_OK) {
                    if (!Environment.getExternalStorageState().equals(
                            Environment.MEDIA_MOUNTED)) {
                        ShowToast("SD不可用");
                        return;
                    }
                    isFromCamera = true;
                    File file = new File(filePath);
                    degree = PhotoUtil.readPictureDegree(file.getAbsolutePath());
                    Log.i("life", "拍照后的角度：" + degree);
                    startImageAction(Uri.fromFile(file), 300, 300,
                            FamerConstants.REQUESTCODE_UPLOADAVATAR_CROP, true);
                }
                break;
            case FamerConstants.REQUESTCODE_UPLOADAVATAR_LOCATION:// 本地修改头像
                if (avatorPop != null) {
                    avatorPop.dismiss();
                }
                Uri uri = null;
                if (data == null) {
                    return;
                }
                if (resultCode == RESULT_OK) {
                    if (!Environment.getExternalStorageState().equals(
                            Environment.MEDIA_MOUNTED)) {
                        ShowToast("SD不可用");
                        return;
                    }
                    isFromCamera = false;
                    uri = data.getData();
                    startImageAction(uri, 300, 300,
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
                pro_avatar.setImageBitmap(bitmap);
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

    /**
     * 上传头像
     */
    private void uploadAvatar() {
        BmobLog.i("头像地址：" + path);
        final BmobFile bmobFile = new BmobFile(new File(path));
        bmobFile.upload(this, new UploadFileListener() {

            @Override
            public void onSuccess() {
                // TODO Auto-generated method stub
                String url = bmobFile.getFileUrl(PublishProductActivity.this);
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

    private void updateProductAvatar(final String url) {
        /**
         * 将url上传
         */
        PhotoMd5 photoMd5=new PhotoMd5();
        photoMd5.setAvatar(url);
        photoMd5.setAvatar_md5(MD5Tool.md5(url));
        final ProgressDialog progress = new ProgressDialog(PublishProductActivity.this);
        progress.setMessage("正在上传...");
        progress.setCanceledOnTouchOutside(false);
        progress.show();
        photoMd5.save(PublishProductActivity.this,new SaveListener() {
            @Override
            public void onSuccess() {
                refreshAvatar(url);
                progress.dismiss();
            }

            @Override
            public void onFailure(int i, String s) {

            }
        });

    }

    /**
     * 对产品头像进行更新
     * @param avatar
     */
    private void refreshAvatar(String avatar) {
        avatar_md5= avatar;
//        Log.i("图片",avatar);
        if (avatar != null && !avatar.equals("")) {
            ImageLoader.getInstance().displayImage(avatar, pro_avatar,
                    ImageLoadOptions.getOptions());
        } else {
            pro_avatar.setImageResource(R.drawable.default_default_head);
        }
    }


    /**
     * 发布产品的网络请求
     */
    private void farmerPublish() {

        //得到用户输入的数据
        String name=pro_name.getText().toString();
        String  danjia=pro_danjia.getText().toString();
        String  num=pro_num.getText().toString();
        String  desc=pro_desc.getText().toString();
        String  tel=pro_tel.getText().toString();
        String  location=pro_location.getText().toString();

        //下面是判断各个输入是否为空以及网络是否连接
        if (TextUtils.isEmpty(name)) {
            new SweetAlertDialog(this, SweetAlertDialog.ERROR_TYPE)
                    .setTitleText("输入错误")
                    .setContentText("产品名不能为空")
                    .show();
            return;
        }

        if(TextUtils.isEmpty(danjia)){
            new SweetAlertDialog(this, SweetAlertDialog.ERROR_TYPE)
                    .setTitleText("输入错误")
                    .setContentText("单价不能为空")
                    .show();

            return;
        }
        if (TextUtils.isEmpty(num)) {
            new SweetAlertDialog(this, SweetAlertDialog.ERROR_TYPE)
                    .setTitleText("输入错误")
                    .setContentText("数量不能为空")
                    .show();

            return;
        }
        if(TextUtils.isEmpty(location)){
            new SweetAlertDialog(this, SweetAlertDialog.ERROR_TYPE)
                    .setTitleText("输入错误")
                    .setContentText("位置不能为空")
                    .show();

            return;
        }
        if(TextUtils.isEmpty(tel)){
            new SweetAlertDialog(this, SweetAlertDialog.ERROR_TYPE)
                    .setTitleText("输入错误")
                    .setContentText("联系方式不能为空")
                    .show();
            return;
        }
        if(TextUtils.isEmpty(desc)){
            new SweetAlertDialog(this, SweetAlertDialog.ERROR_TYPE)
                    .setTitleText("输入错误")
                    .setContentText("详情不能为空")
                    .show();


            return;
        }


        boolean isNetConnected = CommonUtils.isNetworkAvailable(this);
        if(!isNetConnected){
            new SweetAlertDialog(this, SweetAlertDialog.ERROR_TYPE)
                    .setTitleText("网络问题")
                    .setContentText("没有连接网络")
                    .show();

            return;
        }

        final ProgressDialog progress = new ProgressDialog(PublishProductActivity.this);
        progress.setMessage("正在发布...");
        progress.setCanceledOnTouchOutside(false);
        progress.show();
        RequestQueue requestQueue = Volley.newRequestQueue(PublishProductActivity.this);
        JSONObject json = new JSONObject();//创建json对象
        try {
            //把数据put进json对象中
            json.put("productName", pro_name.getText().toString());
            json.put("productImage", avatar_md5);
            json.put("productTagOne",tag1_content.getText().toString());
            json.put("productTagTwo", tag2_content.getText().toString());
            json.put("youNongPrice", pro_danjia.getText().toString() );
            json.put("productNum",pro_num.getText().toString());
            json.put("productLocation", pro_location.getText().toString());
            json.put("description", pro_desc.getText().toString());
            json.put("supplierPhone", pro_tel.getText().toString());
            json.put("userNickName", FarmerConfig.getCachedFarmerNickname(PublishProductActivity.this));
            json.put("datetime", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        }catch (Exception e){

        }

        //开始执行
        JsonObjectRequest jsonObjectRequest=new JsonObjectRequest(Request.Method.POST,"http://192.168.235.22:8080/releaseProduct/",

                json,new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {

                /**
                 * 成功回调，打印数据
                 */

                try {
                    progress.dismiss();
                    Toast.makeText(PublishProductActivity.this, "ProductPublish " + jsonObject.getString("reason"), Toast.LENGTH_LONG).show();
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        },new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {

                /**
                 * 失败回调，提示用户
                 */
                new SweetAlertDialog(PublishProductActivity.this, SweetAlertDialog.ERROR_TYPE)
                        .setTitleText("网络错误")
                        .setContentText("发布淘农信息失败")
                        .show();
                progress.dismiss();

            }
        });
        requestQueue.add(jsonObjectRequest);

    }

}
