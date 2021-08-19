package com.zhichangan.debt.address.service.impl;

import com.zhichangan.debt.address.entity.Address;
import com.zhichangan.debt.address.mapper.AddressMapper;
import com.zhichangan.debt.address.service.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
public class AddressServiceImpl implements AddressService {
    @Autowired
    private AddressMapper addressMapper;
    @Override
    public List<Address> queryAddressByLawCaseId(String id) {
        List<Address> addressList=addressMapper.queryAddressByLawCaseId(id);
        return addressList;
    }

    @Override
    public boolean remarkInvalid(String addressId) {
        Address address = new Address();
        address.setId(addressId);
        address.setState("失效");
        int i = addressMapper.updateByPrimaryKeySelective(address);
        if (i>0){
            return true;
        }else {
            return false;
        }
    }

    //新增联系人

    @Override
    public Map<String, Object> addAddress(Address address) {
        Map<String,Object> result=new HashMap<>();
        address.setId(UUID.randomUUID().toString().replace("-",""));
        address.setState("有效");
        int insert = addressMapper.insert(address);
        if (insert>=0){
            result.put("result",true);
            result.put("address",address);
        }
        return result;
    }
    //根据案件id删除联系人

    @Override
    public int deleteByLawCaseId(String lawCaseId) {
        int result=addressMapper.deleteByLawCaseId(lawCaseId);
        return result;
    }
}
