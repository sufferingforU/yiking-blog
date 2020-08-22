package com.yiking.blog.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;

@Mapper
public interface RealContentMapper {
    int addRealContent(@Param("realContent")String realContent,@Param("publishDate") Date publishDate,@Param("uid")Long uid);
}
