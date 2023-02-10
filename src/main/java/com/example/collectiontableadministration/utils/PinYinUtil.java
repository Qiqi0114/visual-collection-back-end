package com.example.collectiontableadministration.utils;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.HanyuPinyinVCharType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

import java.util.Arrays;
import java.util.HashSet;

/**
 * @author 杨子涵 拼音转换
 */
public class PinYinUtil {
 
    private PinYinUtil(){
        throw new IllegalStateException("Utility class");
    }
 
    private static final int ASCII_MIN_SIZE = 32;
 
    private static final int ASCII_MAX_SIZE = 125;
 
    /**
     * @Description: 汉字转换方法（数字与字母不转换）
     * @param ch: 单个字符
     * @return: java.lang.String[]
     **/
    public static String[] getPinyin(char ch) {
        try {
            HanyuPinyinOutputFormat outputFormat = new HanyuPinyinOutputFormat();
            outputFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
            outputFormat.setCaseType(HanyuPinyinCaseType.LOWERCASE);
            outputFormat.setVCharType(HanyuPinyinVCharType.WITH_V);
            //ASCII >=33 ASCII<=125的直接返回 ,ASCII码表：http://www.asciitable.com/
            if (ch >= ASCII_MIN_SIZE && ch <= ASCII_MAX_SIZE) {
                return new String[]{String.valueOf(ch)};
            }
            return distinct(PinyinHelper.toHanyuPinyinStringArray(ch, outputFormat));
        } catch (BadHanyuPinyinOutputFormatCombination e) {
            throw new IllegalStateException(e);
        }
    }
 
    private static String[] distinct(String[] arr) {
        if (arr == null || arr.length == 0) {
            return arr;
        }
        HashSet<String> set = new HashSet<>();
        for (String str : arr) {
            set.add(str);
        }
        return set.toArray(new String[0]);
    }

    public static String getString(String str) {
        StringBuilder sb =new StringBuilder();
        for (char c : str.toCharArray()) {
            String[] pinyin = PinYinUtil.getPinyin(c);
            Arrays.stream(pinyin).forEach(s->{sb.append(s);});
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        System.out.println(getString("杨子涵"));
    }
}
