package com.nodl.yct380b.convertable;

import com.nodl.print.Converter;
import com.nodl.print.DeviceSetting;
import com.nodl.print.content.convertable.Font;
import com.nodl.yct380b.ESCPOSCode;
import com.nodl.yct380b.util.BytesBuffer;

/**
 * 文档格式
 */
public class FontConverter extends Converter<Font> {

	@Override
	protected byte[] toBytes(DeviceSetting deviceSetting) {

		boolean font_bold = getConvertable().isBold();
		boolean under_line = getConvertable().isUnderline();
		Font.FontSize fontSize = getConvertable().getFontSize();

		BytesBuffer out = new BytesBuffer();

		if (font_bold) {
			out.write(ESCPOSCode.ESC_SET_BOLD);
		} else {
			out.write(ESCPOSCode.ESC_CANCEL_BOLD);
		}

		if (under_line) {
			out.write(ESCPOSCode.FS_SET_UNDERLINE);
		} else {
			out.write(ESCPOSCode.FS_CANCEL_UNDERLINE);
		}

		if (fontSize == Font.FontSize.Big) {
			out.write(ESCPOSCode.ESC_FONT_SIZE_MAX);
		} else if (fontSize == Font.FontSize.Small) {
			out.write(ESCPOSCode.ESC_FONT_SIZE_MIN);
		} else {
			out.write(ESCPOSCode.ESC_FONT_SIZE_MID);
		}

		return out.toByteArray();

	}
}
