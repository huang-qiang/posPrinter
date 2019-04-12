package com.nodl.yct380b.convertable;

import com.nodl.print.Converter;
import com.nodl.print.DeviceSetting;
import com.nodl.print.content.convertable.LF;
import com.nodl.yct380b.ESCPOSCode;

/**
 * 换行转换器
 */
public class LFConverter extends Converter<LF> {

	protected byte[] toBytes(DeviceSetting deviceSetting) {
		return new byte[] { ESCPOSCode.LF };
	}
}
