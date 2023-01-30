## RipplePicView（Android仿探探社交自定义闪聊动画效果）

### 需要实现的效果图如探探

![默认](images/Screenshot_20230130-131719.png)
### 主要实现方式
就是一个自定义控件,循环的去画圆,在圆半径增加的时候，找到圆上的3个点,然后把图片画上对应的坐标，半径越大,透明度越高

* 这里用到了一些数学知识
  * 求圆上某个点的坐标公式:
  * float x1 = (mWidth / 2) + Math.round(Math.sin(Math.toRadians(c.angle)) * radis);
  * float y1 = (mHeight / 2) + Math.round(Math.cos(Math.toRadians(c.angle)) * radis);

  * float x2 = (mWidth / 2) + Math.round(Math.sin(Math.toRadians(c.angle + 110)) * radis);
  * float y2 = (mHeight / 2) + Math.round(Math.cos(Math.toRadians(c.angle + 110)) * radis);

  * float x3 = (mWidth / 2) + Math.round(Math.sin(Math.toRadians(c.angle + 200)) * radis);
  * float y3 = (mHeight / 2) + Math.round(Math.cos(Math.toRadians(c.angle + 200)) * radis);

### 我实现的效果如图（还有点BUG需要修复,后续更新）

![默认](images/Screenshot_20230130-132234.png)

## 使用方式

# Step 1

* Add it in your root build.gradle at the end of repositories:

```groovy 
 allprojects {
     repositories { 
     ... maven {
      url 'https://www.jitpack.io' 
      } 
     } 
  }
```  

# Step 2

. Add the dependency

```groovy  
dependencies {
	  implementation 'com.github.FlashQin:RipplePicView:1.0.2'//具体版本号请查看最新版本号
}
``` 
