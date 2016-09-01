package mah.farmer.fragament;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import cn.bmob.im.util.BmobLog;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UploadFileListener;
import cn.pedant.SweetAlert.SweetAlertDialog;
import mah.farmer.FamerConstants;
import mah.farmer.util.CommonUtils;
import mah.farmer.util.ImageLoadOptions;
import mah.farmer.util.PhotoUtil;
import mah.farmer.bean.PhotoMd5;
import mah.farmer.http.FarmerConfig;
import mah.farmer.tools.MD5Tool;

/**发布土地出租信息的碎片
 * Created by 黑色野兽迈特祖 on 2016/4/26.
 */
public class PublishRentLandFragament extends Fragment  implements View.OnClickListener {
    private Context ctx;// 从activity当中得到的上下文

    private TextView rent_location, rent_publish;
    private EditText rent_num, rent_money, rent_tel, rent_desc;
    private ImageView rent_avatar;
    LinearLayout layout_all;
    RelativeLayout layout_choose;
    RelativeLayout layout_photo;
    PopupWindow avatorPop;
    public String filePath = "";
    String path;
    Bitmap newBitmap;
    boolean isFromCamera = false;// 区分拍照旋转
    int degree = 0;
    protected int mScreenWidth;
    String avatar_md5;

    private LocationClient mLocClient;
    public static String TAG = "LocTestDemo";
    public LocationClient mLocationClient = null;
    public GeofenceClient mGeofenceClient;

    public PublishRentLandFragament(Context ctx) {
        super();

        this.ctx = ctx;
    }

    @Override
    public void onAttach(Activity activity) {
        // TODO Auto-generated method stub
        super.onAttach(activity);
        // 初始化fragment时使用
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // fragment创建时使用
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = null;
        view = View.inflate(ctx, R.layout.fragament_publish_rent, null);
        return view;
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onActivityCreated(savedInstanceState);
        // activity创建时使用
        DisplayMetrics metric = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(metric);
        mScreenWidth = metric.widthPixels;
        initView();
        initLocal();

    }

    public void initView() {

        rent_location = (TextView) getActivity().findViewById(R.id.rent_location);
        rent_avatar = (ImageView) getActivity().findViewById(R.id.rent_avatar);
        rent_num = (EditText) getActivity().findViewById(R.id.rent_num);
        rent_money = (EditText) getActivity().findViewById(R.id.rent_money);
        rent_tel = (EditText) getActivity().findViewById(R.id.rent_tel);
        rent_desc = (EditText) getActivity().findViewById(R.id.rent_desc);
        rent_publish = (TextView) getActivity().findViewById(R.id.rent_publish);
        layout_all = (LinearLayout) getActivity().findViewById(R.id.layout_all);

        rent_avatar.setOnClickListener(this);
        rent_num.setOnClickListener(this);
        rent_money.setOnClickListener(this);
        rent_tel.setOnClickListener(this);
        rent_desc.setOnClickListener(this);
        rent_publish.setOnClickListener(this);


    }

    public void initLocal(){
        mLocationClient = new LocationClient( getActivity() );

        mLocationClient.setAK("697f50541f8d4779124896681cb6584d");
        mLocationClient.registerLocationListener( new MyLocationListenner() );
        mGeofenceClient = new GeofenceClient(getActivity());

        mLocClient = mLocationClient;
        LocationClientOption option = new LocationClientOption();
//		option.setOpenGps(true);
        option.setServiceName("com.baidu.location.service_v2.9");
        option.setPoiExtraInfo(true);
        option.setPriority(LocationClientOption.NetWorkFirst);
        option.setAddrType("all");
        option.setPoiNumber(10);
        option.disableCache(true);
        mLocClient.setLocOption(option);
        mLocClient.start();
        mLocClient.requestLocation();
    }

    public class MyLocationListenner implements BDLocationListener {
        @Override
        public void onReceiveLocation(BDLocation location) {
            if (location == null)
                return ;
            rent_location.setText(location.getAddrStr());
        }
        @Override
        public void onReceivePoi(BDLocation arg0) {
            // TODO Auto-generated method stub

        }


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rent_avatar:
                changAvatar();
                break;
            case R.id.rent_publish:
                publish();
                break;


        }

    }

    private void changAvatar() {
        showAvatarPop();
    }

    @SuppressWarnings("deprecation")
    private void showAvatarPop() {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.pop_showavator,
                null);
        layout_choose = (RelativeLayout) view.findViewById(R.id.layout_choose);
        layout_photo = (RelativeLayout) view.findViewById(R.id.layout_photo);
        layout_photo.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {

                // TODO Auto-generated method stub
                layout_choose.setBackgroundColor(getResources().getColor(
                        R.color.base_color_text_white));
                layout_photo.setBackgroundDrawable(getResources().getDrawable(
                        R.drawable.pop_bg_press));
                File dir = new File(FamerConstants.MyAvatarDir);
                if (!dir.exists()) {
                    dir.mkdirs();
                }
                // 原图
                File file = new File(dir, new SimpleDateFormat("yyMMddHHmmss")
                        .format(new Date()));
                filePath = file.getAbsolutePath();// 获取相片的保存路径
                Uri imageUri = Uri.fromFile(file);

                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
                startActivityForResult(intent,
                        FamerConstants.REQUESTCODE_UPLOADAVATAR_CAMERA);
            }
        });
        layout_choose.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub

                layout_photo.setBackgroundColor(getResources().getColor(
                        R.color.base_color_text_white));
                layout_choose.setBackgroundDrawable(getResources().getDrawable(
                        R.drawable.pop_bg_press));
                Intent intent = new Intent(Intent.ACTION_PICK, null);
                intent.setDataAndType(
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
                startActivityForResult(intent,
                        FamerConstants.REQUESTCODE_UPLOADAVATAR_LOCATION);
            }
        });

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
                if (resultCode == getActivity().RESULT_OK) {
                    if (!Environment.getExternalStorageState().equals(
                            Environment.MEDIA_MOUNTED)) {

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
                if (resultCode == getActivity().RESULT_OK) {
                    if (!Environment.getExternalStorageState().equals(
                            Environment.MEDIA_MOUNTED)) {

                        return;
                    }
                    isFromCamera = false;
                    uri = data.getData();
                    startImageAction(uri, 300, 300,
                            FamerConstants.REQUESTCODE_UPLOADAVATAR_CROP, true);
                } else {

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
        Intent intent = null;
        if (isCrop) {
            intent = new Intent("com.android.camera.action.CROP");
        } else {
            intent = new Intent(Intent.ACTION_GET_CONTENT, null);
        }
        intent.setDataAndType(uri, "image/*");
        intent.putExtra("crop", "true");
//        intent.putExtra("circleCrop",false);
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        intent.putExtra("outputX", outputX);
        intent.putExtra("outputY", outputY);
        intent.putExtra("scale", true);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
        intent.putExtra("return-data", true);
        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
        intent.putExtra("noFaceDetection", true); // no face detection
        startActivityForResult(intent, requestCode);
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
                rent_avatar.setImageBitmap(bitmap);
                // 保存图片
                String filename = new SimpleDateFormat("yyMMddHHmmss")
                        .format(new Date()) + ".png";
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
        BmobLog.i("头像地址：" + path);
        final BmobFile bmobFile = new BmobFile(new File(path));
        bmobFile.upload(getActivity(), new UploadFileListener() {

            @Override
            public void onSuccess() {
                // TODO Auto-generated method stub
                String url = bmobFile.getFileUrl(getActivity());
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

            }
        });
    }

    private void updateProductAvatar(final String url) {
        /**
         * 将url上传
         */
        PhotoMd5 photoMd5 = new PhotoMd5();
        photoMd5.setAvatar(url);
        photoMd5.setAvatar_md5(MD5Tool.md5(url));
        final ProgressDialog progress = new ProgressDialog(getActivity());
        progress.setMessage("正在上传...");
        progress.setCanceledOnTouchOutside(false);
        progress.show();
        photoMd5.save(getActivity(), new SaveListener() {
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
     *
     * @param avatar
     */
    private void refreshAvatar(String avatar) {
        avatar_md5=avatar;
        if (avatar != null && !avatar.equals("")) {
            ImageLoader.getInstance().displayImage(avatar, rent_avatar,
                    ImageLoadOptions.getOptions());
        } else {
            rent_avatar.setImageResource(R.drawable.default_default_head);
        }
    }


    /**
     * 执行发布的网络请求
     */
    private void publish() {
        if (TextUtils.isEmpty(rent_num.getText().toString())) {
            new SweetAlertDialog(getActivity(), SweetAlertDialog.ERROR_TYPE)
                    .setTitleText("输入错误")
                    .setContentText("亩数不能为空")
                    .show();
            return;
        }

        if (TextUtils.isEmpty(rent_money.getText().toString())) {
            new SweetAlertDialog(getActivity(), SweetAlertDialog.ERROR_TYPE)
                    .setTitleText("输入错误")
                    .setContentText("租金不能为空")
                    .show();

            return;
        }
        if (TextUtils.isEmpty(rent_desc.getText().toString())) {
            new SweetAlertDialog(getActivity(), SweetAlertDialog.ERROR_TYPE)
                    .setTitleText("输入错误")
                    .setContentText("描述不能为空")
                    .show();

            return;
        }
        if (TextUtils.isEmpty(rent_tel.getText().toString())) {
            new SweetAlertDialog(getActivity(), SweetAlertDialog.ERROR_TYPE)
                    .setTitleText("输入错误")
                    .setContentText("联系方式不能为空")
                    .show();

            return;
        }


        boolean isNetConnected = CommonUtils.isNetworkAvailable(getActivity());
        if (!isNetConnected) {
            new SweetAlertDialog(getActivity(), SweetAlertDialog.ERROR_TYPE)
                    .setTitleText("网络问题")
                    .setContentText("没有连接网络")
                    .show();

            return;
        }
        final ProgressDialog progress = new ProgressDialog(getActivity());
        progress.setMessage("正在发布...");
        progress.setCanceledOnTouchOutside(false);
        progress.show();
        /**
         * 执行发布网络请求
         */
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        JSONObject json = new JSONObject();//创建json对象
        try {
            json.put("renter", FarmerConfig.getCachedFarmerNickname(getActivity()));//使用URLEncoder.encode对特殊和不可见字符进行编码
            json.put("renterPhoneNum", rent_tel.getText().toString());//把数据put进json对象中
            json.put("landLocation", rent_location.getText().toString());//使用URLEncoder.encode对特殊和不可见字符进行编码
            json.put("landImage", avatar_md5);//把数据put进json对象中
            json.put("rentPrice", rent_money.getText().toString());//使用URLEncoder.encode对特殊和不可见字符进行编码
            json.put("rentNum", rent_num.getText().toString());//使用URLEncoder.encode对特殊和不可见字符进行编码
        }catch (Exception e){

        }
        JsonObjectRequest jsonObjectRequest=new JsonObjectRequest(Request.Method.POST,"http://192.168.235.22:8080/releaseLandRentInfo/",

                json,new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {

                try {
                    Toast.makeText(getActivity(), "RentPublish" + jsonObject.getString("reason"), Toast.LENGTH_LONG).show();
                    progress.dismiss();
                    JSONArray jsonarray = jsonObject.getJSONArray("data");
                    JSONObject products;

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                getActivity().finish();


            }
        },new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                new SweetAlertDialog(getActivity(), SweetAlertDialog.ERROR_TYPE)
                        .setTitleText("网络错误")
                        .setContentText("发布出租信息失败")
                        .show();
                progress.dismiss();

            }
        });
        requestQueue.add(jsonObjectRequest);

    }
}





