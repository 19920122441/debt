package com.zhichangan.debt.address.service;

import com.zhichangan.debt.address.entity.Address;

import java.util.List;
import java.util.Map;

public interface AddressService {
    List<Address> queryAddressByLawCaseId(String id);

    boolean remarkInvalid(String addressId);

    Map<String, Object> addAddress(Address address);

    int deleteByLawCaseId(String lawCaseId);
}
