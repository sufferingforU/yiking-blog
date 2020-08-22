package com.yiking.blog.mapper;

import com.yiking.blog.entities.Category;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;


import java.util.List;

@Mapper
public interface CategoryMapper {
    List<Category> getAllCategories();

    List<Category> getCategories();

    int deleteCategoryByIds(@Param("ids") String[] ids);

    int updateCategoryById(Category category);

    int addCategory(Category category);
}
