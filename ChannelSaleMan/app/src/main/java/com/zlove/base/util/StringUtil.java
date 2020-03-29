
package com.zlove.base.util;


public class StringUtil {

	public static boolean isContains(String source, String content) {
		if (source.indexOf(content) != -1) {
			return true;
		}
		return false;
	}
}
