package org.pan.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

/**
 * Created by panmingzhi on 2016/11/20 0020.
 */
public class GenerateUtil {
    public static char[] chars = {'A','B','C','D','E','F','1','2','3','4','5','6','7','8','9','0'};
    public static List<String> generateSCard(int length, int count) {
        List<String> stringList = new ArrayList<>();
        Random random = new Random();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < count; i++) {
            for (int j = 0; j < length; j++) {
                int nextInt = random.nextInt(chars.length - 1);
                sb.append(chars[nextInt]);
            }
            stringList.add(sb.toString());
            sb.delete(0,length);
        }
        return stringList;
    }

    public static void main(String[] args) {
        List<String> stringList = generateSCard(8, 1000);
        for (String s : stringList) {
            System.out.println(s);
        }
    }
}
