# 网络请求----HttpURLConnection的get，post和图片加载
            <div class="blog-body" id="blogBody">
                                    <val data-name="blog_content_type" data-value="richtext"></val>
                    <div class='BlogContent'>
                        <p>URLConnection是个抽象类，它有两个直接子类分别是HttpURLConnection和JarURLConnection。另外一个重要的类是URL，通常URL可以通过传给构造器一个String类型的参数来生成一个指向特定地址的URL实例。</p> 
<p>JDK自带的请求方式，包名: java.net.HttpURLConnection;</p> 
<p>HttpURLConnection请求的类别:&nbsp;<br> 分为二类,GET与POST请求。二者的区别在于：&nbsp;<br> &nbsp; &nbsp; &nbsp;1: get请求可以获取静态页面，也可以把参数放在URL字串后面，传递给servlet，&nbsp;<br> &nbsp; &nbsp; &nbsp;2: post与get的不同之处在于post的参数不是放在URL字串里面，而是放在http请求的正文内。&nbsp;<br> 效果图：</p> 
<p>&nbsp; &nbsp; &nbsp; &nbsp; &nbsp;&nbsp;<img alt="" height="303" src="https://static.oschina.net/uploads/space/2016/1205/141025_Mhqw_2945455.gif" width="378"></p> 
<p>代码：</p> 
<pre><code class="language-java">public class Util {
	
	/***
	 * get请求方式
	 * @param urlPath
	 * @return
	 */
	public static String get(String urlPath) {
		HttpURLConnection conn = null; // 连接对象
		InputStream is = null;
		String resultData = "";
		try {
			URL url = new URL(urlPath); // URL对象
			conn = (HttpURLConnection) url.openConnection(); // 使用URL打开一个链接
			conn.setDoInput(true); // 允许输入流，即允许下载
			conn.setDoOutput(true); // 允许输出流，即允许上传
			conn.setUseCaches(false); // 不使用缓冲
			conn.setRequestMethod("GET"); // 使用get请求
			is = conn.getInputStream(); // 获取输入流，此时才真正建立链接
			InputStreamReader isr = new InputStreamReader(is);
			BufferedReader bufferReader = new BufferedReader(isr);
			String inputLine = "";
			while ((inputLine = bufferReader.readLine()) != null) {
				resultData += inputLine + "\n";
			}

		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (is != null) {
				try {
					is.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if (conn != null) {
				conn.disconnect();
			}
		}
		Log.i("get", resultData.toString());
		return resultData;
	}
	/***
	 * post请求方式
	 * @param urlPath
	 * @param params
	 * @return
	 */
	public static String post(String urlPath, Map&lt;String, String&gt; params) {
		if (params == null || params.size() == 0) {
			return get(urlPath);
		}
		OutputStream os = null;
		InputStream is = null;
		HttpURLConnection connection = null;
		StringBuffer body = getParamString(params);
		byte[] data = body.toString().getBytes();
		try {
			URL url = new URL(urlPath);
			// 获得URL对象
			connection = (HttpURLConnection) url.openConnection();
			// 获得HttpURLConnection对象
			connection.setRequestMethod("POST");
			// 设置请求方法为post
			connection.setUseCaches(false);
			// 不使用缓存
			connection.setConnectTimeout(10000);
			// 设置超时时间
			connection.setReadTimeout(10000);
			// 设置读取超时时间
			connection.setDoInput(true);
			// 设置是否从httpUrlConnection读入，默认情况下是true;
			connection.setDoOutput(true);
			// 设置为true后才能写入参数
			connection.setRequestProperty("Content-Type",
					"application/x-www-form-urlencoded");
			connection.setRequestProperty("Content-Length",
					String.valueOf(data.length));
			os = connection.getOutputStream();
			os.write(data);
			// 写入参数
			if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
				// 相应码是否为200
				is = connection.getInputStream();
				// 获得输入流
				BufferedReader reader = new BufferedReader(
						new InputStreamReader(is));
				// 包装字节流为字符流
				StringBuilder response = new StringBuilder();
				String line;
				while ((line = reader.readLine()) != null) {
					response.append(line);
				}
				Log.i("post", response.toString());
				return response.toString();
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// 关闭
			if (os != null) {
				try {
					os.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (is != null) {
				try {
					is.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (connection != null) {
				connection.disconnect();
				connection = null;
			}
		}
		return null;
	}

	public static StringBuffer getParamString(Map&lt;String, String&gt; params) {
		StringBuffer result = new StringBuffer();
		Iterator&lt;Map.Entry&lt;String, String&gt;&gt; iterator = params.entrySet()
				.iterator();
		while (iterator.hasNext()) {
			Map.Entry&lt;String, String&gt; param = iterator.next();
			String key = param.getKey();
			String value = param.getValue();
			result.append(key).append('=').append(value);
			if (iterator.hasNext()) {
				result.append('&amp;');
			}
		}
		return result;
	}

	/**
	 * 
	 * 图片加载
	 * @param url
	 * @return
	 */
	public static Bitmap getImageBitmap(String url) {
		URL imgUrl = null;
		Bitmap bitmap = null;
		try {
			imgUrl = new URL(url);
			HttpURLConnection conn = (HttpURLConnection) imgUrl
					.openConnection();
			conn.setDoInput(true);
			conn.connect();
			InputStream is = conn.getInputStream();
			bitmap = BitmapFactory.decodeStream(is);
			is.close();
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return bitmap;
	}

}</code></pre> 
<pre><code class="language-java">public class MainActivity extends Activity implements OnClickListener {
	public static String urls = "http://bajie.zhangwoo.cn/app.php?platform=android&amp;appkey=5a379b5eed8aaae531df5f60b12100cfb6dff2c1";

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

	class DownImgAsyncTask extends AsyncTask&lt;String, Void, Bitmap&gt; {

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
			Map&lt;String, String&gt; params = new HashMap&lt;String, String&gt;();
			params.put("q", "test");
			params.put("showapi_appid", "11548");
			params.put("showapi_timestamp", "20160511151954");
			params.put("showapi_sign", "bb1d15ab7ce646ec87cc89d684ca4bcb");
			String data = Util.post("https://route.showapi.com/32-9", params);
			resultStr1 = data;
		}

	}
}</code></pre> 
<p>&nbsp;</p> 
<ul> 
 <li>注意：使用时常出现出现NetworkOnMainThreadException错误，Android.os.NetworkOnMainThreadException错误提示的原因</li> 
 <li>原因：不允许在主线程中进行网络访问</li> 
 <li>解决：将网络访问的操作单独放到一个线程中</li> 
</ul> 
<p>记得加网络权限</p> 
<pre><code class="language-html"> &lt;uses-permission android:name="android.permission.INTERNET"/&gt;</code></pre> 
 </div>
</div>
