package com.nodl.print;

import java.io.IOException;

/**
 * 只可写的连接
 */
public abstract class OWConnection implements Connection {

	/**
	 * 永远只返回-1
	 */
	public boolean read(byte[] bytes) throws IOException {
		return false;
	}

}
