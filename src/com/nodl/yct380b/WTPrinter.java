package com.nodl.yct380b;

import java.io.IOException;

import com.nodl.print.BasePrinter;
import com.nodl.print.Connection;
import com.nodl.print.ConverterKit;
import com.nodl.print.DeviceSetting;
import com.nodl.print.content.Doucument;
import com.nodl.print.exception.PrinterException;

import android.util.Log;

/**
 * 该设备型号为YCT-380B，72MM型，点位密度为 8点/mm 支持接口中的各项打印命令，支持的编码为gbk,每行可打印汉字数量为24个，英文48个
 * 
 */

public class WTPrinter extends BasePrinter {

	@Override
	protected ConverterKit generateConvert() {
		return new WPConverterKit();
	}

	@Override
	protected DeviceSetting generateDeviceSetting(String paramString) {
		return new DeviceSetting(paramString, 72 * 8, "gbk");
	}

	@Override
	public boolean checkConnection(Connection connection, int mode, byte[] buffer) throws IOException {
		
		if((connection == null)||(buffer == null)) {
			return false;
		}
		
		if((mode < 1)||(mode > 4)) {
			return false;
		}
		//Log.d("PRINTER", "checkConnection1");
		if( connection.write(ESCPOSCode.DLE_EOT(mode)) == false) {
			return false;
		}
		
		//Log.d("PRINTER", "checkConnection2");
		
		if( connection.read(buffer) == false) {
			return false;
		}
		//Log.d("PRINTER", "checkConnection3");
		
		return true;
	}

	@Override
	protected void beforPrint(Connection connection) throws IOException {
		//Log.d("PRINTER", "beforPrint");
		connection.write(ESCPOSCode.RESET);
	}

	@Override
	protected void afterPrint(Connection connection) throws IOException {
		//Log.d("PRINTER", "afterPrint");
		connection.write(ESCPOSCode.FIND_BLACK);
		connection.write(ESCPOSCode.ESC_FLL_CUTPAPER);
	}

}
