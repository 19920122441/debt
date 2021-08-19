package com.zhichangan.debt.user.mapper;

import com.zhichangan.debt.user.entity.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserMapper {
    int deleteByPrimaryKey(String id);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);

    User login(@Param("userName") String userName,@Param("password") String password);


    List<User> queryUserByDeptId(String deptId);

    List<User> queryAllUserIdAndNameNotPublicUser();

    List<User> queryUserByDept(String deptName);

    List<User> pageQuery(Integer missNumber, Integer pageSize);

    List<User> query(User user);

    List<User> queryAllUser();

    User queryUserByJobNumber(String jobNumber);
}