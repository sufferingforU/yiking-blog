package com.yiking.blog.controller;

import com.yiking.blog.entities.Rate;
import com.yiking.blog.entities.RespBean;
import com.yiking.blog.entities.User;
import com.yiking.blog.service.RateService;
import com.yiking.blog.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/rate")
@Api(tags = "评分管理")
public class RateController {
    @Autowired
    RateService rateService;
  @Autowired
    UserService userService;
    @PostMapping("/addrate")
    @ApiOperation(value = "新增评分")
    public RespBean addNewRate(Rate rate) {
        User user = userService.getUserByName(rate.getUsername());
        rate.setUid(user.getId());
        int result = rateService.addNewRate(rate);
        if (result == 1) {
            return new RespBean("success", rate.getId() + "");
        } else {
            return new RespBean("error", "评分失败!");
        }
    }

    @PostMapping("/myrate")
    @ApiOperation(value = "获取博文评分",notes = "获取当前博文的平均评分和用户自己的评分")
    public Map getMyRate( Rate rate1) {
        User user = userService.getUserByName(rate1.getUsername());
        Rate rate = rateService.getRateByAidAndUid(rate1.getAid(),user.getId());
        double rates = rateService.getRates(rate1.getAid());
        HashMap<Object, Object> map = new HashMap<>();
        if (rate != null) {
            map.put("rate", rate.getRate());

        } else {
            map.put("rate", 0);
        }
        map.put("rates", rates);
        return map;
    }
}
