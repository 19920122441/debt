package com.zhichangan.debt.lawCase.service;

import com.zhichangan.debt.lawCase.entity.LawCase;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;
import java.util.List;
import java.util.Map;

public interface LawCaseService {
    ModelAndView detail(String lawCaseId) throws ParseException;
    List<LawCase> queryTodayCallLawCase(HttpSession session);

    LawCase queryLawCaseById(String lawCaseId);

    boolean updateState(String lawCaseId, String state);

    boolean updateSelective(LawCase lawCase);

    Integer publicLawCase();

    List<LawCase> page(String pageSize, String pageNumber);

    List<LawCase> query(LawCase lawCase,String minMoney,String maxMoney,String notCallTime) throws ParseException;

    void delete(String lawCaseId);


    boolean add(LawCase lawCase,HttpServletRequest request);

    boolean multiAdd(HttpServletRequest request,MultipartFile file) throws IOException;
    boolean multiAddService(HttpServletRequest request,LawCase lawCase);

    int editLawCaseOwner(String[] lawCaseIds, String userId, HttpServletRequest request);

    Integer autoEditLawCaseOwner(HttpServletRequest request);
    Integer autoEditLawCaseOwner2(HttpServletRequest request);

    Map<String,Object> markColor(String lawCaseId, String color,HttpServletRequest request);

    Integer myLawCase(String userId);

    List<LawCase> pageByOwner(String pageSize, String pageNumber,String userId);

    Map<String, Object> likeQuery(String text);

    void repayment(MultipartFile file) throws IOException;
}
