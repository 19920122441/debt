package com.zhichangan.debt.userMailRelationship.mapper;

import com.zhichangan.debt.userMailRelationship.entity.UserMailRelationship;

import java.util.List;

public interface UserMailRelationshipMapper {
    int deleteByPrimaryKey(String id);

    int insert(UserMailRelationship record);

    int insertSelective(UserMailRelationship record);

    UserMailRelationship selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(UserMailRelationship record);

    int updateByPrimaryKey(UserMailRelationship record);

    List<UserMailRelationship> queryRelationshipByMailId(String mailId);

    List<UserMailRelationship> queryRelationshipByUserId(String id);

    List<UserMailRelationship> pageQueryRelationshipByUserId(Integer number,Integer pageSize,String id);

    int deleteByUserIdAndMailId(String mailId, String id);

    UserMailRelationship queryRelationshipByMailIdAndUserId(String id, String mailId);
}