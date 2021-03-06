package com.etoc.weflow.utils;

import android.text.TextUtils;

public class StringUtils {
	public static String ToEllipsize(String input, int totalLen) {
		String s = ToSBC(input);
		char ch;
		int character = 0, blank = 0, number = 0, chinese=0, other = 0;
		double len = 0;
		int offset = 0;

		for (int i = 0; i < s.length(); i++) // for循环
		{
			ch = s.charAt(i);
			if ((ch >= 'a' && ch <= 'z') || (ch >= 'A' && ch <= 'Z')) {// 统计字母
				character++; 
				len=len+0.5;
				offset++;
			}
			else if (ch == ' '/* || ch=='：'*/) {// 统计空格和：
				blank++; 
				len=len+0.5;
				offset++;
			}
			else if (ch >= '0' && ch <= '9') { // 统计数字
				number++; 
				len=len+0.5;
				offset++;
			}else if (isChinese(ch)) { // 统汉字
				chinese++; 
				len=len+1;
				offset++;
			}
			else {
				other++;
				len=len+0.5;
				offset++;
			}
			
			if(len>totalLen-1.5){
				break;
			}
		}
		
		if(offset<s.length()){
			return s.substring(0, offset) + "...";
		}else{
			return s;
		}

	}
	public static String ToSBC(String input) {
		// 半角转全角：
		char[] c = input.toCharArray();
		for (int i = 0; i < c.length; i++) {
			if (c[i] == 32) {
				c[i] = (char) 12288;
				continue;
			}
			if (c[i] < 127 && c[i] > 32)
				c[i] = (char) (c[i] + 65248);
		}
		return new String(c);
	}
	
	// 全角转化为半角的方法
	public static String ToDBC(String input) {
		char[] c = input.toCharArray();
		for (int i = 0; i < c.length; i++) {
			if (isChinese(c[i])) {
				if (c[i] == 12288) {
					c[i] = (char) 32;
					continue;
				}
				if (c[i] > 65280 && c[i] < 65375)
					c[i] = (char) (c[i] - 65248);
			}
		}
		return new String(c);
	}
	
	
	public static boolean isChinese(char c) {
		Character.UnicodeBlock ub = Character.UnicodeBlock.of(c);
		if (ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS
				|| ub == Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS
				|| ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A
				|| ub == Character.UnicodeBlock.GENERAL_PUNCTUATION
				|| ub == Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION
				|| ub == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS) {
			return true;
		}
		return false;
	}
	
	public static String getStringInfo(String s) {// 获得字母、数字、空格的个数
		char ch;
		int character = 0, blank = 0, number = 0, chinese=0, other = 0;
		for (int i = 0; i < s.length(); i++) // for循环
		{
			ch = s.charAt(i);
			if ((ch >= 'a' && ch <= 'z') || (ch >= 'A' && ch <= 'Z')) {// 统计字母
				character++; 
			}
			else if (ch == ' ') {// 统计空格
				blank++; 
			}
			else if (ch >= '0' && ch <= '9') { // 统计数字
				number++;
			}
			else if (isChinese(ch)) { // 统计中文
				chinese++;
			}
			else {
				other++;
			}
		}
		return "字符串中共有" + character + "个字母," + blank + "个空格," + number + "个数字, " + chinese + "个汉字, "+ other + "个其它字符";
	}
	
	public static boolean isChineseChar(char c) throws Exception {// 判断是否是一个汉字
		return String.valueOf(c).getBytes("GBK").length > 1;// 汉字的字节数大于1
	}
	
	public static int getChineseCount(String s) throws Exception {// 获得汉字的长度
		char c;
		int chineseCount = 0;
		if (!"".equals("")) {// 判断是否为空
			s = new String(s.getBytes(), "GBK"); // 进行统一编码
		}
		for (int i = 0; i < s.length(); i++) {// for循环
			c = s.charAt(i); // 获得字符串中的每个字符
			if (isChineseChar(c)) {// 调用方法进行判断是否是汉字
				chineseCount++; // 等同于chineseCount=chineseCount+1
			}
		}
		return chineseCount; // 返回汉字个数
	}

	public static String nullToEmpty(String string) {
		if (string == null || string.equalsIgnoreCase("null")) {
			string = "";
		}
		return string;
	}
	
	public static String emptyToNull(String string) {
		if (string != null && TextUtils.isEmpty(string)) {
			string = null;
		}
		return string;
	}
	
	/**
     * is null or its length is 0
     * 
     * <pre>
     * isEmpty(null) = true;
     * isEmpty(&quot;&quot;) = true;
     * isEmpty(&quot;  &quot;) = false;
     * </pre>
     * 
     * @param str
     * @return if string is null or its size is 0, return true, else return false.
     */
	public static boolean isEmpty(CharSequence str) {
        return (str == null || str.length() == 0);
    }
	
	/**
     * is null or its length is 0 or it is made by space
     * 
     * <pre>
     * isBlank(null) = true;
     * isBlank(&quot;&quot;) = true;
     * isBlank(&quot;  &quot;) = true;
     * isBlank(&quot;a&quot;) = false;
     * isBlank(&quot;a &quot;) = false;
     * isBlank(&quot; a&quot;) = false;
     * isBlank(&quot;a b&quot;) = false;
     * </pre>
     * 
     * @param str
     * @return if string is null or its size is 0 or it is made by space, return true, else return false.
     */
    public static boolean isBlank(String str) {
        return (str == null || str.trim().length() == 0);
    }

}
