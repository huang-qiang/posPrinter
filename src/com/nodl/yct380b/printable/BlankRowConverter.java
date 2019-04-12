package com.nodl.yct380b.printable;

import com.nodl.print.Converter;
import com.nodl.print.DeviceSetting;
import com.nodl.print.content.printable.BlankRow;
import com.nodl.yct380b.ESCPOSCode;

/**
 * 空行转换器
 */
public class BlankRowConverter extends Converter<BlankRow> {

	@Override
	public byte[] toBytes(DeviceSetting deviceSetting) {
		return ESCPOSCode.ESC_FORWARD_LINE(getConvertable().getLineNumber());
	}

}
