package com.zhichangan.debt.address.mapper;

import com.zhichangan.debt.address.entity.Address;

import java.util.List;

public interface AddressMapper {
    int deleteByPrimaryKey(String id);

    int insert(Address record);

    int insertSelective(Address record);

    Address selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(Address record);

    int updateByPrimaryKey(Address record);

    List<Address> queryAddressByLawCaseId(String id);

    int deleteByLawCaseId(String lawCaseId);
}