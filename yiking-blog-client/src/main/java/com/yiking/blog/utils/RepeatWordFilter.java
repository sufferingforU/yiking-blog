package com.yiking.blog.utils;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.*;

public class RepeatWordFilter {
    @SuppressWarnings("rawtypes")
    public Map<String, Map> sensitiveWordMap = null;
    public static int minMatchTYpe = 1;      //最小匹配规则
    public static int maxMatchType = 2;      //最大匹配规则

    /**
     * 构造函数，初始化敏感词库
     */


    public RepeatWordFilter() {
        sensitiveWordMap = (HashMap<String, Map>) new RepeatWordInitialzer().initKeyWord();
        //System.out.println(sensitiveWordMap);
    }



    /**
     * 判断文字是否包含敏感字符
     *
     * @param txt       文字
     * @param matchType 匹配规则&nbsp;1：最小匹配规则，2：最大匹配规则
     * @return 若包含返回true，否则返回false
     * @version 1.0
     */
    public boolean isContaintSensitiveWord(String txt, int matchType, Map map) {
        boolean flag = false;
        for (int i = 0; i < txt.length(); i++) {
            int matchFlag = this.CheckSensitiveWord(txt, i, matchType, map); //判断是否包含敏感字符
            if (matchFlag > 0) {    //大于0存在，返回true
                flag = true;
            }
        }
        return flag;
    }
    public boolean isContaintSensitiveWord2(String txt, int matchType) {
        Map map = new HashMap();
        for (Map.Entry m : sensitiveWordMap.entrySet()){
            map.putAll((Map) m.getValue());
        }
        boolean flag = false;
        for (int i = 0; i < txt.length(); i++) {
            int matchFlag = this.CheckSensitiveWord(txt, i, matchType, map); //判断是否包含敏感字符
            if (matchFlag > 0) {    //大于0存在，返回true
                flag = true;
            }
        }
        return flag;
    }

    /**
     * 检查文字中是否包含敏感字符，检查规则如下：
     *
     * @param txt
     * @param beginIndex
     * @param matchType
     * @return，如果存在，则返回敏感词字符的长度，不存在返回0
     * @version 1.0
     */
    @SuppressWarnings({"rawtypes"})
    public int CheckSensitiveWord(String txt, int beginIndex, int matchType, Map senMap) {
        boolean flag = false;    //敏感词结束标识位：用于敏感词只有1位的情况
        int matchFlag = 0;     //匹配标识数默认为0
        char word = 0;
        Map nowMap = senMap;
        for (int i = beginIndex; i < txt.length(); i++) {
            word = txt.charAt(i);
            //System.out.println(nowMap);
            nowMap = (Map) nowMap.get(word);     //获取指定key
            // System.out.println(nowMap);
            if (nowMap != null) {     //存在，则判断是否为最后一个
                matchFlag++;     //找到相应key，匹配标识+1
                if ("1".equals(nowMap.get("isEnd"))) {       //如果为最后一个匹配规则,结束循环，返回匹配标识数
                    flag = true;       //结束标志位为true
                    if (SensitivewordFilter.minMatchTYpe == matchType) {    //最小规则，直接返回,最大规则还需继续查找
                        break;
                    }
                }
            } else {     //不存在，直接返回
                break;
            }
        }
        if (matchFlag < 2 || !flag) {        //长度必须大于等于1，为词
            matchFlag = 0;
        }
        return matchFlag;
    }


    /**
     * 获取文字中的敏感词
     *
     * @param matchType 匹配规则&nbsp;1：最小匹配规则，2：最大匹配规则
     * @return
     * @version 1.0
     */
    public Set<String> getSensitiveWord(String txt, int matchType, Map map) {
        Set<String> sensitiveWordList = new HashSet<String>();

        for (int i = 0; i < txt.length(); i++) {
            int length = CheckSensitiveWord(txt, i, matchType, map);    //判断是否包含敏感字符
            if (length > 0) {    //存在,加入list中
                sensitiveWordList.add(txt.substring(i, i + length));
                i = i + length - 1;    //减1的原因，是因为for会自增
            }
        }
        return sensitiveWordList;
    }

    public Set<String> getSensitiveWord(String txt, int matchType) {
        Set<String> sensitiveWordList = new HashSet<String>();
        Map map = new HashMap();
        for (Map.Entry m : sensitiveWordMap.entrySet()) {
            map.putAll((Map) m.getValue());
        }
        for (int i = 0; i < txt.length(); i++) {
            int length = CheckSensitiveWord(txt, i, matchType, map);    //判断是否包含敏感字符
            if (length > 0) {    //存在,加入list中
                sensitiveWordList.add(txt.substring(i, i + length));
                i = i + length - 1;    //减1的原因，是因为for会自增
            }
        }
        return sensitiveWordList;
    }

    //    public double statisticRepeatedWords(String des){
//        Set<String> sensitiveWord = getSensitiveWord(des, 2);
//        int repeat=0;
//        for (String s: sensitiveWord) {
//            repeat+=s.length();
//        }
//        System.out.println(des.length()+"->"+repeat);
//        return repeat*1.0/des.length();
//    }
    public Map<String, Object> showRepeatWords(String des) {
        HashMap<String, Object> ans = new HashMap<>();
        String temp = des;
        int realLengthOfRepeat = getRealLengthOfRepeat(des);
        DecimalFormat df = new DecimalFormat("0.00");
        df.setRoundingMode(RoundingMode.HALF_UP);
        List repeatArticle = new ArrayList<Object>();
        int sum = 0;
        for (Map.Entry map : sensitiveWordMap.entrySet()) {
            Map senMap = (Map) map.getValue();
            String keys = (String) map.getKey();
            String[] split = keys.split("-");
            String title = split[0];
            String userName = split[1];
            String aid = split[2];

            Set<String> sensitiveWord = getSensitiveWord(des, 2, senMap);
            String res = des;
            int len = 0;
            for (String s : sensitiveWord) {//System.out.println(s);
                len += s.length();
                res = res.replaceAll(s, "<mark>" + s + "</mark>");
            }

            if (len * 1.0 / realLengthOfRepeat> 0.05) {
                sum+=len;
                for (String s : sensitiveWord) {
                    temp = temp.replaceAll(s, "<mark>" + s + "</mark>");
                }
                ArrayList<Object> list = new ArrayList<>();
                String format = df.format(len * 1.0 / realLengthOfRepeat * 100) + "%";
                list.add(title);
                list.add(res);
                list.add(format);
                list.add(aid);
                list.add(userName);
                repeatArticle.add(list);
            }

        }
        ans.put("repeatArticle", repeatArticle);
        ans.put("allRepeat", temp);
        ans.put("allrepeats", sum * 1.0 /realLengthOfRepeat* 100);
        ans.put("repeatRates", df.format(sum * 1.0 /realLengthOfRepeat * 100) + "%");
        return ans;
    }

    public int getRealLengthOfRepeat(String des){
        String temp = des.trim();
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
        temp = temp.replaceAll("&", "");
        return temp.length();
    }
  /*  public static void main(String[] args) throws IOException {
    RepeatWordFilter repeatWordFilter = new RepeatWordFilter();
       // MyRepeatWordFilter myRepeatWordFilter = new MyRepeatWordFilter();
        String ss=new String("为了保证持久性，必须将数据在事务提交前写到磁盘。只要事务成功提交，数据必然已经持久化。\n" +
                "\n" +
                "Undo log必须先于数据持久化到磁盘。如果在G,H之间系统崩溃，undo log是完整的， 可以用来回滚事务。\n" +
                "\n" +
                "如果在A-F之间系统崩溃,因为数据没有持久化到磁盘。所以磁盘上的数据还是保持在事务开始前的状态。");
       String temp=ss.replaceAll(" ","");
        temp = temp.replaceAll(",", "");
        temp = temp.replaceAll("\\.", "");
        temp = temp.replaceAll("。", "");
        temp = temp.replaceAll("，", "");
        System.out.println(repeatWordFilter.statisticRepeatedWords(temp)*100+"%");
        boolean word = repeatWordFilter.isContaintSensitiveWord(ss, 2);
      System.out.println(word);
        Set<String> sensitiveWord = repeatWordFilter.getSensitiveWord(ss, 2);
        for (String s: sensitiveWord) {
            //System.out.println(s);
            ss = ss.replaceAll( s , "(->"+s+"<-)");

        }
        System.out.println(ss);

//         HashMap sensitiveWordMap = new HashMap();
//        Map newWorMap;
//        Map nowMap=sensitiveWordMap;
//        newWorMap = new HashMap<String,String>();
//        newWorMap.put("isEnd", "0");     //不是最后一个
//        nowMap.put("日", newWorMap);
//        nowMap = newWorMap;
//        nowMap.put("isEnd", "1");
//        System.out.println(sensitiveWordMap);
    }*/
}
