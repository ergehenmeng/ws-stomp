package com.eghm.websocket.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


public class StringUtil {
	
	
	private static final char UNDERLINE='_';
	
	private static final char DASH = '-';
	
	
	/**
	 * 驼峰式转换对应的格式
	 * @param param 传递的参数
	 * @param character 格式类型:'_','-','@','#','%','^','&','*'
	 * @return
	 */
	public static String getSwitchCase(String param,Character character){
	       if (param==null||"".equals(param.trim())){  
	           return "";  
	       }  
	       int len=param.length();  
	       StringBuilder sb=new StringBuilder(len);  
	       for (int i = 0; i < len; i++) {  
	           char c=param.charAt(i);  
	           if (Character.isUpperCase(c)){  
	               sb.append(character);  
	               sb.append(Character.toLowerCase(c));  
	           }else{  
	               sb.append(c);  
	           }  
	       }  
	       return sb.toString(); 
	}
	
	/**
	 * 将驼峰式转换为下划线
	 * @param param 参数
	 * @return
	 */
	public static String getUnderlineCase(String param){
		return getSwitchCase(param , UNDERLINE);
	}
	
	/**
	 * 将驼峰式转为横杠
	 * @param param
	 * @return
	 */
	public static String getDashlineCase(String param){
		return getSwitchCase(param,DASH);
	}
	
	/**
	 * 下划线转驼峰式
	 * @param s
	 * @return
	 */
	public static String toCamelCase(String s) {
        if (s == null) {
            return null;
        }
        s = s.toLowerCase();
        StringBuilder sb = new StringBuilder(s.length());
        boolean upperCase = false;
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
 
            if (c == UNDERLINE) {
                upperCase = true;
            } else if (upperCase) {
                sb.append(Character.toUpperCase(c));
                upperCase = false;
            } else {
                sb.append(c);
            }
        }
 
        return sb.toString();
    }
	
	
	 public static String md5(String param) {
	        String result = null;
	        if (param != null) {
	            try {
	                // 指定加密的方式为MD5
	                MessageDigest md = MessageDigest.getInstance("MD5");
	                // 进行加密运算
	                byte bytes[] = md.digest(param.getBytes());
	                for (int i = 0; i < bytes.length; i++) {
	                    // 将整数转换成十六进制形式的字符串 这里与0xff进行与运算的原因是保证转换结果为32位
	                    String str = Integer.toHexString(bytes[i] & 0xFF);
	                    if (str.length() == 1) {
	                        str += "F";
	                    }
	                    result += str;
	                }
	                return result;
	            } catch (NoSuchAlgorithmException e) {
	                e.printStackTrace();
	            }
	        }
	        return null;
	  }
	
	/**
	 * 基本解密算法
	 * @param str
	 * @return
	 */
	private static String decrypt(String str){
		try {
			String[] string = str.split("%");
			StringBuffer result = new StringBuffer();
			for(int i=0;i < string.length;i++){
				result.append((char)(Integer.parseInt(string[i])-27));
			}
			return result.toString();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	
	/**
	 * 将字符串转换为二进制,并且用" "来确定一个字符串长度
	 * @param str
	 * @return
	 */
	private static String StrToBinstr(String str){
		char[] strChar = str.toCharArray();
		StringBuffer sb = new StringBuffer();
		for(int i = 0;i < strChar.length;i++){
			sb.append(Integer.toBinaryString(strChar[i])+" ");
		}
		return sb.toString();
	}
	
	/**
	 * 将二进制转换为Unicode字符串格式
	 * @param binStr
	 * @return
	 */
	private static String BinstrToStr(String binStr) {
        String[] tempStr=StrToStrArray(binStr);
        char[] tempChar=new char[tempStr.length];
        for(int i=0;i<tempStr.length;i++) {
            tempChar[i]=BinstrToChar(tempStr[i]);
        }
	    return String.valueOf(tempChar);
	}
	
	/**
	 * 将二进制字符串转换为char
	 * @param binStr
	 * @return
	 */
	private static char BinstrToChar(String binStr) {
		int[] temp=BinstrToIntArray(binStr);
        int sum=0;   
        for(int i=0; i<temp.length;i++){
            sum +=temp[temp.length-1-i]<<i;
        }   
        return (char)sum;
	}
	
    
    /**
     * 将二进制字符串转换为数组
     * @param binStr
     * @return
     */
    private static int[] BinstrToIntArray(String binStr) {       
        char[] temp=binStr.toCharArray();
        int[] result=new int[temp.length];   
        for(int i=0;i<temp.length;i++) {
            result[i]=temp[i]-48;
        }
        return result;
    }
	/**
	 * 二进制切分
	 * @param str
	 * @return
	 */
	private static String[] StrToStrArray(String str) {
	        return str.split(" ");
	}
}
