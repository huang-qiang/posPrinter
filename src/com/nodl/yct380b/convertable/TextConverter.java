package com.nodl.yct380b.convertable;

import java.io.UnsupportedEncodingException;

import com.nodl.print.Converter;
import com.nodl.print.DeviceSetting;
import com.nodl.print.content.convertable.Font;
import com.nodl.print.content.convertable.Text;
import com.nodl.print.exception.PrinterException;
import com.nodl.yct380b.util.BytesBuffer;

/**
 * 文本转换器
 */
public class TextConverter extends Converter<Text> {

	@Override
	protected byte[] toBytes(DeviceSetting setting) throws PrinterException {
		Font font = getConvertable().getFont();
		String content = getConvertable().getText();
		BytesBuffer out = new BytesBuffer();
		out.write(getConverterKit().fromConverter(font, setting));
		try {
			out.write(content.getBytes(setting.getCharset()));
		} catch (UnsupportedEncodingException e) {
			throw new PrinterException(e);
		}
		return out.toByteArray();
	}
}
