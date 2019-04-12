package com.nodl.yct380b.printable;

import com.nodl.print.Converter;
import com.nodl.print.DeviceSetting;
import com.nodl.print.content.printable.Image;
import com.nodl.print.content.printable.QRcode;
import com.nodl.print.exception.PrinterException;
import com.nodl.yct380b.util.BytesBuffer;
import com.nodl.yct380b.util.ImageCreater;

/**
 * 二维码转换器
 */
public class QRcodeConverter extends Converter<QRcode> {

	@Override
	public byte[] toBytes(DeviceSetting deviceSetting) throws PrinterException {

		String content = getConvertable().getContent();
		int margin = getConvertable().getMargin();
		int width = getConvertable().getWidth();
		if (0 == width) {
			width = deviceSetting.getDrawablePixel();
		}

		Image image = ImageCreater.creatQRcodeImage(content, width);
		image.setMargin(margin);

		BytesBuffer out = new BytesBuffer();
		out.write(getConverterKit().fromConverter(image, deviceSetting)); // 该指令有打印功能
		return out.toByteArray();

	}
}
