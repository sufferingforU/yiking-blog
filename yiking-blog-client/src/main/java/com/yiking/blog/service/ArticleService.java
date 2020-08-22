package com.yiking.blog.service;


import com.yiking.blog.entities.Article;
import com.yiking.blog.entities.SearchCondition;
import com.yiking.blog.entities.Tags;
import com.yiking.blog.mapper.ArticleMapper;
import com.yiking.blog.mapper.SearchConditionMapper;
import com.yiking.blog.mapper.TagsMapper;
import com.yiking.blog.utils.SensitivewordFilter;
import com.yiking.blog.utils.UserUtil;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.util.List;

/**
 * Created by  yiking 2020/07/03
 */
@Service
@Transactional
public class ArticleService {
    @Autowired
    ArticleMapper articleMapper;
    @Autowired
    TagsMapper tagsMapper;
    @Autowired
    SearchConditionMapper searchConditionMapper;
    //@Cacheable(value="allarticle",key="'allarticle_'+#", unless = "#startPage == null")
    public List<Article> getAllArticles(int startPage) {

        return articleMapper.getAllArticles(startPage);
    }
    //@Cacheable(value="allarticle",key="'allarticle'")
    public List<Article> getAllArticles2() {
        return articleMapper.getAllArticles2();
    }
  //  @Cacheable(value = "allarticle",key = "'myarticles_'+#uid",unless ="#uid==null" )
    public List<Article> getMyArticles(Long uid){
        return articleMapper.getMyArticles(uid);
    }

    public int getArticleCount() {
        return articleMapper.getArticleCount();
    }
  // @CachePut(value = "article",key="'article_'+#article.title")
    public int addNewArticle(Article article) {
        //过滤文章
        SensitivewordFilter filter = SensitivewordFilter.getSensitivewordFilter();
        String hContent = filter.checkAndReplaceComments(article.getHtmlContent());
        article.setHtmlContent(hContent);
        //处理文章摘要
        if (article.getSummary() == null || "".equals(article.getSummary())) {
            //直接截取
            String stripHtml = stripHtml(article.getHtmlContent());
            article.setSummary(stripHtml.substring(0, stripHtml.length() > 50 ? 50 : stripHtml.length()));
        }
        if (article.getId() == -1) {
            //添加操作
            Timestamp timestamp = new Timestamp(System.currentTimeMillis());
            if (article.getState() == 1) {
                //设置发表日期
                article.setPublishDate(timestamp);
            }
            article.setEditTime(timestamp);
            //设置当前用户
            article.setUid(UserUtil.getCurrentUser().getId());
            int i = articleMapper.addNewArticle(article);
            //打标签
            String[] dynamicTags = article.getDynamicTags();
            if (dynamicTags != null && dynamicTags.length > 0) {
                int tags = addTagsToArticle(dynamicTags, article.getId());
                if (tags == -1) {
                    return tags;
                }
            }
            return i;
        } else {
            Timestamp timestamp = new Timestamp(System.currentTimeMillis());
            if (article.getState() == 1) {
                //设置发表日期
                article.setPublishDate(timestamp);
            }
            //更新
            article.setEditTime(new Timestamp(System.currentTimeMillis()));
            int i = articleMapper.updateArticle(article);
            //修改标签
            String[] dynamicTags = article.getDynamicTags();
            if (dynamicTags != null && dynamicTags.length > 0) {
                int tags = addTagsToArticle(dynamicTags, article.getId());
                if (tags == -1) {
                    return tags;
                }
            }
            return i;
        }
    }
   public List<Tags> getTagsByAid( Long aid){
        return tagsMapper.getTagsByAid(aid);
    }
   // @Cacheable(value="allarticle",key="'allarticle_'+#cid",unless = "#cid == null")
    public List<Article> getArticlesByCid(Long cid) {
        return articleMapper.getArticlesByCid(cid);
    }

    public int getUserArticleSum(Long uid) {
        return articleMapper.getUserArticleSum(uid);
    }

    public String getUserRepeatRate(Long uid) {
        int articleSum = articleMapper.getUserArticleSum(uid);
        double userRepeatRate = articleMapper.getUserRepeatRate(uid);
        double res = 0;
        if (articleSum != 0) {
            res = userRepeatRate / articleSum;
        }
        DecimalFormat df = new DecimalFormat("0.00");
        String res1 = df.format(res);
        return res1;
    }
   // @Cacheable(value="allarticle",key="'allarticle_'+#condition",unless = "#condition == null")
    public List<Article> searchArticles(String condition) {
        SearchCondition searchConditionByCondition = searchConditionMapper.getSearchConditionByCondition(condition);
        if (searchConditionByCondition == null) {
            searchConditionMapper.addNewSearchCondition(condition);
        } else {
            searchConditionMapper.updateSearchCondition(condition);
        }
        List<Article> articles = articleMapper.searchArticle("%" + condition + "%");
        return articles;
    }
   // @Cacheable(value="allarticle",key="'allarticle_o_'+#startPage",unless = "#startPage == null")
    public List<Article> getAllArticlesOrderByPublishDate(int startPage) {
        return articleMapper.getAllArticlesOrderByPublishDate(startPage);
    }
   // @Cacheable(value="allarticle",key="'allarticle_o_'+#startPage",unless = "#startPage == null")
    public List<Article> getAllArticlesOrderByComments(int startPage) {
        return articleMapper.getAllArticlesOrderByComments(startPage);
    }
    //@Cacheable(value="allarticle",key="'allarticle'")
    public List<Article> getAllArticlesOrderByComments2() {
        return articleMapper.getAllArticlesOrderByComments2();
    }
    //@Cacheable(value="allarticle",key="'allarticle_o_'+#startPage",unless = "#startPage == null")
    public List<Article> getAllArticlesOrderByRates(int startPage) {
        return articleMapper.getAllArticlesOrderByRates(startPage);
    }
    //@Cacheable(value="allarticle",key="'allarticle'")
    public List<Article> getAllArticlesOrderByRate2() {
        return articleMapper.getAllArticlesOrderByRates2();
    }
   // @Cacheable(value="allarticle",key="'allarticle_search_'+#condition",unless = "#condition == null")
    public List<Article> searchArticlesOrderByPublishDate(String condition) {
        return articleMapper.searchArticlesOrderByPublishDate("%" + condition + "%");
    }
   //@Cacheable(value="allarticle",key="'allarticle_search_'+#condition",unless = "#condition == null")
    public List<Article> searchArticlesOrderByComments(String condition) {
        return articleMapper.searchArticlesOrderByComments("%" + condition + "%");
    }
   // @Cacheable(value="allarticle",key="'allarticle_search_'+#condition",unless = "#condition == null")
    public List<Article> searchArticlesOrderByRates(String condition) {
        return articleMapper.searchArticlesOrderByRates("%" + condition + "%");
    }

    private int addTagsToArticle(String[] dynamicTags, Long aid) {
        //1.删除该文章目前所有的标签
        tagsMapper.deleteTagsByAid(aid);
        //2.将上传上来的标签全部存入数据库
        tagsMapper.saveTags(dynamicTags);
        //3.查询这些标签的id
        List<Long> tIds = tagsMapper.getTagsIdByTagName(dynamicTags);
        //4.重新给文章设置标签
        int i = tagsMapper.saveTags2ArticleTags(tIds, aid);
        return i == dynamicTags.length ? i : -1;
    }

    public String stripHtml(String content) {
        content = content.replaceAll("<p .*?>", "");
        content = content.replaceAll("<br\\s*/?>", "");
        content = content.replaceAll("\\<.*?>", "");
        return content;
    }
    //@Cacheable(value="allarticle",key="'allarticle'")
    public List<Article> getArticleByState(Integer state, Integer page, Integer count, String keywords) {
        int start = (page - 1) * count;
        Long uid = UserUtil.getCurrentUser().getId();
        return articleMapper.getArticleByState(state, start, count, uid, keywords);
    }

//    public List<Article> getArticleByStateByAdmin(Integer page, Integer count,String keywords) {
//        int start = (page - 1) * count;
//        return articleMapper.getArticleByStateByAdmin(start, count,keywords);
//    }

    public int getArticleCountByState(Integer state, Long uid, String keywords) {
        return articleMapper.getArticleCountByState(state, uid, keywords);
    }

    public int updateArticleState(Long[] aids, Integer state) {
        if (state == 2) {
            return articleMapper.deleteArticleById(aids);
        } else {
            return articleMapper.updateArticleState(aids, 2);//放入到回收站中
        }
    }
    @CachePut(value = "article",key = "'article_'+#articleId")
    public int restoreArticle(Integer articleId) {
        return articleMapper.updateArticleStateById(articleId, 1); // 从回收站还原在原处
    }
    @Cacheable(value = "article",key = "'article_'+#aid")
    public Article getArticleById(Long aid) {
        Article article = articleMapper.getArticleById(aid);
        articleMapper.pvIncrement(aid);
        return article;
    }

    public void pvStatisticsPerDay() {
        articleMapper.pvStatisticsPerDay();
    }

    /**
     * 获取最近七天的日期
     *
     * @return
     */
    public List<String> getCategories() {
        return articleMapper.getCategories(UserUtil.getCurrentUser().getId());
    }

    /**
     * 获取最近七天的数据
     *
     * @return
     */
    public List<Integer> getDataStatistics() {
        return articleMapper.getDataStatistics(UserUtil.getCurrentUser().getId());
    }
}
