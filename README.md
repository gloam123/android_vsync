# android_vsync
## 鸿蒙系统vsync波动丢帧复现demo


### Oneplus 7t android 10.0
![Image text](https://github.com/gloam123/android_vsync/blob/main/docs/oneplus7t.png)


### Honor 20s HarmonyOS 2.0:
![Image text](https://github.com/gloam123/android_vsync/blob/main/docs/hongmeng.png)



## 核心代码如下:
```
mChoreographer = Choreographer.getInstance();
mVSyncFrameCallback = new Choreographer.FrameCallback() {
    @Override
    public void doFrame(long frameTimeNanos) {
    	mChoreographer.postFrameCallback(mVSyncFrameCallback);

        View_xxx.postInvalidateOnAnimation();
    }
};
```
