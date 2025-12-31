package com.lvr.ihave.business.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lvr.ihave.business.mapper.CategoryMapper;
import com.lvr.ihave.business.service.CategoryService;
import com.lvr.ihave.business.utils.PageRequest;
import com.lvr.ihave.business.utils.PageResult;
import com.lvr.ihave.business.utils.PageUtils;
import com.lvr.ihave.pojo.Catelog;
import org.springframework.stereotype.Service;

import jakarta.annotation.Resource;
import java.util.HashMap;
import java.util.List;

@Service("categoryService")
public class CategoryServiceImpl implements CategoryService {

    @Resource
    private CategoryMapper catelogMapper;

    public int deleteByPrimaryKey(Integer id) {
        return catelogMapper.deleteByPrimaryKey(id);
    }

    public int insert(Catelog record) {
        return catelogMapper.insert(record);
    }

    public Catelog selectByPrimaryKey(Integer id) {
        return catelogMapper.selectByPrimaryKey(id);
    }

    public List<Catelog> selectAll() {
        return catelogMapper.selectAll();
    }

    @Override
    public PageResult findPage(String keyword, PageRequest pageRequest) {
        return PageUtils.getPageResult(pageRequest, getPageInfo(keyword, pageRequest));
    }

    private PageInfo<Catelog> getPageInfo(String keyword, PageRequest pageRequest) {
        int pageNum = pageRequest.getPageNum();
        int pageSize = pageRequest.getPageSize();
        PageHelper.startPage(pageNum, pageSize);
        List<Catelog> categoryList = catelogMapper.searchCatelogByName(keyword);
        return new PageInfo<Catelog>(categoryList);
    }

    public int updateByPrimaryKey(Catelog record) {
        return catelogMapper.updateByPrimaryKey(record);
    }

    public List<Catelog> getAllCatelogByStatus(Byte status) {
        return catelogMapper.getAllCatelogByStatus(status);
    }

    @Override
    public HashMap<Integer, String> getAllCatelogWithOptions() {
        List<Catelog> catelogs = catelogMapper.getAllCatelogByStatus((byte)1);
        HashMap<Integer,String> categoryOptions = new HashMap<>();
        for (int i=0;i<catelogs.size();i++) {
            categoryOptions.put(catelogs.get(i).getId(),catelogs.get(i).getName());
        }
        return categoryOptions;
    }

    @Override
    public List<Catelog> searchCatelogByName(String name) {
        return catelogMapper.searchCatelogByName(name);
    }

    @Override
    public void updateStatus(Integer id, Byte status) {
        catelogMapper.updateStatus(id, status);
    }
}