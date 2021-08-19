package com.zhichangan.debt.dic.service.impl;

import com.zhichangan.debt.dic.entity.Dic;
import com.zhichangan.debt.dic.mapper.DicMapper;
import com.zhichangan.debt.dic.service.DicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;


@Service
public class DicServiceImp implements DicService {
    @Autowired
    private DicMapper dicMapper;

}
