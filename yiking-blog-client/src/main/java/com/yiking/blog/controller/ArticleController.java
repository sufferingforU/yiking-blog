package com.yiking.blog.controller;

import com.yiking.blog.entities.*;
import com.yiking.blog.mapper.SearchConditionMapper;
import com.yiking.blog.service.ArticleService;
import com.yiking.blog.service.CommentsService;
import com.yiking.blog.service.RateService;
import com.yiking.blog.service.UserService;
import com.yiking.blog.utils.RepeatWordFilter;
import com.yiking.blog.utils.ResultHelper;
import com.yiking.blog.utils.StoreDateToFile;
import com.yiking.blog.utils.UserUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by  yiking 2020/07/03
 */
@Api(tags = "博文管理")
@RestController
@RequestMapping("/article")
public class ArticleController {

    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    ResultHelper resultHelper=new ResultHelper();
    @Autowired
    ArticleService articleService;
    @Autowired
    UserService userService;
    @Autowired
    SearchConditionMapper searchConditionMapper;
    @Autowired
    RateService rateService;
    @Autowired
    CommentsService commentsService;

    //获取所有文章
    @ApiOperation(value = "获取博文列表", notes = "根据起始页码获取博文列表")
    @GetMapping("/allarticles/{startPage}")
    public Map<String, Object> getAllArticles(@PathVariable int startPage) {
//        StoreDateToFile dateToFile = new StoreDateToFile();
//        List<Article> allArticles2 = articleService.getAllArticles2();
//        for (Article a : allArticles2) {
//            String mdContent = a.getMdContent();
//                dateToFile.transferDatetoFile(mdContent,a.getTitle()+"-"+userService.getUserById(a.getUid()).getUsername()+"-"+a.getId());
//
//        }
        HashMap<String, Object> map = new HashMap<>();
        List<Article> articles = articleService.getAllArticles(startPage);
       List<Object> res = resultHelper.getResultListArticle(articles,userService,sdf);
        int count = articleService.getArticleCount();
        map.put("articles", res);
        map.put("totalSize", count);
        return map;
    }

    @PostMapping("/checkrepeat")
    @ApiOperation(value = "检查文章重复率", notes = "根据mdContent查询是否和博文库中的博文重复，若有重复则返回重复率和与之重复的具体文章（可以是多篇文章）")
    public Map<String, Object> checkRepeatWords(String mdContent) {
        HashMap<String, Object> map = new HashMap<>();
        RepeatWordFilter instance = new RepeatWordFilter();
        Map<String, Object> stringStringMap = instance.showRepeatWords(mdContent);
        map.put("repeatwords", stringStringMap);
        return map;
    }

    //获取所有文章
    @GetMapping("/articlesorderbypublishdate/{startPage}")
    @ApiOperation(value = "根据发表日期获取博文列表")
    public Map<String, Object> getAllArticlesOrderByPublishDate(@PathVariable int startPage) {
        HashMap<String, Object> map = new HashMap<>();
        List<Article> articles = articleService.getAllArticlesOrderByPublishDate(startPage);
        List<Object> res = resultHelper.getResultListArticle(articles,userService,sdf);
        int count = articleService.getArticleCount();
        map.put("articles", res);
        map.put("totalSize", count);
        return map;
    }
    @ApiOperation(value = "根据评论数量获取博文列表")
    @GetMapping("/articlesorderbycomments/{startPage}")
    public Map<String, Object> getAllArticlesOrderByComments(@PathVariable int startPage) {
        {

            HashMap<String, Object> map = new HashMap<>();
            List<Article> articles = articleService.getAllArticlesOrderByComments(startPage);
            List<Article> resCount = articleService.getAllArticlesOrderByComments2();
            List<Object> res  = resultHelper.getResultListArticle(articles,userService,sdf);
            int count = resCount.size();
            map.put("articles", res);
            map.put("totalSize", count);
            return map;
        }
    }
    @ApiOperation(value = "根据博文评分获取博文列表")
    @GetMapping("/articlesorderbyrates/{startPage}")
    public Map<String, Object> getAllArticlesOrderByRates(@PathVariable int startPage) {
        {

            HashMap<String, Object> map = new HashMap<>();
            List<Article> articles = articleService.getAllArticlesOrderByRates(startPage);
            List<Article> resCount = articleService.getAllArticlesOrderByRate2();
            List<Object> res =resultHelper.getResultListArticle(articles,userService,sdf);
            int count = resCount.size();
            map.put("articles", res);
            map.put("totalSize", count);
            return map;
        }
    }

    @GetMapping("/searcharticlesorderbyrates/{condition}")
    @ApiOperation(value = "将查询到的博文列表按评分降序返回")
    public Map<String, Object> searchArticlesOrderByRates(@PathVariable String condition) {
        {
            HashMap<String, Object> map = new HashMap<>();
            List<Article> articles = articleService.searchArticlesOrderByRates(condition);
            List<Article> resCount = articleService.getAllArticlesOrderByRate2();
            List<Object> res = resultHelper.getResultListArticle(articles,userService,sdf);
            int count = resCount.size();
            map.put("articles", res);
            map.put("totalSize", count);
            return map;
        }
    }
    @ApiOperation(value = "将查询到的博文列表按发表日期返回")
    @GetMapping("/searcharticlesorderbypublishdate/{condition}")
    public Map<String, Object> searchArticlesOrderByPublishDate(@PathVariable String condition) {
        {
            //System.out.println(condition);
            //System.out.println("yiking");
            HashMap<String, Object> map = new HashMap<>();
            List<Article> articles = articleService.searchArticlesOrderByPublishDate(condition);
            //System.out.println(articles);
            List<Article> resCount = articleService.getAllArticlesOrderByRate2();
            List<Object> res  = resultHelper.getResultListArticle(articles,userService,sdf);
            int count = resCount.size();
            //System.out.println(res);
            map.put("articles", res);
            map.put("totalSize", count);
            return map;
        }
    }

    @GetMapping("/searcharticlesorderbycomments/{condition}")
    @ApiOperation(value = "将查询到的博文列表按评论数量返回")
    public Map<String, Object> searchArticlesOrderByComments(@PathVariable String condition) {
        {
            HashMap<String, Object> map = new HashMap<>();
            List<Article> articles = articleService.searchArticlesOrderByComments(condition);
            List<Article> resCount = articleService.getAllArticlesOrderByRate2();
            List<Object> res = resultHelper.getResultListArticle(articles,userService,sdf);
            int count = resCount.size();
            map.put("articles", res);
            map.put("totalSize", count);
            return map;
        }
    }
    @ApiOperation(value = "查询指定类别博文",notes = "按类别id查询指定类别的博文")
    @GetMapping("/getcatory/{cid}")
    public Map<String, Object> getArticlesByCid(@PathVariable Long cid) {
        {
            HashMap<String, Object> map = new HashMap<>();
            List<Article> articles = articleService.getArticlesByCid(cid);

            List<Object> res= resultHelper.getResultListArticle(articles,userService,sdf);
            map.put("articles", res);
            return map;
        }
    }
    @ApiOperation(value = "查询用户自己的博文列表")
    @GetMapping("/myarticles/{username}")
    public Map<String, Object> getMyArticles(@PathVariable String username) {
        User user = userService.getUserByName(username);
        HashMap<String, Object> map = new HashMap<>();
        List<Article> articles = articleService.getMyArticles(user.getId());
        List<Object> res = new ArrayList<>();
        for (int i=0;i<articles.size();i++) {

            Article a = articles.get(i);
            HashMap<String, Object> content = new HashMap<>();
            content.put("title", a.getTitle());
            content.put("id", a.getId());

            // System.out.println(user.getUsername());
            content.put("author", username);
            content.put("pageview",a.getPageView());
            String format = sdf.format(a.getPublishDate());
            content.put("publishDate", format);
            content.put("summary", a.getSummary());
            res.add(content);
            content = null;
            user = null;
        }
        map.put("articles", res);
        return map;
    }

    @PostMapping(value = "/")
    @ApiOperation(value = "发表博文",notes = "发表博文，并将博文去重后加入博文库")
    public RespBean addNewArticle(Article article) {
        HashMap<String, Object> map = new HashMap<>();
        RepeatWordFilter instance = new RepeatWordFilter();
        Map<String, Object> stringStringMap = instance.showRepeatWords(article.getMdContent());
        double allrepeats = (double) stringStringMap.get("allrepeats");
        article.setRepeatRate(allrepeats);
        int result = articleService.addNewArticle(article);

        if (result == 1) {
            StoreDateToFile dateToFile = new StoreDateToFile();
            List<Article> allArticles2 = articleService.getAllArticles2();
            Article article1 = new Article();
            for (Article a : allArticles2) {
                if (a.getTitle().equals(article.getTitle()) && a.getSummary().equals(article.getSummary())) {
                    article1 = a;
                }
            }
            dateToFile.transferDatetoFile(article.getMdContent(), article1.getTitle() + "-"
                    + userService.getUserById(article1.getUid()).getUsername() + "-" + article1.getId());
            return new RespBean("success", article.getId() + "");
        } else {
            return new RespBean("error", article.getState() == 0 ? "文章保存失败!" : "文章发表失败!");
        }
    }

    @GetMapping("/getsearchconditionsum")
    @ApiOperation(value = "查询热搜词",notes = "查询当前20条搜索频率最高的热搜词极其搜索次数")
    public List getSearchConditionSum() {
        List<Map> list = new ArrayList();
        List<SearchCondition> allSearchCondition = searchConditionMapper.getAllSearchCondition();
        for (SearchCondition s : allSearchCondition) {
            HashMap<String, Object> map = new HashMap<>();
            map.put("name", s.getSearchCondition());
            map.put("value", s.getFrequency());
            list.add(map);
        }
        return list;
    }
    @ApiOperation(value = "查询用户个人信息",notes = "查询用户的头像、背景图片、博文数、评论数、博文整体重复度、未读评论、未读回复、并将有未读评论及回复的文章列表返回")
    @GetMapping("/getuserdata/{username}")
    public Map getUserData(@PathVariable String username) {

        User user = userService.getUserByName(username);
        Long id = user.getId();
        List<Comments> commentsByReplyName = commentsService.getCommentsByReplyName(username, (long) 2);
        List<Comments> comments = commentsService.getCommentsByParentId(id, (long) 1);
        Set<Long> aids=new HashSet<>();
        Set<Long> aids2=new HashSet<>();
        Map temp=new HashMap();
        for (Comments c:comments){
            Long aid = c.getAid();
           aids.add(aid);
        }
        List<Map>res=new ArrayList<>();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        for (Long i:aids){
            Article a = articleService.getArticleById(i);
            HashMap<String, Object> content = new HashMap<>();
            content.put("title", a.getTitle());
            content.put("id", a.getId());

            // System.out.println(user.getUsername());
            content.put("author", username);
            String format = dateFormat.format(a.getPublishDate());
            content.put("publishDate", format);
            content.put("summary", a.getSummary());
            res.add(content);
            content = null;
           a=null;
        }
        for (Comments c:commentsByReplyName){
            Long aid = c.getAid();
            aids2.add(aid);
        }
        List<Map>res2=new ArrayList<>();

        for (Long i:aids2){
            Article a = articleService.getArticleById(i);
            HashMap<String, Object> content = new HashMap<>();
            content.put("title", a.getTitle());
            content.put("id", a.getId());
            // System.out.println(user.getUsername());
            User userById = userService.getUserById(a.getUid());
            // System.out.println(user.getUsername());
            content.put("author", userById.getUsername());
            String format = dateFormat.format(a.getPublishDate());
            content.put("publishDate", format);
            content.put("pageview",a.getPageView());
            content.put("summary", a.getSummary());
            res2.add(content);
            content = null;
            a=null;
        }
        Map<String,Object> map=new HashMap<>();
        map.put("headerimage",user.getHeadImg());

        map.put("userbackground",user.getBackground());
        map.put("articlesum",articleService.getUserArticleSum(id));
        map.put("articlesrate",rateService.getUserRates(id));
        map.put("userrepeatrate",articleService.getUserRepeatRate(id));
        map.put("readingcommentsum",comments.size());
        map.put("replycommentsum",commentsByReplyName.size());
        map.put("commentsarticles",res);
        map.put("commentsarticles2",res2);
        return map;
    }
    @GetMapping("/getauthordata/{username}")
    @ApiOperation(value = "查询用户个人信息",notes = "查询用户的头像、博文数、评论数、博文整体重复度")
    public Map getAuthorData(@PathVariable String username) {
        User user = userService.getUserByName(username);
        Long id = user.getId();
        Map<String,Object> map=new HashMap<>();
        map.put("articlesum",articleService.getUserArticleSum(id));
        map.put("articlesrate",rateService.getUserRates(id));
        map.put("userrepeatrate",articleService.getUserRepeatRate(id));
        return map;
    }
    @GetMapping("/search/{condition}")
    @ApiOperation(value = "查询博文",notes = "按查询条件查询博文title、summary里有查询条件的博文")
    public Map<String, Object> searchArticles(@PathVariable String condition) {
        HashMap<String, Object> map = new HashMap<>();
        List<Article> articles = articleService.searchArticles(condition);
        List<Object> res  = resultHelper.getResultListArticle(articles,userService,sdf);
        int count = articles.size();
        map.put("articles", res);
        // map.put("totalSize",count);
        return map;
    }

    /**
     * 上传图片
     *
     * @return 返回值为图片的地址
     */
    @PostMapping(value = "/uploadimg")
    @ApiOperation(value = "上传图片",notes = "在编写博文时上传博文中的图片")
    public RespBean uploadImg(HttpServletRequest req, MultipartFile image) {
        StringBuffer url = new StringBuffer();
        String filePath = "/blogimg/" + sdf.format(new Date());
        String imgFolderPath = req.getServletContext().getRealPath(filePath);
        //String imgFolderPath="D:\\YIKING-BLOG\\images";

        File imgFolder = new File(imgFolderPath);
        if (!imgFolder.exists()) {
            imgFolder.mkdirs();
        }
        File imgFolder2 = new File("D:\\YIKING-BLOG\\pictures");
        if (!imgFolder2.exists()) {
            imgFolder2.mkdirs();
        }
        url.append(req.getScheme())
                .append("://")
                .append(req.getServerName())
                .append(":")
                .append(req.getServerPort())
                .append(req.getContextPath())
                .append(filePath);
        String imgName = UUID.randomUUID() + "_" + image.getOriginalFilename().replaceAll(" ", "");
        try {
            IOUtils.write(image.getBytes(), new FileOutputStream(new File(imgFolder, imgName)));
            IOUtils.write(image.getBytes(), new FileOutputStream(new File(imgFolder2, imgName)));
            url.append("/").append(imgName);
            String myurl="http://localhost:8003/image/"+imgName;
            return new RespBean("success", myurl);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new RespBean("error", "上传失败!");
    }

    @GetMapping(value = "/all")
    @ApiOperation(value = "查询所有文章")
    public Map<String, Object> getArticleByState(@RequestParam(value = "state", defaultValue = "-1") Integer state, @RequestParam(value = "page", defaultValue = "1") Integer page, @RequestParam(value = "count", defaultValue = "6") Integer count, String keywords) {
        int totalCount = articleService.getArticleCountByState(state, UserUtil.getCurrentUser().getId(), keywords);
        List<Article> articles = articleService.getArticleByState(state, page, count, keywords);
        Map<String, Object> map = new HashMap<>();
        map.put("totalCount", totalCount);
        map.put("articles", articles);
        return map;
    }

    @GetMapping(value = "currarticle/{aid}")
    @ApiOperation(value = "查询博文",notes = "按照文章id查询博文并返回")
    public Article getArticleById(@PathVariable Long aid) {
        Article article = articleService.getArticleById(aid);
        article.setTags(articleService.getTagsByAid(aid));
        return article;
    }

    @PutMapping(value = "/dustbin")
    @ApiOperation(value = "删除博文")
    public RespBean updateArticleState(Long[] aids, Integer state) {
        if (articleService.updateArticleState(aids, state) == aids.length) {
            return new RespBean("success", "删除成功!");
        }
        return new RespBean("error", "删除失败!");
    }

    @ApiOperation(value = "还原博文",notes = "将删除的博文还原")
    @PutMapping(value = "/restore")
    public RespBean restoreArticle(Integer articleId) {
        if (articleService.restoreArticle(articleId) == 1) {
            return new RespBean("success", "还原成功!");
        }
        return new RespBean("error", "还原失败!");
    }

    @GetMapping("/dataStatistics")
    @ApiOperation(value = "博文浏览量统计")
    public Map<String, Object> dataStatistics() {
        Map<String, Object> map = new HashMap<>();
        List<String> categories = articleService.getCategories();
        List<Integer> dataStatistics = articleService.getDataStatistics();
        map.put("categories", categories);
        map.put("ds", dataStatistics);
        return map;
    }


}
