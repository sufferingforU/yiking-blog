package com.yiking.blog.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class StoreDateToFile {
    public void transferDatetoFile(String res, String title) {
        OutputStreamWriter streamWriter = null;
        try {
            File file = new File("D:\\YIKING-BLOG\\repeatWords.txt");
            streamWriter = new OutputStreamWriter(new FileOutputStream(file, true));
            String temp = res.trim();
            temp = temp.replaceAll(",", "&");
            temp = temp.replaceAll(" ", "&");
            temp = temp.replaceAll(";", "&");
            temp = temp.replaceAll("；", "&");
            temp = temp.replaceAll("\\.", "&");
            temp = temp.replaceAll("\\|", "&");
            temp = temp.replaceAll("\\\\", "&");
            temp = temp.replaceAll("\\(", "&");
            temp = temp.replaceAll(":", "&");
            temp = temp.replaceAll("：", "&");
            temp = temp.replaceAll("\\]", "&");
            temp = temp.replaceAll("\\)", "&");
            temp = temp.replaceAll("\\*", "&");
            temp = temp.replaceAll("。", "&");
            temp = temp.replaceAll("\\\"", "&");
            temp = temp.replaceAll("\\?", "&");
            temp = temp.replaceAll("“", "&");
            temp = temp.replaceAll("”", "&");
            temp = temp.replaceAll("[\\t\\n\\r]", "&");
            temp = temp.replaceAll("\\[", "&");
            temp = temp.replaceAll("\\+", "&");
            temp = temp.replaceAll("，", "&");
            temp = temp.replaceAll("#", "&");
            temp = temp.replaceAll("\\'", "&");
            temp = temp.replaceAll("-", "&");
            temp = temp.replaceAll("\\{", "&");
            temp = temp.replaceAll("\\}", "&");
            String[] split = temp.split("&");
            //RepeatWordFilter repeatWordFilter = RepeatWordFilter.getInstance();
            TempInitialzer tempInitialzer = new TempInitialzer();
            Map<String, String> stringStringMap = tempInitialzer.initMap();
            HashMap<String, String> cacheMap = new HashMap<>();

            for (int i = 0; i < split.length; i++) {
                cacheMap.put(split[i],"yiking");
            }
            for (Map.Entry key:cacheMap.entrySet()){
                String str = (String) key.getKey();
                //System.out.println(stringStringMap
                if (str.length() > 2 ) {
                    if (!stringStringMap.containsKey(str)){
                    streamWriter.append(str + "\n");}
                }

            }
            streamWriter.append("1941844279YIKING" + title + "\n");
            streamWriter.flush();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                streamWriter.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
