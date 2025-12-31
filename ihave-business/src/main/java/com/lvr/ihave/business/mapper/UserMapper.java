package com.lvr.ihave.business.mapper;

import com.lvr.ihave.pojo.SysUser;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserMapper {

    int deleteByPrimaryKey(String id);

    int insert(SysUser record);

    SysUser selectByPrimaryKey(String userId);

    List<SysUser> selectAll();

    int updateByPrimaryKey(SysUser record);

    int updateLastLoginByPrimaryKey(SysUser record);

    int updateByPrimaryKeySelective(SysUser record);

    int updateStatusByPrimaryKey(@Param("userId") String userId, @Param("status") Integer status);

    SysUser getUserByPhone(@Param("phone") String phone);

    List<SysUser> getUserList();

    int updateImgUrl(@Param("id") String id, @Param("url") String url);

    Integer checkUserByPhone(@Param("phone") String phone);

    int updatePasswordByPrimaryKey(@Param("uid") Integer uid, @Param("password") String password);

    List<SysUser> searchUserByNameOrPhone(@Param("name") String name, @Param("phone") String phone);

    SysUser selectUserByUserName(@Param("userName") String userName);
}