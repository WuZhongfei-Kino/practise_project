package com.wzf.service.impl;

import com.wzf.mapper.CategoryMapper;
import com.wzf.pojo.Category;
import com.wzf.service.CategoryService;
import com.wzf.utils.ThreadLocalUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryMapper categoryMapper;

    @Override
    public void add(Category category) {
        //补充属性值
        category.setCreateTime(LocalDateTime.now());
        category.setUpdateTime(LocalDateTime.now());
        System.out.println("创建时间:"+category.getCreateTime());
        Map<String, Object> map = ThreadLocalUtil.get();
        Integer userId = (Integer) map.get("id");
        category.setCreateUser(userId);
        categoryMapper.add(category);
    }

    @Override
    public List<Category> list() {
        Map<String, Object> map = ThreadLocalUtil.get();
        Integer id = (Integer) map.get("id");
        //传入当前用户id
        return categoryMapper.list(id);
    }

    @Override
    public Category findById(Integer id) {
        Category category= categoryMapper.findById(id);
        return category;
    }

    @Override
    public void update(Category category) {
        //补齐更新日期
        category.setUpdateTime(LocalDateTime.now());
        categoryMapper.update(category);
    }

    @Override
    public void delete(Integer id) {
        categoryMapper.delete(id);
    }
}
