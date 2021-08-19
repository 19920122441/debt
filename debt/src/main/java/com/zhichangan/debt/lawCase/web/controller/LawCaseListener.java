package com.zhichangan.debt.lawCase.web.controller;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.zhichangan.debt.lawCase.entity.LawCase;
import com.zhichangan.debt.lawCase.service.LawCaseService;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class LawCaseListener extends AnalysisEventListener<LawCase> {

    LawCaseService lawCaseService;
    List<LawCase> lawCaseList=new ArrayList<>();
    HttpServletRequest request;

    public LawCaseListener(LawCaseService lawCaseService, HttpServletRequest request) {
        this.lawCaseService = lawCaseService;
        this.request=request;
    }

    @Override
    public void invoke(LawCase lawCase, AnalysisContext analysisContext) {
        lawCase.setId(UUID.randomUUID().toString().replace("-",""));
        lawCase.setLastCallTime("未跟进");
        lawCase.setNextCallTime("未设置");
        lawCase.setState("查找");
        lawCase.setOwner("cc2380d603454615ba7f036d40584522");
        System.out.println(lawCase);
        lawCaseService.multiAddService(request,lawCase);
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {

    }
}
