package com.common.utils;

import java.io.UnsupportedEncodingException;

/**
 * The <code>Charsets</code> utility class provides a set of useful constants. and methods for processing charsets and
 * character encoding.
 */
public class Charsets {

	public static String ISO8859_1 = "ISO8859_1";

	public static String GB2312 = "GB2312";

	public static String GBK = "GBK";

	public static String UTF_8 = "UTF-8";

	public static String UTF_16 = "UTF-16";

	/**
	 * Encode the specified byte array to a {@code String} using the given charset.
	 * 
	 * @param bytes
	 *            original byte array
	 * @param charset
	 *            specified charset
	 * @return a {@code String} object
	 * @throws java.io.UnsupportedEncodingException
	 *             If the character encoding is not supported.
	 */
	public static String encode(byte[] bytes, String charset) throws UnsupportedEncodingException {
		return new String(bytes, charset);
	}
}
