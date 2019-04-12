package com.nodl.yct380b;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLDecoder;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import com.nodl.print.BasePrinter;
import com.nodl.print.Connection;
import com.nodl.print.PrinterDriver;
import com.nodl.print.PrinterStatus;
import com.nodl.print.content.Doucument;
import com.nodl.print.content.Printable;
import com.nodl.print.content.convertable.Font;
import com.nodl.print.content.printable.Barcode;
import com.nodl.print.content.printable.BlankRow;
import com.nodl.print.content.printable.CutPage;
import com.nodl.print.content.printable.Image;
import com.nodl.print.content.printable.KeyValue;
import com.nodl.print.content.printable.QRcode;
import com.nodl.print.content.printable.Section;
import com.nodl.print.content.printable.Section.TextAlign;
import com.nodl.print.content.printable.Table;
import com.nodl.print.exception.PrinterException;
import com.nodl.yct380b.connection.USBconnection;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.text.TextUtils;
import android.util.Log;

public class PrintManager {

	private static final DateFormat date_time_format = new SimpleDateFormat("yyyy-MM-dd  HH:mm:ss");
	
	private static BasePrinter p = null;
	private static Connection usbConnection = null;
	private static Context mContext = null;
	private static boolean bDeviceVertify = false;
	private static Lock lock = new ReentrantLock();

	public static void init(Context context) {
		lock.lock();
		mContext = context;
		bDeviceVertify = checkDevice();

		Log.d("PRINTER", "init V1.0.1");
		usbConnection = new USBconnection(context);

		// 加载驱动
		try {
			p = PrinterDriver.newInstanse("com.nodl.yct380b.WTPrinter", "");
		} catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
			e.printStackTrace();
		}

		// 设置usb连接
		p.setConnection(usbConnection);		
		lock.unlock();
	}
	
	public static void uninit() {
		lock.lock();
		if(usbConnection != null) {
			try {
				usbConnection.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			usbConnection = null;
		}
		lock.unlock();
	}
	
	public static boolean checkStatus(PrinterStatus printerStatus) {
		if(!bDeviceVertify) {
			return false;
		}
		lock.lock();
		if((p == null)||(printerStatus == null)||(usbConnection == null)) {
			lock.unlock();
			return false;
		}
		boolean bRet = false;
		byte[] buffer = new byte[1];
		try {
			bRet = p.checkConnection(usbConnection, 4, buffer);
			if(bRet) {
				if((buffer[0]&WPConsts.FEEDER_WITHOUT_PAPER) == WPConsts.FEEDER_WITHOUT_PAPER) {
					printerStatus.setbFeederWithoutPaper(true);
				}
				if((buffer[0]&WPConsts.PAPER_MISSING) == WPConsts.PAPER_MISSING) {
					printerStatus.setbPaperMissing(true);
				}
				if((buffer[0]&WPConsts.PAPER_LITTLE) == WPConsts.PAPER_LITTLE) {
					printerStatus.setbPaperLittle(true);
				}
				if((buffer[0]&WPConsts.PAPER_BLOCK) == WPConsts.PAPER_BLOCK) {
					printerStatus.setbPaperBlock(true);
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		lock.unlock();
		return bRet;
	}
	
	public static boolean printImage(String path) {
		if(!bDeviceVertify) {
			return false;
		}
		lock.lock();
		Log.d("PRINTER", "printImage");
		if((p == null)||(usbConnection == null)) {
			lock.unlock();
			return false;
		}
		if(TextUtils.isEmpty(path)) {
			lock.unlock();
			return false;
		}
		
		boolean bRet = false;
		// 封装文档内容
				Doucument document = new Doucument();
//				document.addContents(_title());
//				document.addContents(_order());
//				document.addContents(_baseInfo());
//				document.addContents(_orderItem());
//				document.addContents(_receipt());
//				document.addContents(_description());
				try {
//					document.addContent(_image(path));
					addImage(document,path);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				//document.addContent(new CutPage());

				// 打印文档内容
				bRet = p.print(document);
				lock.unlock();
				return bRet;
	}
	
	private static void addImage(Doucument document, String path) throws IOException {
		if((document == null) || (TextUtils.isEmpty(path))) {
			return;
		}
		
		int width = 0, height = 0;
		int pixel = 0;
		int tmpY = 0;
		Bitmap bitmap = null;
		Image image = null;
		File mFile = new File(path);

		// 若该文件存在
		if (mFile.exists()) {
			bitmap = BitmapFactory.decodeFile(path);
			width = bitmap.getWidth();
			height = bitmap.getHeight();			
			
			int cut_height = (6000*8)/width;						
			
			if(cut_height>height) {
				cut_height = height;
			} else {
				cut_height = cut_height/24*24;
			}
			
			int cut_count = (height + cut_height - 1) / cut_height;
			
			Log.d("PRINTER", "width:"+width+" height:"+height+" cut_height:"+cut_height);
			
			for (int i = 0; i < cut_count; i++) {
				int cut_image_height = (i==(cut_count-1))?(height-(cut_count-1)*cut_height):cut_height;
				image = new Image(width, cut_image_height, new int[(width) * (cut_image_height)]);
				for (int y = 0; y < cut_height; y++) {
					for (int x = 0; x < width; x++) {
						tmpY = (y + cut_height * i);
						if (tmpY < height) {
							pixel = bitmap.getPixel(x, tmpY);
							image.getPixels()[y * width + x] = pixel;
						}
					}					
				}
				document.addContent(image);
			}
		}
	}

	@SuppressWarnings("unused")
	private static Printable _image(String path) throws IOException {
		int width = 0, height = 0;
		Bitmap bitmap = null;
		File mFile = new File(path);

		// 若该文件存在
		if (mFile.exists()) {
			bitmap = BitmapFactory.decodeFile(path);
			width = bitmap.getWidth();
			height = bitmap.getHeight();
			Log.d("PRINTER", "width:"+width+" height:"+height);
			Image image = new Image(width, height, new int[(width) * (height)]);
			for (int y = 0; y < height; y++) {
				for (int x = 0; x < width; x++) {
					int pixel = bitmap.getPixel(x, y);
					image.getPixels()[y * width + x] = pixel;
				}
			}
			//image.scaleWidth(220);
			return image;
		}

		return null;
	}

	private static List<Printable> _title() {
		List<Printable> list = new ArrayList<>();
		list.add(new Section("购物单", new Font(Font.FontSize.Normal, true, false), TextAlign.Center));
		list.add(new BlankRow());
		return list;
	}

	private static List<Printable> _description() {
		List<Printable> list = new ArrayList<>();
		list.add(new QRcode("http://minminguoye.com", 100));
		list.add(new Section("明明果业是一家专门做高端水果的连锁零售企业，在北京市拥有实体店面313家，扫码加入会员将享受永久9折礼遇！", Font.DEFAULT));
		return list;
	}

	private static List<Printable> _receipt() {
		List<Printable> list = new ArrayList<>();
		list.add(new KeyValue("已收:", "￥1200.00", true));
		list.add(new KeyValue("应收:", "￥1400.00", true));
		list.add(new KeyValue("优惠:", "￥200.00", true));
		list.add(new KeyValue("积分:", "120", true));
		return list;
	}

	private static List<Printable> _orderItem() {
		List<Printable> list = new ArrayList<>();
		String[] header = new String[] { "商品名称", "数量", "总价" };
		int[] cell_width = new int[] { 12, 10, 10 };
		Table table = new Table(header, cell_width, new boolean[] { false, true, true });
		table.addOneRow(new String[] { "老人香蕉", "35", "996.00" });
		table.addOneRow(new String[] { "靓妹木瓜", "10", "80.10" });
		table.addOneRow(new String[] { "热带火龙果", "20", "965.45" });
		table.addOneRow(Table.LINE_ROW);
		table.addOneRow(new String[] { "合计", "960", "188888" });
		list.add(table);
		return list;
	}

	private static List<Printable> _baseInfo() {
		List<Printable> base_info = new ArrayList<>();
		base_info.add(new KeyValue("客　户:", "鹿含(13543267865)"));
		base_info.add(new KeyValue("商　家:", "明明果业"));
		base_info.add(new KeyValue("店　面:", "北京朝阳慧忠里小区店"));
		base_info.add(new KeyValue("地　址:", "北京朝阳慧忠里小区北门13栋04号"));
		base_info.add(new KeyValue("售货员:", "林允儿"));
		return base_info;
	}

	private static List<Printable> _order() {
		List<Printable> list = new ArrayList<>();
		list.add(new Barcode("D00000000016"));
		list.add(new KeyValue("订单号:", "D00000000016"));
		list.add(new KeyValue("日　期:", date_time_format.format(new Date())));
		return list;
	}
	
	private static boolean checkDevice() {
		boolean bRet = false;
		String name = "";
		byte[] str = new byte[15];
		str[0] = 'r';
		str[1] = 'o';
		str[2] = '.';
		str[3] = 'p';
		str[4] = 'r';
		str[5] = 'o';
		str[6] = 'd';
		str[7] = 'u';
		str[8] = 'c';
		str[9] = 't';
		str[10] = '.';
		str[11] = 'n';
		str[12] = 'a';
		str[13] = 'm';
		str[14] = 'e';

		String s = new String(str);
		name = SystemPropertiesGet(s);
		
		if(name.length() > 4) {
			if(name.endsWith("ing")) {
				bRet = true;
			}
		}
		
		return bRet;
	}
	
	private static String SystemPropertiesGet(String key) {
        String result="";
        try {
            Class<?> c = Class.forName("android.os.SystemProperties");

            Method get = c.getMethod("get", String.class);
            result=(String)get.invoke(c, key);

        } catch (ClassNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return result;
	}

}
