package com.nodl.yct380b.printable;

import java.util.List;

import com.nodl.print.Converter;
import com.nodl.print.DeviceSetting;
import com.nodl.print.content.Convertable;
import com.nodl.print.content.printable.Section;
import com.nodl.print.content.printable.Section.TextAlign;
import com.nodl.print.exception.PrinterException;
import com.nodl.yct380b.ESCPOSCode;
import com.nodl.yct380b.util.BytesBuffer;
import com.nodl.yct380b.util.TextAlignUtil;

/**
 * 打印文字段落命令
 */
public class SectionConverter extends Converter<Section> {

	@Override
	public byte[] toBytes(DeviceSetting deviceSetting) throws PrinterException {
		List<Convertable> contents = getConvertable().getConvertables();
		TextAlign align = getConvertable().getTextAlign();
		BytesBuffer out = new BytesBuffer();
		out.write(TextAlignUtil.toBytes(align));
		for (Convertable text : contents) {
			out.write(getConverterKit().fromConverter(text, deviceSetting));
		}
		out.write(ESCPOSCode.LF);
		return out.toByteArray();
	}
}
