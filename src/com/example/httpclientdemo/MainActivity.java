package com.example.httpclientdemo;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

/****
 * 
 * @author zq
 *
 */
public class MainActivity extends Activity implements OnClickListener {

	public static String urls = "http://gpj.zhangwoo.cn/app.php?platform=android&appkey=5a379b5eed8aaae531df5f60b12100cfb6dff2c1";

	private TextView mTextView;
	private ImageView imagegvoew;
	String resultStr = "";
	String resultStr1 = "";
	private Bitmap mBitmap = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		initView();
	}

	private void initView() {
		findViewById(R.id.btn1).setOnClickListener(this);
		findViewById(R.id.btn2).setOnClickListener(this);
		findViewById(R.id.btn3).setOnClickListener(this);
		findViewById(R.id.btn4).setOnClickListener(this);
		mTextView = (TextView) findViewById(R.id.Text);
		imagegvoew = (ImageView) findViewById(R.id.imagegvoew);

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn1:

			Thread visitBaiduThread = new Thread(new VisitWebRunnable());
			visitBaiduThread.start();
			try {
				visitBaiduThread.join();
				if (!resultStr.equals("")) {
					mTextView.setText(resultStr);
				}
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			break;
		case R.id.btn2:
			Thread visitBaiduThreads = new Thread(new VisitWebRunnables());
			visitBaiduThreads.start();
			try {
				visitBaiduThreads.join();
				if (!resultStr1.equals("")) {
					mTextView.setText(resultStr1);
				}
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
		case R.id.btn3:

			new DownImgAsyncTask()
					.execute("http://avatar.csdn.net/8/6/0/2_dickyqie.jpg");

			break;
		case R.id.btn4:
			//Util.addFile("url");//图片路径
			break;
		default:
			break;
		}

	}

	/** 这里重写handleMessage方法，接受到子线程数据后更新UI **/
	private Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 1:
				// 关闭
				imagegvoew.setImageBitmap(mBitmap);
				break;
			}
		}
	};

	class DownImgAsyncTask extends AsyncTask<String, Void, Bitmap> {

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();

		}

		@Override
		protected Bitmap doInBackground(String... params) {
			// TODO Auto-generated method stub
			Bitmap b = Util.getImageBitmap(params[0]);
			return b;
		}

		@Override
		protected void onPostExecute(Bitmap result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			if (result != null) {
				imagegvoew.setImageBitmap(result);
			}
		}

	}

	/***
	 * get
	 * 
	 * @author zq
	 * 
	 */
	class VisitWebRunnable implements Runnable {

		@Override
		public void run() {
			// TODO Auto-generated method stub
			HttpClient httpCLient = new DefaultHttpClient();
			// 创建get请求实例
			HttpGet httpget = new HttpGet("http://www.baidu.com");
			try {
				HttpResponse response = httpCLient.execute(httpget);
				if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {

					String result = EntityUtils.toString(response.getEntity(),
							"UTF-8");
					resultStr = result;
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

	}

	/***
	 * Post
	 * 
	 * @author zq
	 * 
	 */
	class VisitWebRunnables implements Runnable {

		@Override
		public void run() {
			// TODO Auto-generated method stub
			List<NameValuePair> formparams = new ArrayList<NameValuePair>();
			formparams.add(new BasicNameValuePair("c", "member"));
			formparams.add(new BasicNameValuePair("a", "getdepartments"));
			HttpClient client = null;
			HttpPost request = null;
			try {
				client = new DefaultHttpClient();
				request = new HttpPost(urls);
				request.setEntity(new UrlEncodedFormEntity(formparams,
						HTTP.UTF_8));
				HttpResponse response = client.execute(request);
				if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
					String result = EntityUtils.toString(response.getEntity(),
							"UTF-8");
					System.out.println(result);
					resultStr1 = result;
				}
			} catch (IOException e) {
				e.printStackTrace();

			}

		}

	}

}
