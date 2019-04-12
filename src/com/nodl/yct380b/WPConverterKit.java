package com.nodl.yct380b;

import java.util.Set;

import com.nodl.print.Converter;
import com.nodl.print.ConverterKit;
import com.nodl.yct380b.convertable.FontConverter;
import com.nodl.yct380b.convertable.LFConverter;
import com.nodl.yct380b.convertable.TextConverter;
import com.nodl.yct380b.printable.BarcodeConverter;
import com.nodl.yct380b.printable.BlankRowConverter;
import com.nodl.yct380b.printable.CutPageConverter;
import com.nodl.yct380b.printable.ImageConverter;
import com.nodl.yct380b.printable.KeyValueConverter;
import com.nodl.yct380b.printable.LineConverter;
import com.nodl.yct380b.printable.QRcodeConverter;
import com.nodl.yct380b.printable.SectionConverter;
import com.nodl.yct380b.printable.TableConverter;

public class WPConverterKit extends ConverterKit {

	@Override
	public void registConverter(Set<Class<? extends Converter<?>>> set) {
		set.add(BarcodeConverter.class);
		set.add(CutPageConverter.class);
		set.add(BlankRowConverter.class);
		set.add(KeyValueConverter.class);
		set.add(ImageConverter.class);
		set.add(LFConverter.class);
		set.add(LineConverter.class);
		set.add(QRcodeConverter.class);
		set.add(TextConverter.class);
		set.add(TableConverter.class);
		set.add(SectionConverter.class);
		set.add(FontConverter.class);
	}

	@Override
	public boolean noMatchConverterException() {
		return true;
	}

}
