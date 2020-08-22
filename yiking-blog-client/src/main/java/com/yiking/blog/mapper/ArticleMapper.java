package com.yiking.blog.mapper;

import com.yiking.blog.entities.Article;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;


import java.util.List;


@Mapper
public interface ArticleMapper {

    int addNewArticle(Article article);

    int getArticleCount();

    List<Article> searchArticle(@Param("condition")String condition);

    int updateArticle(Article article);

    List<Article> getAllArticles(@Param("startPage")int startPage);

    List<Article> getAllArticlesOrderByPublishDate(@Param("startPage")int startPage);

    List<Article> getAllArticlesOrderByComments(@Param("startPage")int startPage);

    List<Article> getAllArticlesOrderByRates(@Param("startPage")int startPage);

    List<Article> searchArticlesOrderByPublishDate(@Param("condition")String condition);

    List<Article> searchArticlesOrderByComments(@Param("condition")String condition);



    List<Article> searchArticlesOrderByRates(@Param("condition")String condition);

    List<Article> getAllArticlesOrderByRates2();

    List<Article> getArticlesByCid(@Param("cid")Long cid);

    List<Article> getAllArticlesOrderByComments2();

    List<Article> getAllArticles2();

    List<Article> getMyArticles(@Param("uid")Long uid);

    List<Article> getArticleByState(@Param("state") Integer state, @Param("start") Integer start, @Param("count") Integer count, @Param("uid") Long uid, @Param("keywords") String keywords);

//    List<Article> getArticleByStateByAdmin(@Param("start") int start, @Param("count") Integer count, @Param("keywords") String keywords);

    int getArticleCountByState(@Param("state") Integer state, @Param("uid") Long uid, @Param("keywords") String keywords);

    int updateArticleState(@Param("aids") Long aids[], @Param("state") Integer state);

    int updateArticleStateById(@Param("articleId") Integer articleId, @Param("state") Integer state);

    int deleteArticleById(@Param("aids") Long[] aids);

    int getUserArticleSum(@Param("uid")Long uid);

    Article getArticleById(Long aid);

    void pvIncrement(Long aid);

    //INSERT INTO pv(countDate,pv,uid) SELECT NOW(),SUM(pageView),uid FROM article GROUP BY uid
    void pvStatisticsPerDay();

    List<String> getCategories(@Param("uid")Long uid);

    double getUserRepeatRate(@Param("uid") Long uid);

    List<Integer> getDataStatistics(Long uid);
}
