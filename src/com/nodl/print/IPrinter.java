package com.nodl.print;

import com.nodl.print.content.Doucument;
import com.nodl.print.exception.PrinterException;

public interface IPrinter {

	public boolean print(Doucument content);

}
