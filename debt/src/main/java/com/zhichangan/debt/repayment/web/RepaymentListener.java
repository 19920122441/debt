package com.zhichangan.debt.repayment.web;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.excel.metadata.CellExtra;
import com.alibaba.excel.read.listener.ReadListener;
import com.zhichangan.debt.dept.entity.Dept;
import com.zhichangan.debt.dept.mapper.DeptMapper;
import com.zhichangan.debt.lawCase.entity.LawCase;
import com.zhichangan.debt.lawCase.mapper.LawCaseMapper;
import com.zhichangan.debt.lawCase.service.LawCaseService;
import com.zhichangan.debt.repayment.entity.Repayment;
import com.zhichangan.debt.repayment.mapper.RepaymentMapper;
import com.zhichangan.debt.user.entity.User;
import com.zhichangan.debt.user.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.*;
@Component
public class RepaymentListener extends AnalysisEventListener<Repayment> {
    private List<Repayment> repaymentList=new ArrayList<>();
    @Autowired
    private RepaymentMapper repaymentMapper;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private DeptMapper deptMapper;
    @Autowired
    private LawCaseMapper lawCaseMapper;
    @Value("${com.A}")
    private String comA;
    @Value("${com.B}")
    private String comB;
    @Value("${com.C}")
    private String comC;
    @Value("${com.D}")
    private String comD;




    @Override
    public void invoke(Repayment repayment, AnalysisContext analysisContext) {
        //先把还款记录保存
        repayment.setId(UUID.randomUUID().toString().replace("-",""));
        repaymentMapper.insert(repayment);
        //给员工的还款额加上
        LawCase lawCase=lawCaseMapper.queryLawcaseByCardIdAndLawCaseId(repayment.getCardId(),repayment.getLawcaseId());
        User user=userMapper.selectByPrimaryKey(lawCase.getOwner());
        user.setAch1(user.getAch1().add(repayment.getMoney()));
        userMapper.updateByPrimaryKeySelective(user);
        //给部门的还款额加上
        Dept dept = deptMapper.selectByPrimaryKey(user.getDept());
        dept.setAch1(dept.getAch1().add(repayment.getMoney()));
        deptMapper.updateByPrimaryKeySelective(dept);
        //给员工的佣金加上
        System.out.println(comA);
        Double salDouble=null;
        switch (lawCase.getLawcaseLevel()){
            case "A":salDouble=Double.valueOf(comA);break;
            case "B":salDouble=Double.valueOf(comB);break;
            case "C":salDouble=Double.valueOf(comC);break;
            case "D":salDouble=Double.valueOf(comD);break;
        }

        user.setCom1(user.getCom1().multiply(new BigDecimal(salDouble)));
        userMapper.updateByPrimaryKeySelective(user);
        //把案件金额更新
        lawCase.setCardMoney(lawCase.getCardMoney().subtract(repayment.getMoney()));
        lawCaseMapper.updateByPrimaryKeySelective(lawCase);
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {

    }
}
