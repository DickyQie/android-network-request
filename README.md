# xUtils框架的使用 
<h2>&nbsp; &nbsp;xUtils简介</h2> 
<ul> 
 <li>xUtils 包含了很多实用的android工具，xUtils 源于Afinal框架，对Afinal进行了大量重构，使得xUtils支持大文件上传，更全面的http请求协议支持，拥有更加灵活的ORM。</li> 
 <li>xUitls 最低兼容android 2.2 (api level 8)。</li> 
 <li>在aFinal基础上进行重构和扩展的框架，相比aFinal有很大的改善，基于网路的应用，只要处理得当，能让大家彻底的摆脱各种工具类和重复代码的困扰。</li> 
 <li>&nbsp;</li> 
 <li>Java反射(Reflect)技术</li> 
 <li> 
  <ol> 
   <li>动态获取在当前Java虚拟机中的类、接口或者对象信息</li> 
   <li>解除两个类之间的耦合性，即在未得到依赖类的情况下，自身应用可以通过编译</li> 
   <li>动态依赖注入(即需要某一类对象时动态生成类实例,并设置到被依赖的类中)，减少编译时的内存开销</li> 
  </ol> </li> 
</ul> 
<span id="OSC_h2_2"></span>
<h2>xUtils主要有四大模块</h2> 
<p>&nbsp;</p> 
<span id="OSC_h3_3"></span>
<h3><strong>ViewUtils模块</strong></h3> 
<ul> 
 <li>&nbsp;android中的ioc框架，完全注解方式就可以进行UI，资源和事件绑定；</li> 
 <li>&nbsp;新的事件绑定方式，使用混淆工具混淆后仍可正常工作；</li> 
 <li>&nbsp;目前支持常用的20种事件绑定，参见ViewCommonEventListener类和包com.lidroid.xutils.view.annotation.event。</li> 
</ul> 
<span id="OSC_h3_4"></span>
<h3><strong>BitmapUtils模块</strong></h3> 
<ul> 
 <li>加载bitmap的时候无需考虑bitmap加载过程中出现的oom和android容器快速滑动时候出现的图片错位等现象；</li> 
 <li>&nbsp;支持加载网络图片和本地图片；</li> 
 <li>&nbsp;内存管理使用lru算法，更好的管理bitmap内存；</li> 
 <li>&nbsp;可配置线程加载线程数量，缓存大小，缓存路径，加载显示动画等...</li> 
</ul> 
<span id="OSC_h3_5"></span>
<h3>&nbsp;HttpUtils模块</h3> 
<ul> 
 <li>&nbsp;支持同步，异步方式的请求；</li> 
 <li>&nbsp;支持大文件上传，上传大文件不会oom；</li> 
 <li>&nbsp;支持GET，POST，PUT，MOVE，COPY，DELETE，HEAD，OPTIONS，TRACE，CONNECT请求；</li> 
 <li>&nbsp;下载支持301/302重定向，支持设置是否根据Content-Disposition重命名下载的文件；</li> 
 <li>&nbsp;返回文本内容的请求(默认只启用了GET请求)支持缓存，可设置默认过期时间和针对当前请求的过期时间。</li> 
</ul> 
<span id="OSC_h3_6"></span>
<h3>&nbsp;DbUtils模块</h3> 
<ul> 
 <li>&nbsp;android中的orm框架，一行代码就可以进行增删改查；</li> 
 <li>&nbsp;支持事务，默认关闭；</li> 
 <li>&nbsp;可通过注解自定义表名，列名，外键，唯一性约束，NOT NULL约束，CHECK约束等（需要混淆的时候请注解表名和列名）；</li> 
 <li>&nbsp;支持绑定外键，保存实体时外键关联实体自动保存或更新；</li> 
 <li>&nbsp;自动加载外键关联实体，支持延时加载；</li> 
 <li>&nbsp;支持链式表达查询，更直观的查询语义</li> 
</ul> 
<p>案例效果如图：</p> 
<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <img alt="" src="https://static.oschina.net/uploads/space/2017/0209/093138_Gdj8_2945455.gif"></p> 
<pre><code class="language-java"> ViewUtils使用方法
  2 
  3 @ContentView(R.layout.activity_xutils)//加载布局文件
  4 public class MainActivity extends Activity {
  5    // 初始化控件
  6    @ViewInject(R.id.btnUtil1)
  7    Button btn1;
  8    @Override
  9    protected void onCreate(Bundle savedInstanceState) {
 10       super.onCreate(savedInstanceState);
 11       ViewUtils.inject(this);//注入view和事件
 12    }
 13    @OnClick(R.id.btnUtil1)//点击事件
 14    public void btnClick(View v) {
 15       Toast.makeText(getApplicationContext(),"get",1).show();
 16    }
 17 }
 18 BitmapUtils使用方法
 19 
 20 BitmapUtils bitmapUtils = new BitmapUtils(this);
 21 bitmapUtils.display(mImageView,"http://pic.58pic.com/58pic/11/02/26/91F58PICpGw.jpg");
 22 
 23 HttpUtils使用方法
 24    /*
 25     * 网络请求get
 26     */
 27    private void get1() {
 28       HttpUtils http = new HttpUtils();
 29       http.send(HttpRequest.HttpMethod.GET,"http://www.lidroid.com",new RequestCallBack&lt;String&gt;() {
 30                 @Override
 31                 public void onLoading(long total,long current,boolean isUploading) {
 32                    text1.setText(current+ "/" + total);
 33                 }
 34                 @Override
 35                 public void onSuccess(ResponseInfo&lt;String&gt; responseInfo) {
 36                    text1.setText(responseInfo.result);
 37                 }
 38                 @Override
 39                 public void onStart(){
 40                 }
 41                 @Override
 42                 public void onFailure(HttpException error, String msg) {
 43                 }
 44             });
 45    }
 46  
 47    private void get2() {
 48       // TODOAuto-generated method stub
 49       HttpUtils http = new HttpUtils(10000);//放回html数据和api数据--10000代表10s超时
 50       http.configCurrentHttpCacheExpiry(5000);//设置缓存5s,5秒内直接返回上次成功请求的结果。
 51       http.configResponseTextCharset("UTF-8");//配置HTTP响应的文本编码
 52       http.send( HttpRequest.HttpMethod.GET,
 53             "https://route.showapi.com/255-1?showapi_appid=11548&amp;type=10&amp;page=1&amp;maxResult=20&amp;"
 54               + "showapi_timestamp=" +str+ "&amp;showapi_sign=bb1d15ab7ce646ec87cc89d684ca4bcb",
 55             new RequestCallBack&lt;String&gt;() {
 56                 @Override
 57                 public void onLoading(long total,long current,boolean isUploading) {//更新任务进度（需覆盖重写）
 58                    text1.setText(current+ "/" + total);
 59                 }
 60                 @Override
 61                 public void onSuccess(ResponseInfo&lt;String&gt; responseInfo) {
 62                    text1.setText(responseInfo.result);//网络请求执行成功（需覆盖重写）//接口回调返回数据
 63                 }
 64                 @Override
 65                 public void onStart(){//开始发送网络请求（需覆盖重写）
 66                 }
 67                 @Override
 68                 public void onFailure(HttpException error, String msg) {
 69                 }
 70             });
 71    }
 72    /***
 73     * post请求
 74     */
 75    private void post1() {
 76       RequestParams params = new RequestParams("utf-8");
 77       params.addBodyParameter("showapi_appid","11548");
 78       params.addBodyParameter("showapi_sign","bb1d15ab7ce646ec87cc89d684ca4bcb");
 79       params.addBodyParameter("type","10");
 80       params.addBodyParameter("page","1");
 81       params.addBodyParameter("maxResult","20");
 82       params.addBodyParameter("showapi_timestamp",str);
 83       HttpUtils http = new HttpUtils();
 84       http.send(HttpRequest.HttpMethod.POST,"https://route.showapi.com/255-1",                         params,new RequestCallBack&lt;String&gt;() {
 85                 @Override
 86                 public void onStart(){
 87                    text1.setText("conn...");
 88                 }
 89                 @Override
 90                 public void onLoading(long total,long current,boolean isUploading) {
 91                    if(isUploading) {
 92                       text1.setText("upload:" + current + "/" + total);
 93                    } else {
 94                       text1.setText("reply:" + current + "/" + total);
 95                    }
 96                 }
 97                 @Override
 98                 public void onSuccess(ResponseInfo&lt;String&gt; responseInfo) {
 99                    text1.setText("reply:" + responseInfo.result);
100                 }
101                 @Override
102                 public void onFailure(HttpException error, String msg) {
103                    text1.setText(error.getExceptionCode()+ ":" + msg);
104                 }
105             });
106    }</code></pre> 
<p>使用xUtils框架需要在AndroidManifest.xml加以下权限</p> 
<pre><code class="language-html"> &lt;uses-permissionandroid:name="android.permission.INTERNET"/&gt;

 &lt;uses-permissionandroid:name="android.permission.WRITE_EXTERNAL_STORAGE"/&gt;</code></pre> 
