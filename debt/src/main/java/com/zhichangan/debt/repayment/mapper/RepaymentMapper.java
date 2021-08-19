package com.zhichangan.debt.repayment.mapper;

import com.zhichangan.debt.repayment.entity.Repayment;

import java.util.List;

public interface RepaymentMapper {
    int deleteByPrimaryKey(String id);

    int insert(Repayment record);

    int insertSelective(Repayment record);

    Repayment selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(Repayment record);

    int updateByPrimaryKey(Repayment record);

    List<Repayment> queryRepaymentBycardIdAndLawcaseId(String cardId, String lawcaseId);
}