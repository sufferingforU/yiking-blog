package com.yiking.blog.mapper;

import com.yiking.blog.entities.Rate;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface RateMapper {
    int addNewRate(Rate rate);
    Rate getRateByAidAndUid(@Param("aid")Long aid,@Param("uid")Long uid);
    int updateRateByAidAndUid(Rate rate);
    double getRates(@Param("aid")Long aid);
    int getUserRate(@Param("uid") Long uid);
}
