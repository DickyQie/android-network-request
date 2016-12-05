package com.example.httpurlconnctiondemo;

import java.util.HashMap;
import java.util.Map;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

/****
 * 
 * 
 * httpURLConnection网络请求和图片加载
 * 
 * 
 * 使用时常出现 NetworkOnMainThreadException错误
 * 出现Android.os.NetworkOnMainThreadException错误提示的原因 原因：不允许在主线程中进行网络访问
 * 
 * 解决：将网络访问的操作单独放到一个线程中
 * 
 * @author zq
 * 
 */
public class MainActivity extends Activity implements OnClickListener {
	public static String urls = "http://bajie.zhangwoo.cn/app.php?platform=android&appkey=5a379b5eed8aaae531df5f60b12100cfb6dff2c1";

	private TextView mTextView;
	private ImageView imagegvoew;
	String resultStr = "";
	String resultStr1 = "";

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
			new Thread(new Runnable() {

				@Override
				public void run() {
					// TODO Auto-generated method stub
					new DownImgAsyncTask()
							.execute("http://avatar.csdn.net/8/6/0/2_dickyqie.jpg");
				}
			}).start();

			break;
		default:
			break;
		}

	}

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

	class VisitWebRunnable implements Runnable {

		@Override
		public void run() {
			// TODO Auto-generated method stub
			String data = Util.get(urls);
			resultStr = data;
		}

	}

	class VisitWebRunnables implements Runnable {

		@Override
		public void run() {
			// TODO Auto-generated method stub
			Map<String, String> params = new HashMap<String, String>();
			params.put("q", "test");
			params.put("showapi_appid", "11548");
			params.put("showapi_timestamp", "20160511151954");
			params.put("showapi_sign", "bb1d15ab7ce646ec87cc89d684ca4bcb");
			String data = Util.post("https://route.showapi.com/32-9", params);
			resultStr1 = data;
		}

	}
}
