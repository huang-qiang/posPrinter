package com.nodl.yct380b.printable;

import com.nodl.print.Converter;
import com.nodl.print.DeviceSetting;
import com.nodl.print.content.printable.Barcode;
import com.nodl.print.content.printable.Image;
import com.nodl.print.exception.PrinterException;
import com.nodl.yct380b.WPConsts;
import com.nodl.yct380b.util.BytesBuffer;
import com.nodl.yct380b.util.ImageCreater;

/**
 * 条形码转换器
 */
public class BarcodeConverter extends Converter<Barcode> {

	@Override
	public byte[] toBytes(DeviceSetting deviceSetting) throws PrinterException {

		String content = getConvertable().getContent();
		int margin = getConvertable().getMargin();
		int height = getConvertable().getHeight();
		if (0 == height) {
			height = WPConsts.DEFAULT_BARCODE_HEIGHT;
		}

		Image image = ImageCreater.creatBarcodeImage(deviceSetting.getDrawablePixel(), height, content);
		image.setMargin(margin);

		BytesBuffer out = new BytesBuffer();
		out.write(getConverterKit().fromConverter(image, deviceSetting));
		return out.toByteArray();
	}
}
