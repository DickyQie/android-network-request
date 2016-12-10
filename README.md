# 网络请求框架----HttpClient的get,post和图片上传服务器
  <div class="blog-body" id="blogBody">
                                    <val data-name="blog_content_type" data-value="richtext"></val>
                    <div class='BlogContent'>
                        <p>HttpClient是Apache Jakarta Common下的子项目，用来提供高效的、最新的、功能丰富的支持HTTP协议的客户端编程工具包，并且它支持HTTP协议最新的版本和建议。HttpClient已经应用在很多的项目中，比如Apache Jakarta上很著名的另外两个开源项目Cactus和HTMLUnit都使用了HttpClient。</p> 
<p>HttpClient:是一个接口.</p> 
<span id="OSC_h2_1"></span>
<h2><span style="color:#B22222">特性：</span></h2> 
<p>1. 基于标准、纯净的java语言。实现了Http1.0和Http1.1</p> 
<p>2. 以可扩展的面向对象的结构实现了Http全部的方法（GET, POST, PUT, DELETE, HEAD, OPTIONS, and TRACE）。</p> 
<p>3. 支持HTTPS协议。</p> 
<p>4. 通过Http代理建立透明的连接。</p> 
<p>5. 利用CONNECT方法通过Http代理建立隧道的https连接。</p> 
<p>6. Basic, Digest, NTLMv1, NTLMv2, NTLM2 Session, SNPNEGO/Kerberos认证方案。</p> 
<p>7. 插件式的自定义认证方案。</p> 
<p>8. 便携可靠的套接字工厂使它更容易的使用第三方解决方案。</p> 
<p>9. 连接管理器支持多线程应用。支持设置最大连接数，同时支持设置每个主机的最大连接数，发现并关闭过期的连接。</p> 
<p>10. 自动处理Set-Cookie中的Cookie。</p> 
<p>11. 插件式的自定义Cookie策略。</p> 
<p>12. Request的输出流可以避免流中内容直接缓冲到socket服务器。</p> 
<p>13. Response的输入流可以有效的从socket服务器直接读取相应内容。</p> 
<p>14. 在http1.0和http1.1中利用KeepAlive保持持久连接。</p> 
<p>15. 直接获取服务器发送的response code和 headers。</p> 
<p>16. 设置连接超时的能力。</p> 
<p>17. 实验性的支持http1.1 response caching。</p> 
<p>18. 源代码基于Apache License 可免费获取。&nbsp;</p> 
<p>案例效果图：</p> 
<p>&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;<img alt="" height="541" src="https://static.oschina.net/uploads/space/2016/1210/095212_9vu1_2945455.gif" width="338"></p> 
<p>MainActivity.java</p> 
<pre><code class="language-java">public class MainActivity extends Activity implements OnClickListener {

	public static String urls = "http://gpj.zhangwoo.cn/app.php?platform=android&amp;appkey=5a379b5eed8aaae531df5f60b12100cfb6dff2c1";

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
			List&lt;NameValuePair&gt; formparams = new ArrayList&lt;NameValuePair&gt;();
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
</code></pre> 
<pre><code class="language-java">public class Util {
	/**
	 * 图片加载
	 * 
	 * @param url
	 * @return
	 */
	public static Bitmap getImageBitmap(String url) {
		DefaultHttpClient httpclient = new DefaultHttpClient();
		HttpGet httpget = new HttpGet(url);
		try {
			HttpResponse resp = httpclient.execute(httpget);
			// 判断是否正确执行
			if (HttpStatus.SC_OK == resp.getStatusLine().getStatusCode()) {
				// 将返回内容转换为bitmap
				HttpEntity entity = resp.getEntity();
				InputStream in = entity.getContent();
				Bitmap mBitmap = BitmapFactory.decodeStream(in);
				// 向handler发送消息，执行显示图片操作
				return mBitmap;
			}

		} catch (Exception e) {
		} finally {
			httpclient.getConnectionManager().shutdown();
		}

		return null;
	}

	/***
	 * 上传文件
	 */
	public static void addFile(String url) {
		CloseableHttpClient httpclient = HttpClients.createDefault();
		try {
			HttpPost httppost = new HttpPost(url);

			FileBody bin = new FileBody(new File(url));
			StringBody comment = new StringBody("A binary file of some kind",
					ContentType.TEXT_PLAIN);

			HttpEntity reqEntity = MultipartEntityBuilder.create()
					.addPart("bin", bin).addPart("comment", comment).build();

			httppost.setEntity(reqEntity);

			System.out
					.println("executing request " + httppost.getRequestLine());
			CloseableHttpResponse response = httpclient.execute(httppost);
			try {
				System.out.println(response.getStatusLine());
				HttpEntity resEntity = response.getEntity();
				if (resEntity != null) {
					System.out.println("Response content length: "
							+ resEntity.getContentLength());
				}
			} finally {
				response.close();
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				httpclient.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
</code></pre> 
<p>记得加网络权限</p> 
<pre><code class="language-html">&lt;uses-permission android:name="android.permission.INTERNET"/&gt;</code></pre> 
<p>完成以上功能需要 <a href="http://dl.download.csdn.net/down11/20161024/cb7b31ef56db02a700e9d655e9e202ff.jar?response-content-disposition=attachment%3Bfilename%3D%22httpclient-4.3.5.jar%22&amp;OSSAccessKeyId=9q6nvzoJGowBj4q1&amp;Expires=1481338628&amp;Signature=RPvUtBIot7Hy9EF%2FkeBrjiHK9X0%3D" rel="nofollow">httpclient.jar </a>&nbsp;, <a href="http://dl.download.csdn.net/down11/20161024/deb68cebca19b1ae153505c3377ccc19.jar?response-content-disposition=attachment%3Bfilename%3D%22httpcore-4.3.2.jar%22&amp;OSSAccessKeyId=9q6nvzoJGowBj4q1&amp;Expires=1481338903&amp;Signature=A2eqoeW9TS0aMvTCp241dBmkrPs%3D" rel="nofollow">httpcore.jar</a> &nbsp;<a href="http://dl.download.csdn.net/down11/20161024/f63090f1745c3a2959e966c5bf943492.jar?response-content-disposition=attachment%3Bfilename%3D%22httpmime-4.3.5.jar%22&amp;OSSAccessKeyId=9q6nvzoJGowBj4q1&amp;Expires=1481338769&amp;Signature=dx7LmkicJGGDtxBHmx0XRJUBGDc%3D" rel="nofollow">htttpmime.jar</a> &nbsp; 点击下载即可</p> 
