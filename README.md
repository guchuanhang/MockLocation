# 让开发者快速运行起来模拟位置Demo，获取有益信息


这个模拟位置APP，其中key code 来自与XieXiePro/MockLocation， 
由于原来的项目使用的gradle版本有些陈旧，不太容易导入AS查看效果。
为了方便自己，也为对有需要的朋友快速运行起来。在新的gradle版本上
调整一下，再次上传。

*****

运行过程如下：
1. 从github上checkout项目；
2. 运行到手机上，同意定位权限后，参照图片说明开启模拟位置权限；
3. 退出APP；
4. 再次打开APP。

![使用说明](hhttps://img-blog.csdnimg.cn/20190108135733797.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L2d1Y2h1YW5oYW5n,size_16,color_FFFFFF,t_70 "使用说明")

![成功画面](https://img-blog.csdnimg.cn/20190108135821557.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L2d1Y2h1YW5oYW5n,size_16,color_FFFFFF,t_70 "开始模拟位置画面")


*GPSService是一个查询模拟位置信息从Service，需要的同学可以快速抽取出来*