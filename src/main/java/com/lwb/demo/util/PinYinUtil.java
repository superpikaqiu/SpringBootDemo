package com.lwb.demo.util;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.HanyuPinyinVCharType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

/**
 * @author lwb
 * @date 2021/1/19 9:53
 * @Description TODO
 */
public class PinYinUtil {
    public static String getPinYin(String str){
        char [] chars = str.toCharArray();

        HanyuPinyinOutputFormat outputFormat = new HanyuPinyinOutputFormat();
        outputFormat.setCaseType(HanyuPinyinCaseType.LOWERCASE);
        outputFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
        outputFormat.setVCharType(HanyuPinyinVCharType.WITH_V);

        StringBuilder stringBuilder = new StringBuilder();
        try{
            for(int i=0;i<chars.length;i++){
                if(String.valueOf(chars[i]).matches("[\\u4E00-\\u9FA5]+")){
                    String[] pinyinArray = PinyinHelper.toHanyuPinyinStringArray(chars[i],outputFormat);
                    if(pinyinArray != null && pinyinArray.length > 0){
                        stringBuilder.append(pinyinArray[0]);
                    }
                } else{
                    stringBuilder.append(chars[i]);
                }
            }


        } catch (BadHanyuPinyinOutputFormatCombination e){
            e.printStackTrace();
        }

        return stringBuilder.toString();
    }
}
