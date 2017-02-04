package http.httputils;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.hdl.myhttputils.CommCallback;
import com.hdl.myhttputils.MyHttpUtils;

import java.io.File;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;

import http.httputils.bean.UserBean;
import http.httputils.bean.UserInfo;


/*****
 * HttpURLConnection  高级封装 MyHttpUtils
 */
public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    public static String urls2 ="http://gpj.zhangwoo.cn/app.php?platform=android&appkey=5a379b5eed8aaae531df5f60b12100cfb6dff2c1";
    private TextView tvProgress;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tvProgress = (TextView) findViewById(R.id.tv_progress);
        findViewById(R.id.get).setOnClickListener(this);
        findViewById(R.id.post).setOnClickListener(this);
        findViewById(R.id.download).setOnClickListener(this);
        findViewById(R.id.uploadFile).setOnClickListener(this);
        findViewById(R.id.uploadFileMult).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.get:
                onGet();
                break;
            case R.id.post:
                onPost();
                break;
            case R.id.download:
                onDownload();
                break;
            case R.id.uploadFile:
                onUploadFile();
                break;
            case R.id.uploadFileMult:
                onUploadFileMult();
                break;
        }

    }

    /**
     * get
     */
    public void onGet() {
        String url = "http://gpj.zhangwoo.cn/app.php?platform=android&appkey=5a379b5eed8aaae531df5f60b12100cfb6dff2c1&c=member&a=getdepartments";
        new MyHttpUtils()
                .url(url)//请求的url
                .setJavaBean(UserBean.class)//设置需要解析成的javabean对象
                .setReadTimeout(60000)//设置读取超时时间,不设置的话默认为30s(30000)
                .setConnTimeout(6000)//设置连接超时时间,不设置的话默认5s(5000)
                .onExecute(new CommCallback<UserBean>() {//开始执行异步请求,传入一个通用回调对象,泛型为返回的javabean对象
                    @Override
                    public void onSucess(UserBean bean) {//成功之后回调
                        Util.showMsg(MainActivity.this, bean.getData().get(0).getDepartname());
                    }

                    @Override
                    public void onFailed(String msg) {//失败时候回调
                        Util.showMsg(MainActivity.this, msg);
                    }
                });
    }
    /**
     * post
     *
     */
    public void onPost() {
        HashMap<String, String> param = new HashMap<>();
        param.put("c", "member");
        param.put("a", "getdepartments");
        new MyHttpUtils()
                .url(urls2)
                .addParam(param)
                .setJavaBean(UserBean.class)
                .onExecuteByPost(new CommCallback<UserBean>() {///实体类自动解析
                    @Override
                    public void onSucess(UserBean remarkBean) {
                        Log.i("tag",remarkBean.toString());
                        Util.showMsg(MainActivity.this, remarkBean.getData().get(0).getDepartname());
                    }
                    @Override
                    public void onFailed(String msg) {
                        Util.showMsg(MainActivity.this, msg);
                    }
                });
    }

    /**
     * 下载文件
     */
    public void onDownload() {
        String url = "http://avatar.csdn.net/8/6/0/2_dickyqie.jpg";
        new MyHttpUtils()
                .url(url)
                .setFileSavePath("/sdcard/downloadtest")//不要这里只是填写文件保存的路径，不包括文件名哦
                .setReadTimeout(5 * 60 * 1000)//由于下载文件耗时比较大，所以设置读取时间为5分钟
                .downloadFile(new CommCallback<String>() {
                    @Override
                    public void onSucess(String msg) {
                        Util.showMsg(MainActivity.this, msg);
                    }

                    @Override
                    public void onFailed(String s) {

                    }

                    /**
                     * 可以重写进度回调方法
                     * @param total
                     * @param current
                     */
                    @Override
                    public void onDownloading(long total, long current) {
                        tvProgress.setText("当前进度：" + new DecimalFormat("######0.00").format(((double) current / total) * 100) + "%");
                    }
                });
    }

    /***
     * 上传单个文件
     */
    public void onUploadFile() {
        HashMap<String, String> params = new HashMap<>();
        new MyHttpUtils()
                .url("url")
                .setJavaBean(UserInfo.class)
                .addUploadFile(new File("/sdcard/downloadtest/qie.jpg"))//设置需上传文件
                .uploadFile(new CommCallback<UserInfo>() {
                    @Override
                    public void onSucess(UserInfo uploadResultBean) {
                        Util.showMsg(MainActivity.this, uploadResultBean.getMessage());

                    }
                    @Override
                    public void onFailed(String msg) {
                        Util.showMsg(MainActivity.this, msg);
                    }
                });
    }

    /***
     * 上传多个文件
     */
    public void onUploadFileMult() {
        ArrayList<File>fileList=new ArrayList<>();//文件列表
        fileList.add(new File("/sdcard/downloadtest/qie.jpg"));
        fileList.add(new File("/sdcard/downloadtest/qq.apk"));
        new MyHttpUtils()
                .url("url")
                .setJavaBean(UserInfo.class)
                .addUploadFiles(fileList)//设置需上传的多个文件
                .uploadFileMult(new CommCallback<UserInfo>() {
                    @Override
                    public void onSucess(UserInfo uploadResultBean) {
                        Util.showMsg(MainActivity.this, uploadResultBean.getMessage());
                    }

                    @Override
                    public void onFailed(String msg) {
                        Util.showMsg(MainActivity.this, msg);
                    }
                });
    }
}