/**
 * Project Name:family
 * File Name:PingYinUtil.java
 * Package Name:com.ly.family.util
 * Date:2013-7-22下午10:26:32
 * Copyright (c) 2013, ly.boy2012@gmail.com All Rights Reserved.
 *
*/

package cn.weeon.job.common;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.HanyuPinyinVCharType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;


/**
 * ClassName:PingYinUtil 
 * Reason:	 TODO ADD REASON. 
 * Date:     2013-7-22 下午10:26:32
 * @author   liying
 * @version  1.0
 * @since    JDK 1.6
 * @see 	 
 */
public class PingYinUtil {
	 public static String getPingYin(String inputString) {
         HanyuPinyinOutputFormat format = new
         HanyuPinyinOutputFormat();
         format.setCaseType(HanyuPinyinCaseType.LOWERCASE);
         format.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
         format.setVCharType(HanyuPinyinVCharType.WITH_V);

         char[] input = inputString.trim().toCharArray();
         String output = "";

         try {
             for (int i = 0; i < input.length; i++) {
                 if (Character.toString(input[i]).
                 matches("[\\u4E00-\\u9FA5]+")) {
                     String[] temp = PinyinHelper.
                     toHanyuPinyinStringArray(input[i],
                     format);
                     output += temp[0];
                 } else
                     output += Character.toString(
                     input[i]);
             }
         } catch (BadHanyuPinyinOutputFormatCombination e) {
             e.printStackTrace();
         }
         
         return output;
     }
	 
	 
	 public static String converterToFirstSpell(String chines){             
         String pinyinName = "";      
        char[] nameChar = chines.toCharArray();      
         HanyuPinyinOutputFormat defaultFormat = new HanyuPinyinOutputFormat();      
         defaultFormat.setCaseType(HanyuPinyinCaseType.UPPERCASE);      
         defaultFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);      
        for (int i = 0; i < nameChar.length; i++) {      
            if (nameChar[i] > 128) {      
                try {      
                     pinyinName += PinyinHelper.toHanyuPinyinStringArray(nameChar[i], defaultFormat)[0].charAt(0);      
                 } catch (BadHanyuPinyinOutputFormatCombination e) {      
                     e.printStackTrace();      
                 }      
             }else{      
                 pinyinName += nameChar[i];      
             }      
         }      
        return pinyinName;      
     }      
}

