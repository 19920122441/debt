package com.zhichangan.debt.lawCase.mapper;

import com.zhichangan.debt.lawCase.entity.LawCase;

import java.util.List;

public interface LawCaseMapper {
    int deleteByPrimaryKey(String id);

    int insert(LawCase record);

    int insertSelective(LawCase record);

    LawCase selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(LawCase record);

    int updateByPrimaryKey(LawCase record);

    List<LawCase> selective(LawCase lawCase);

    Integer count();

    List<LawCase> page(Integer page, Integer ipageSize);

    List<LawCase> queryTodayCallLawCase(LawCase lawCase);

    List<LawCase> getPublicLawCase();

    Integer countByOwner(String userId);

    List<LawCase> pageByOwner(Integer page, Integer ipageSize,String userId);

    List<LawCase> queryAllLawCase();

    List<String> queryNotOwnerBank();

    List<LawCase> queryNotOwnerByBank(String bank);

    Integer queryTodayCallNumberByUserId(String id,String moring);

    LawCase queryLawcaseByCardIdAndLawCaseId(String cardId, String lawcaseId);
}