### xUtils框架的使用 
<h4>&nbsp; &nbsp;xUtils简介</h4> 
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
<h4>xUtils主要有四大模块</h4> 
<p>&nbsp;</p> 
<span id="OSC_h3_3"></span>
<h5><strong>ViewUtils模块</strong></h5> 
<ul> 
 <li>&nbsp;android中的ioc框架，完全注解方式就可以进行UI，资源和事件绑定；</li> 
 <li>&nbsp;新的事件绑定方式，使用混淆工具混淆后仍可正常工作；</li> 
 <li>&nbsp;目前支持常用的20种事件绑定，参见ViewCommonEventListener类和包com.lidroid.xutils.view.annotation.event。</li> 
</ul> 
<span id="OSC_h3_4"></span>
<h5><strong>BitmapUtils模块</strong></h5> 
<ul> 
 <li>加载bitmap的时候无需考虑bitmap加载过程中出现的oom和android容器快速滑动时候出现的图片错位等现象；</li> 
 <li>&nbsp;支持加载网络图片和本地图片；</li> 
 <li>&nbsp;内存管理使用lru算法，更好的管理bitmap内存；</li> 
 <li>&nbsp;可配置线程加载线程数量，缓存大小，缓存路径，加载显示动画等...</li> 
</ul> 
<span id="OSC_h3_5"></span>
<h5>&nbsp;HttpUtils模块</h5> 
<ul> 
 <li>&nbsp;支持同步，异步方式的请求；</li> 
 <li>&nbsp;支持大文件上传，上传大文件不会oom；</li> 
 <li>&nbsp;支持GET，POST，PUT，MOVE，COPY，DELETE，HEAD，OPTIONS，TRACE，CONNECT请求；</li> 
 <li>&nbsp;下载支持301/302重定向，支持设置是否根据Content-Disposition重命名下载的文件；</li> 
 <li>&nbsp;返回文本内容的请求(默认只启用了GET请求)支持缓存，可设置默认过期时间和针对当前请求的过期时间。</li> 
</ul> 
<span id="OSC_h3_6"></span>
<h5>&nbsp;DbUtils模块</h5> 
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

