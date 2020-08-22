package com.yiking.blog.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.*;

public class TempInitialzer {
    /**
     *  初始化敏感词库，将敏感词加入到HashMap中，构建DFA算法模型
     *
     *  */
        private String ENCODING = "UTF-8";    //字符编码
        private Map<String,String> map = null;
        public Map<String,String> initMap(){
            try {
                map=readSensitiveWordFile();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return map;
        }
        //读取敏感词文件 加到set集合中
        @SuppressWarnings("resource")
        private   Map<String,String> readSensitiveWordFile() throws Exception{

            File file = new File("D:\\YIKING-BLOG\\repeatWords.txt");    //读取文件
            InputStreamReader read = new InputStreamReader(new FileInputStream(file),ENCODING);
            try {
                if(file.isFile() && file.exists()){      //文件流是否存在
                    map= new HashMap<>();
                    BufferedReader bufferedReader = new BufferedReader(read);
                    String txt = null;
                    while((txt = bufferedReader.readLine()) != null){    //读取文件，将文件内容放入到set中
                        map.put(txt,"yiking");
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
            return  map;
        }
    }


