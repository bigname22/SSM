package com.bigname.common;

public class StringUtil {
	public static boolean isNullOrZero (String str) {
		if(str == null || str.trim().length() == 0) {
			return true;
		}
		return false;
	}
}
