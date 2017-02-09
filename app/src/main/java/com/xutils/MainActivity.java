package com.xutils;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.lidroid.xutils.BitmapUtils;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.lidroid.xutils.view.annotation.ContentView;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

import java.text.SimpleDateFormat;
import java.util.Date;

/****
 *
 *
 * Xutils框架的基本使用
 *
 * 由于版本过高HttpRequest.HttpMethod  淘汰
 *
 * 故在  build.gradle中加
 * android {
    useLibrary 'org.apache.http.legacy'
    }

 *
 *
 */
@ContentView(R.layout.activity_xutils)//// 加载布局文件
public class MainActivity extends Activity {


    // 初始化控件
    @ViewInject(R.id.btnUtil1)
    Button btn1;
    @ViewInject(R.id.btnUtil2)
    private Button btn2;
    @ViewInject(R.id.btnUtil3)
    private Button btn3;
    @ViewInject(R.id.btnUtil4)
    private Button btn4;
    @ViewInject(R.id.btnUtil5)
    private Button btn5;
    @ViewInject(R.id.btnUtilText1)
    private TextView text1;
    @ViewInject(R.id.imgimg)
    private ImageView mImageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ViewUtils.inject(this); // 注入view和事件
    }

    @OnClick(R.id.btnUtil1)
    // 点击事件
    public void btnClick(View v) {
        Toast.makeText(getApplicationContext(), "get", Toast.LENGTH_LONG).show();
    }

    @OnClick(R.id.btnUtil2)
    public void btnClick2(View v) {
        image();
    }

    @OnClick(R.id.btnUtil3)
    public void btnClick3(View v) {
        get1();
    }

    @OnClick(R.id.btnUtil4)
    // 点击事件
    public void btnClick4(View v) {
        get2();
    }

    @OnClick(R.id.btnUtil5)
    public void btnClick5(View v) {
        post1();
    }

    @OnClick(R.id.btnUtil6)
    public void btnClick6(View v) {
        Intent intent = new Intent(MainActivity.this, DbActivity.class);
        startActivity(intent);
    }

    /***
     *
     * 网络请求get
     */
    private void get1() {
        HttpUtils http = new HttpUtils();
        http.send(HttpRequest.HttpMethod.GET, "http://www.lidroid.com",
                new RequestCallBack<String>() {
                    @Override
                    public void onLoading(long total, long current,
                                          boolean isUploading) {
                        text1.setText(current + "/" + total);
                    }

                    @Override
                    public void onSuccess(ResponseInfo<String> responseInfo) {
                        text1.setText(responseInfo.result);
                    }

                    @Override
                    public void onStart() {
                    }

                    @Override
                    public void onFailure(HttpException error, String msg) {
                    }
                });
    }

    private void get2() {
        // TODO Auto-generated method stub
        HttpUtils http = new HttpUtils(10000);// 放回html数据和api数据--10000代表10s超时
        http.configCurrentHttpCacheExpiry(5000);// 设置缓存5s,5秒内直接返回上次成功请求的结果。
        http.configResponseTextCharset("UTF-8");// 配置HTTP响应的文本编码
        http.send(HttpRequest.HttpMethod.GET,
                "https://route.showapi.com/255-1?showapi_appid=11548&type=10&page=1&maxResult=20&"
                        + "showapi_timestamp="
                        + str
                        + "&showapi_sign=bb1d15ab7ce646ec87cc89d684ca4bcb",
                new RequestCallBack<String>() {
                    @Override
                    public void onLoading(long total, long current,
                                          boolean isUploading) {// 更新任务进度（需覆盖重写）
                        text1.setText(current + "/" + total);
                    }

                    @Override
                    public void onSuccess(ResponseInfo<String> responseInfo) {
                        text1.setText(responseInfo.result);// 网络请求执行成功（需覆盖重写）//接口回调返回数据
                    }

                    @Override
                    public void onStart() {// 开始发送网络请求（需覆盖重写）
                    }

                    @Override
                    public void onFailure(HttpException error, String msg) {
                    }
                });
    }

    /***
     * post请求
     */

    private void post1() {
       RequestParams params = new RequestParams("utf-8");
        params.addBodyParameter("showapi_appid", "11548");
        params.addBodyParameter("showapi_sign",
                "bb1d15ab7ce646ec87cc89d684ca4bcb");
        params.addBodyParameter("type", "10");
        params.addBodyParameter("page", "1");
        params.addBodyParameter("maxResult", "20");
        params.addBodyParameter("showapi_timestamp", str);
        HttpUtils http = new HttpUtils();
        http.send(HttpRequest.HttpMethod.POST,
                "https://route.showapi.com/255-1", params,
                new RequestCallBack<String>() {

                    @Override
                    public void onStart() {
                        text1.setText("conn...");
                    }

                    @Override
                    public void onLoading(long total, long current,
                                          boolean isUploading) {
                        if (isUploading) {
                            text1.setText("upload: " + current + "/" + total);
                        } else {
                            text1.setText("reply: " + current + "/" + total);
                        }
                    }

                    @Override
                    public void onSuccess(ResponseInfo<String> responseInfo) {
                        text1.setText("reply: " + responseInfo.result);
                    }

                    @Override
                    public void onFailure(HttpException error, String msg) {
                        text1.setText(error.getExceptionCode() + ":" + msg);
                    }
                });
    }

    /****
     *
     * 图片加载
     *
     */
    private void image() {
        // TODO Auto-generated method stub
        BitmapUtils bitmapUtils = new BitmapUtils(this);
        bitmapUtils.display(mImageView,
                "http://pic.58pic.com/58pic/11/02/26/91F58PICpGw.jpg");
    }

    Date date = new Date();
    SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
    SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
    String str = sdf.format(date);



}
