package com.lvr.ihave.business.service.impl;

import com.lvr.ihave.business.mapper.UserMapper;
import com.lvr.ihave.business.service.UserService;
import com.lvr.ihave.constant.Constant;
import com.lvr.ihave.ex.PhoneNotFoundException;
import com.lvr.ihave.ex.UsernameTakenException;
import com.lvr.ihave.pojo.SysUser;
import org.springframework.stereotype.Service;

import jakarta.annotation.Resource;

import java.io.Console;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Random;

@Service("userService")
public class UserServiceImpl implements UserService {

    @Resource
    private UserMapper userMapper;


    public int deleteByPrimaryKey(String id) {
        return userMapper.deleteByPrimaryKey(id);
    }

    public int insert(SysUser record) {
        SysUser user = userMapper.getUserByPhone(record.getPhone());
        if(user == null){
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            // 生成六位随机数字ID
            Random random = new Random();
            String userId = String.format("%06d", random.nextInt(1000000));
            record.setUserId(userId);
            record.setCreateAt(sdf.format(new Date()));
            return userMapper.insert(record);
        }else{
            throw new UsernameTakenException(Constant.PHONE_SINED);
        }
    }

    public SysUser selectByPrimaryKey(String id) {
        return userMapper.selectByPrimaryKey(id);
    }

    public List<SysUser> selectAll() {
        return userMapper.selectAll();
    }

    public int updateByPrimaryKey(SysUser record) {
        return userMapper.updateByPrimaryKey(record);
    }

    public int updateLastLoginByPrimaryKey(SysUser record) {
        return userMapper.updateLastLoginByPrimaryKey(record);
    }

    public int updateByPrimaryKeySelective(SysUser record) {
        return userMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public SysUser selectUserByUserName(String userName) {
        return userMapper.selectUserByUserName(userName);
    }

    public SysUser getUserByPhone(String phone) {
        SysUser user = userMapper.getUserByPhone(phone);
        if(user == null){
            throw new PhoneNotFoundException("手机号未注册");
        }else{
            return user;
        }
    }

    public List<SysUser> getUserList() {
        return userMapper.getUserList();
    }

    public int updateImgUrl(String id, String url) {
        return userMapper.updateImgUrl(id,url);
    }

    @Override
    public Integer checkUserByPhone(String phone) {
        return userMapper.checkUserByPhone(phone);
    }

    @Override
    public int updatePasswordByPrimaryKey(Integer uid, String password) {
        return userMapper.updatePasswordByPrimaryKey(uid,password);
    }

    @Override
    public int updateStatusByPrimaryKey(String id, Integer status) {
        return userMapper.updateStatusByPrimaryKey(id,status);
    }

    @Override
    public List<SysUser> searchUserByNameOrPhone(String name, String phone) {
        return userMapper.searchUserByNameOrPhone(name,phone);
    }

}