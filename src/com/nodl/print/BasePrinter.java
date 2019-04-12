package com.nodl.print;

import java.io.IOException;
import java.util.List;

import com.nodl.print.content.Doucument;
import com.nodl.print.content.Printable;
import com.nodl.print.exception.PrinterException;

import android.util.Log;

/**
 * 打印机对象
 */
public abstract class BasePrinter implements IPrinter {

	/**
	 * 设备信息，宽度，像素密度等
	 */
	private DeviceSetting deviceSetting;

	/**
	 * 指令转换器
	 */
	private ConverterKit convertKit;

	/**
	 * 连接，与具体设备的连接通道
	 */
	private Connection connection;

	final void init(String paramString) {
		//Log.d("PRINTER", "BasePrinter init");
		this.deviceSetting = generateDeviceSetting(paramString);
		this.convertKit = generateConvert();

	}

	/**
	 * 获得一个转换器对象
	 * 
	 * @return
	 */
	protected abstract ConverterKit generateConvert();

	/**
	 * 获得一个配置对象
	 * 
	 * @param paramString
	 * @return
	 */
	protected abstract DeviceSetting generateDeviceSetting(String paramString);

	/**
	 * 设置打印机连接，只能设置一次
	 * 
	 * @param connection
	 * @throws PrinterException
	 */
	public void setConnection(final Connection connection) throws PrinterException {
		if (null != this.connection) {
			throw new PrinterException("Connection is already seted.");
		}
		this.connection = connection;
	}

	/**
	 * 检查打印机连接
	 * 
	 * @param connection
	 * @throws IOException
	 */
	public abstract boolean checkConnection(Connection connection,int mode, byte[] buffer) throws IOException;

	/**
	 * 打印文档之前的动作
	 * 
	 * @param connection
	 * @throws IOException
	 */
	protected abstract void beforPrint(Connection connection) throws IOException;

	/**
	 * 打印文档，实际上是调用connection.write(byte[])来写入东西
	 * 
	 * @param content
	 * @return
	 * @throws IOException
	 * @throws PrinterException
	 */
	public boolean print(Doucument content) {
		if (null == connection) {
			//throw new PrinterException("Connection is null, please call setConnection() befor call print.");
			return false;
		}
		//Log.d("PRINTER", "print");
		boolean bRet = false;
		try {
			//checkConnection(connection);
			beforPrint(connection);
			bRet = printDocument(content);
			afterPrint(connection);
			connection.flush();
		} catch (IOException e) {
			//throw new PrinterException(e);
		}
		
		return bRet;
	}

	/**
	 * 打印动作之后的动作
	 * 
	 * @param connection
	 * @throws IOException
	 */
	protected abstract void afterPrint(Connection connection) throws IOException;

	/**
	 * 将打印条目转换为打印机识别的指令
	 * 
	 * @param content
	 * @return
	 * @throws IOException
	 * @throws PrinterException
	 */
	private boolean printDocument(Doucument content) throws PrinterException {
		if(connection == null) {
			return false;
		}
		
		boolean bRet = false;
		List<Printable> printable_list = content.getContents();
		for (Printable printable : printable_list) {
			byte[] bs = convertKit.fromConverter(printable, deviceSetting);
			try {
				bRet = connection.write(bs);
				if(!bRet) {
					return false;
				}
			} catch (IOException e) {
				throw new PrinterException(e);
			}
		}
		
		return bRet;
	}

	public Connection getConnection() {
		return connection;
	}

	public DeviceSetting getDeviceSetting() {
		return deviceSetting;
	}

	public ConverterKit getConvertKit() {
		return convertKit;
	}

}
