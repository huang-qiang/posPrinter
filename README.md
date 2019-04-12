### 热敏打印机型号：YCT-380B
    在源码环境中，mm编译生成jar包
## 1.	PrintManager初始化
函数原型	| public static void init(Context context)
:-------:|:--
函数描述	| 无
入口参数	| context
出口参数	| 无
返 回 值	 | 无	
备注说明	| 使用PrintManager前，必须先进行初始化
调用实例	| PrintManager.init(context);

## 2.	PrintManager反初始化
函数原型	| public static void uninit()
:-------:|:--
函数描述	| 无
入口参数	| 无
出口参数	| 无
返 回 值	| 无	
备注说明	| 和init()对应，成对调用
调用实例	| PrintManager.uninit();
## 3.	打印图片
函数原型	| public static boolean printImage (String path)
:-------:|:--
函数描述	| 无
入口参数	| path:图片的绝对路径
出口参数	| 无
返 回 值	| true:成功 false:失败	
备注说明	| 无
调用实例	| PrintManager.printImage(“/sdcard/test.png”);
## 4.	检测打印机状态
函数原型	| public static boolean checkStatus(PrinterStatus printerStatus)
:-------:|:--
函数描述	| 无
入口参数	| 无
出口参数	| PrinterStatus:返回打印机状态
返 回 值	| true:通信成功 false:通信失败	
备注说明	| 无
调用实例	| 
``` java
PrinterStatus printerStatus = new PrinterStatus();
bRet = PrintManager.checkStatus(printerStatus);
if(bRet){
	Log.d("", "吐纸器无纸： "+printerStatus.isFeederWithoutPaper()
+" 缺纸 "+printerStatus.isPaperMissing()
+" 纸将尽："+ printerStatus.isPaperLittle()
+" 有纸堵： "+printerStatus.isPaperBlock());
}
```
	

