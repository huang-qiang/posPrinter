package com.nodl.print;

import java.io.IOException;

public interface Connection {

	boolean write(byte[] bytes) throws IOException;

	boolean write(byte[] bytes, int start, int length) throws IOException;

	void flush() throws IOException;

	boolean read(byte[] bytes) throws IOException;

	void close() throws IOException;

}
