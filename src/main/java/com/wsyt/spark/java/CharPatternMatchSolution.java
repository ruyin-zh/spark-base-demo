package com.wsyt.spark.java;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author ruyin_zh
 * @Date 2020/11/3
 * @Title
 * @Desc
 **/
public class CharPatternMatchSolution {

    public static boolean charPatternMatch(String pattern,String matchStr){
        if(pattern == null || matchStr == null){
            return false;
        }

        Map<Character,String> charMap = new HashMap<>();

        char[] patternArray = pattern.toCharArray();
        String[] strArray = matchStr.split(" ");

        int paLen = patternArray.length;
        int saLen = strArray.length;
        if(paLen != saLen) return false;

        boolean keyExist = false;
        boolean valExist = false;
        String val = null;
        for(int i = 0; i < paLen; i ++){
            keyExist = charMap.containsKey(patternArray[i]);
            valExist = charMap.containsValue(strArray[i]);

            val = charMap.get(patternArray[i]);

            if(keyExist){
                if(!val.equals(strArray[i])){
                    return false;
                }
            }else{
                if(valExist) return false;
                else charMap.put(patternArray[i],strArray[i]);

            }
        }

        return true;
    }


    public static void main(String[] args){
        System.out.println(charPatternMatch("abbc","北京 杭州 杭州 上海"));
        System.out.println(charPatternMatch("abba","北京 杭州 杭州 北京"));
    }
}
