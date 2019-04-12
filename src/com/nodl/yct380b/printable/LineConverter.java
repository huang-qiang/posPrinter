package com.nodl.yct380b.printable;

import com.nodl.print.Converter;
import com.nodl.print.DeviceSetting;
import com.nodl.print.content.printable.Image;
import com.nodl.print.content.printable.Line;
import com.nodl.print.exception.PrinterException;
import com.nodl.yct380b.util.BytesBuffer;
import com.nodl.yct380b.util.ImageCreater;

/**
 * 横线转换器
 */
public class LineConverter extends Converter<Line> {

	@Override
	public byte[] toBytes(DeviceSetting deviceSetting) throws PrinterException {

		int width = deviceSetting.getDrawablePixel();
		int hegiht = getConvertable().getHeight();
		boolean dotted = getConvertable().isDotted();

		Image image = ImageCreater.createLine(width, hegiht, dotted);
		image.setMargin(0);

		BytesBuffer out = new BytesBuffer();
		out.write(getConverterKit().fromConverter(image, deviceSetting));
		return out.toByteArray();

	}
}
