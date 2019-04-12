package com.nodl.yct380b.printable;

import java.io.UnsupportedEncodingException;

import com.nodl.print.Converter;
import com.nodl.print.DeviceSetting;
import com.nodl.print.content.convertable.Font;
import com.nodl.print.content.printable.KeyValue;
import com.nodl.print.exception.PrinterException;
import com.nodl.yct380b.ESCPOSCode;
import com.nodl.yct380b.util.BytesBuffer;
import com.nodl.yct380b.util.StringUtil;

/**
 * KeyValue转换器
 */
public class KeyValueConverter extends Converter<KeyValue> {

	@Override
	public byte[] toBytes(DeviceSetting setting) throws PrinterException {
		String key = getConvertable().getKey();
		String value = getConvertable().getValue();
		BytesBuffer out = new BytesBuffer();
		try {
			out.write(getConverterKit().fromConverter(Font.DEFAULT, setting));
			String str = key + " " + value;
			if (getConvertable().isOutter()) {
				if (StringUtil.lengthOfGBK(str) >= StringUtil.getCharEachLine(setting)) {
					out.write(ESCPOSCode.ESC_ALIGN_RIGHT);
					out.write(str.getBytes(setting.getCharset()));
				} else {
					out.write(ESCPOSCode.ESC_ALIGN_LEFT);
					out.write((key + StringUtil.fillLeft2GBKLength(value, ' ', StringUtil.getCharEachLine(setting) - StringUtil.lengthOfGBK(key))).getBytes(setting.getCharset()));
				}
			} else {
				out.write(ESCPOSCode.ESC_ALIGN_LEFT);
				if (StringUtil.lengthOfGBK(key + value) <= StringUtil.getCharEachLine(setting)) {
					out.write(str.getBytes(setting.getCharset()));
				} else {
					int leftblanknum = StringUtil.lengthOfGBK(key + " ");
					String line = StringUtil.toGBKLengthNoAdd(str, StringUtil.getCharEachLine(setting));
					out.write(StringUtil.toGBKLength(line, StringUtil.getCharEachLine(setting)).getBytes(setting.getCharset()));
					do {
						str = str.substring(line.length());
						if (str.isEmpty()) {
							break;
						}
						line = StringUtil.toGBKLengthNoAdd(str, StringUtil.getCharEachLine(setting) - leftblanknum);
						out.write(StringUtil.toGBKLengthNoAdd(repeatBlank(leftblanknum) + line, StringUtil.getCharEachLine(setting)).getBytes(setting.getCharset()));
					} while (true);
				}
			}
			out.write(ESCPOSCode.LF);
			return out.toByteArray();
		} catch (UnsupportedEncodingException e) {
			throw new PrinterException(e);
		}
	}

	private String repeatBlank(int n) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < n; i++) {
			sb.append(' ');
		}
		return sb.toString();
	}
}
