package com.nodl.yct380b.printable;

import com.nodl.print.Converter;
import com.nodl.print.DeviceSetting;
import com.nodl.print.content.printable.CutPage;
import com.nodl.yct380b.ESCPOSCode;
import com.nodl.yct380b.util.BytesBuffer;

/**
 * 切纸转换器
 */
public class CutPageConverter extends Converter<CutPage> {

	public byte[] toBytes(DeviceSetting deviceSetting) {
		BytesBuffer out = new BytesBuffer();
		out.write(ESCPOSCode.ESC_FORWARD_LINE(6));
		return out.toByteArray();
	}

}
