package com.yiking.blog.mapper;

import com.yiking.blog.entities.SearchCondition;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface SearchConditionMapper {
    SearchCondition getSearchConditionByCondition(@Param("searchCondition")String searchCondition);
    int addNewSearchCondition(@Param("searchCondition")String searchCondition);
    int updateSearchCondition(@Param("searchCondition")String searchCondition);
    List<SearchCondition> getAllSearchCondition();
}
