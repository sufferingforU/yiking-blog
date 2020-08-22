package com.yiking.blog.utils;

import java.io.*;
import java.util.*;

public class RepeatWordInitialzer {
    private String ENCODING = "UTF-8";    //字符编码
    @SuppressWarnings("rawtypes")
    public HashMap<String,Map> senWordMap =new HashMap<>();

    @SuppressWarnings("rawtypes")
    public Map initKeyWord(){
        try {
            //读取敏感词库
            Map<String, Set<String>> stringSetMap = readSensitiveWordFile();
            //将敏感词库加入到HashMap中
            for(Map.Entry s:stringSetMap.entrySet()){
                addSensitiveWordToHashMap((Set<String>)s.getValue(),(String)s.getKey());
            }

            //spring获取application，然后application.setAttribute("sensitiveWordMap",sensitiveWordMap);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return senWordMap;
    }
    //将得到的敏感词库用一个DFA算法模型放到map中
    @SuppressWarnings({ "rawtypes", "unchecked" })
    private void addSensitiveWordToHashMap(Set<String> keyWordSet,String title) {
        HashMap sensitiveWordMap = new HashMap(keyWordSet.size());     //初始化敏感词容器，减少扩容操作
        String key = null;
        Map nowMap = null;
        Map<String, String> newWorMap = null;
        //迭代keyWordSet
        Iterator<String> iterator = keyWordSet.iterator();
        while(iterator.hasNext()){
            key = iterator.next();    //关键字
            nowMap = sensitiveWordMap;
            for(int i = 0 ; i < key.length() ; i++){
                char keyChar = key.charAt(i);       //转换成char型
                Object wordMap = nowMap.get(keyChar);       //获取

                if(wordMap != null){        //如果存在该key，直接赋值
                    nowMap = (Map) wordMap;
                }
                else{     //不存在则，则构建一个map，同时将isEnd设置为0，因为他不是最后一个
                    newWorMap = new HashMap<String,String>();
                    newWorMap.put("isEnd", "0");     //不是最后一个
                    nowMap.put(keyChar, newWorMap);
                    nowMap = newWorMap;
                }

                if(i == key.length() - 1){
                    nowMap.put("isEnd", "1");    //最后一个
                }
            }
        }
        senWordMap.put(title,sensitiveWordMap);
    }

    //读取敏感词文件 加到set集合中
    @SuppressWarnings("resource")
    private Map<String,Set<String>> readSensitiveWordFile() throws Exception{
        Set<String> set = null;
        HashMap<String, Set<String>> stringSetHashMap = new HashMap<>();
        File file = new File("D:\\YIKING-BLOG\\repeatWords.txt");    //读取文件
        InputStreamReader read = new InputStreamReader(new FileInputStream(file),ENCODING);
        try {
            if(file.isFile() && file.exists()){      //文件流是否存在
                set = new HashSet<String>();
                BufferedReader bufferedReader = new BufferedReader(read);
                String txt = null;
                while((txt = bufferedReader.readLine()) != null){    //读取文件，将文件内容放入到set中

                    if(txt.startsWith("1941844279YIKING")){
                        stringSetHashMap.put(txt.substring(16,txt.length()),set);
                        set=new HashSet<String>();
                        continue;
                    }
                    set.add(txt);
                }
            }
            else{         //不存在抛出异常信息
                throw new Exception("敏感词库文件不存在");
            }
        } catch (Exception e) {
            throw e;
        }finally{
            read.close();     //关闭文件流
        }
        return stringSetHashMap;
    }
}
