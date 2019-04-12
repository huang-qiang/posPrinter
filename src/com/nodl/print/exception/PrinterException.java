package com.nodl.print.exception;

/**
 * 打印机对象的异常
 */
public class PrinterException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public PrinterException() {
		super();
	}

	public PrinterException(String message, Throwable cause) {
		super(message, cause);
	}

	public PrinterException(String message) {
		super(message);
	}

	public PrinterException(Throwable cause) {
		super(cause);
	}

}
