## RipplePicView（Android仿探探社交自定义闪聊动画效果）

### 需要实现的效果图如探探

![默认](images/Screenshot_20230130-131719.png)

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
	  implementation 'com.github.FlashQin:RipplePicView:1.0.2'
}
``` 
