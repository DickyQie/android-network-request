#### 网络请求----HttpURLConnection的get，post和图片加载

<p>URLConnection是个抽象类，它有两个直接子类分别是HttpURLConnection和JarURLConnection。另外一个重要的类是URL，通常URL可以通过传给构造器一个String类型的参数来生成一个指向特定地址的URL实例。</p> 
<p>JDK自带的请求方式，包名: java.net.HttpURLConnection;</p> 
<p>HttpURLConnection请求的类别:&nbsp;<br> 分为二类,GET与POST请求。二者的区别在于：&nbsp;<br> &nbsp; &nbsp; &nbsp;1: get请求可以获取静态页面，也可以把参数放在URL字串后面，传递给servlet，&nbsp;<br> &nbsp; &nbsp; &nbsp;2: post与get的不同之处在于post的参数不是放在URL字串里面，而是放在http请求的正文内。&nbsp;<br> 效果图：</p> 
<p>&nbsp; &nbsp; &nbsp; &nbsp; &nbsp;&nbsp;<img alt="" height="303" src="https://static.oschina.net/uploads/space/2016/1205/141025_Mhqw_2945455.gif" width="378"></p> 
