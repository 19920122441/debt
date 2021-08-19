package com.zhichangan.debt.dic.mapper;

import com.zhichangan.debt.dic.entity.Dic;

import java.util.List;

public interface DicMapper {
    int insert(Dic record);

    int insertSelective(Dic record);

    List<Dic> getAll();
}