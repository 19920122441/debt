package com.zhichangan.debt.address.web.controller;

import com.zhichangan.debt.address.entity.Address;
import com.zhichangan.debt.address.service.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

@Controller
public class AddressController {
    @Autowired
    private AddressService addressService;
    //根据id更新地址状态为失效
    @RequestMapping("/address/remarkInvalid")
    @ResponseBody
    public Map<String,Object> remarkInvalid(String addressId){
        boolean result=addressService.remarkInvalid(addressId);
        HashMap<String, Object> resultMap = new HashMap<>();
        resultMap.put("result",result);
        return resultMap;
    }
    //新增地址
    @RequestMapping("/address/add")
    @ResponseBody
    public Map<String,Object> addAddress(Address address){
        Map<String,Object> resultMap=addressService.addAddress(address);
        return resultMap;
    }
}
