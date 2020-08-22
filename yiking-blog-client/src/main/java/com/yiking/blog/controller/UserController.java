package com.yiking.blog.controller;

import com.yiking.blog.entities.Category;
import com.yiking.blog.entities.RespBean;
import com.yiking.blog.entities.User;
import com.yiking.blog.service.CategoryService;
import com.yiking.blog.service.UserService;
import com.yiking.blog.utils.UserUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.*;

//@CrossOrigin
@RestController
@Api(tags = "用户管理")
public class UserController {
    @Autowired
    UserService userService;
    @Autowired
    CategoryService categoryService;
    @ApiOperation(value = "查询博文类别",notes = "查询博文类别按其优先级返回5个")
    @GetMapping("/getcategories")
    public List<Category> getCategories(){
        List<Category> categories = categoryService.getCategories();
        return categories;
    }

    @GetMapping("/login_page")
    @ApiOperation(value = "提示用户登录")
    public RespBean loginPage() {
        return new RespBean("error", "尚未登录，请登录!");
    }

    @GetMapping("/currentUserName")
    @ApiOperation(value = "获取当前用户名")
    public String currentUserName() {

        return UserUtil.getCurrentUser().getUsername();
    }
    @PostMapping("/updatebackground")
    @ApiOperation(value = "更新用户背景")
    public RespBean updateBackground(String username,String background){
        int res = userService.updateUserBg(username, background);
        if (res>0){
            return new RespBean("success", "上传成功");
        }
        return new RespBean("error", "上传失败!");
    }
    @PostMapping("/updateheadimg")
    @ApiOperation(value = "更新用户头像")
    public RespBean updateHeadImg(MultipartFile image,String username){
        File imgFolder2 = new File("D:\\YIKING-BLOG\\pictures");
        if (!imgFolder2.exists()) {
            imgFolder2.mkdirs();
        }
        String imgName = UUID.randomUUID() + "_" + image.getOriginalFilename().replaceAll(" ", "");
        try {
            IOUtils.write(image.getBytes(), new FileOutputStream(new File(imgFolder2, imgName)));
            String myurl="http://localhost:8003/image/"+imgName;
            userService.updateUserFace(myurl,username);
            return new RespBean("success", myurl);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new RespBean("error", "上传失败!");
    }
    @PostMapping("/reguser")
    @ApiOperation(value = "注册用户")
    public RespBean regUser(@Validated User user, BindingResult binder) {
       List<String> errors = new ArrayList<>();
        List<FieldError> list = binder.getFieldErrors();
        boolean flag = binder.hasErrors();
        if (flag) {
            for (FieldError ss : list) {
                errors.add(ss.getDefaultMessage());
                //System.out.println(ss.getDefaultMessage());
            }
        }else{
            int reg = userService.reg(user);
            if (reg == 0) {
                return new RespBean("success", user.getUsername() + "注册成功");
            } else if (reg == 1) {
                return new RespBean("error", "用户名重复！");
            }
        }
            return new RespBean("error", errors);
    }

}
