package com.zhichangan.debt.lawCase.service.impl;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.read.builder.ExcelReaderBuilder;
import com.zhichangan.debt.address.entity.Address;
import com.zhichangan.debt.address.service.AddressService;
import com.zhichangan.debt.contacts.entity.Contacts;
import com.zhichangan.debt.contacts.service.ContactsService;
import com.zhichangan.debt.dept.mapper.DeptMapper;
import com.zhichangan.debt.lawCase.entity.LawCase;
import com.zhichangan.debt.lawCase.mapper.LawCaseMapper;
import com.zhichangan.debt.lawCase.service.LawCaseService;
import com.zhichangan.debt.lawCase.web.controller.LawCaseListener;
import com.zhichangan.debt.note.entity.Note;
import com.zhichangan.debt.note.mapper.NoteMapper;
import com.zhichangan.debt.note.service.NoteService;
import com.zhichangan.debt.repayment.entity.Repayment;
import com.zhichangan.debt.repayment.mapper.RepaymentMapper;
import com.zhichangan.debt.repayment.web.RepaymentListener;
import com.zhichangan.debt.user.entity.User;
import com.zhichangan.debt.user.mapper.UserMapper;
import com.zhichangan.debt.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class LawCaseServiceImpl implements LawCaseService {

    @Autowired
    private LawCaseMapper lawCaseMapper;
    @Autowired
    private UserService userService;
    @Autowired
    private ContactsService contactsService;
    @Autowired
    private AddressService addressService;
    @Autowired
    private NoteService noteService;
    @Autowired
    private NoteMapper noteMapper;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private RepaymentMapper repaymentMapper;
    @Autowired
    private DeptMapper deptMapper;
    @Autowired
    private RepaymentListener repaymentListener;
    /**
     * 根据id查找案件，以及案件催收员的信息
     * 与案件相关的联系人 催收记录 地址 同催案件
     * 并且转发到详情展示页。
     * @param lawCaseId
     * @return
     */
    @Override
    public ModelAndView detail(String lawCaseId) throws ParseException {
        ModelAndView modelAndView=new ModelAndView();
        //根据id查找案件，
        LawCase lawCase=queryLawCaseById(lawCaseId);
        //并且获取案件中最后跟进时间，用今天的时间减去，获得未跟进时间。set到最后跟时间中，供前端使用。
        //如果是新增的案件，从未跟进，那就保持原样不动。
        if("未跟进".equals(lawCase.getLastCallTime())){

        }else{
            SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String todayStr=simpleDateFormat.format(new Date());
            Date today=simpleDateFormat.parse(todayStr);
            Date lastCallTime=simpleDateFormat.parse(lawCase.getLastCallTime());
            long time=today.getTime()-lastCallTime.getTime();
            lawCase.setLastCallTime(String.valueOf(time/86400000) );
        }

        //根据案件信息查找催收员信息
        User user=userService.queryUserById(lawCase.getOwner());
        //根据案件id查找联系人信息
        List<Contacts> contactsList=contactsService.getContactsByLawCaseId(lawCase.getId());
        //根据案件id查找地址信息
        List<Address> addressList=addressService.queryAddressByLawCaseId(lawCase.getId());
        //根据案件id查找催收记录信息
        List<Note> noteList=noteService.queryNoteByLawCaseId(lawCase.getId());

        //遍历查找到的催收记录，并且查找对应的联系人，存放到note的新增属性中
        for (Note note:noteList) {
            Contacts contacts=contactsService.queryContactsById(note.getContactsId());
            note.setContacts(contacts);
        }
        //统计催记的总个数
        Integer noteCount=noteService.count(lawCaseId);

        //获取同催案件
        List<LawCase> commonLawcase;
        LawCase select=new LawCase();
        select.setLawcaseOwnerId(lawCase.getLawcaseOwnerId());
        commonLawcase=lawCaseMapper.selective(select);
        Iterator<LawCase> iterator = commonLawcase.iterator();
        while(iterator.hasNext()){
            LawCase next = iterator.next();
            if (next.getId().equals(lawCase.getId())){
                iterator.remove();
            }
        }
        //获取还款记录
        List<Repayment> repaymentList=repaymentMapper.queryRepaymentBycardIdAndLawcaseId(lawCase.getCardId(),lawCase.getLawcaseId());
        modelAndView.addObject("repaymentList",repaymentList);
        modelAndView.addObject("noteCount",noteCount);
        modelAndView.addObject("commonLawcase",commonLawcase);
        modelAndView.addObject("noteList",noteList);
        modelAndView.addObject("lawCase",lawCase);
        modelAndView.addObject("lawCaseUser",user);
        modelAndView.addObject("contactsList",contactsList);
        modelAndView.addObject("addressList",addressList);
        return modelAndView;
    }

    /**
     * 获取当天需要跟进的案件
     * @param session
     * @return
     */
    @Override
    public List<LawCase> queryTodayCallLawCase(HttpSession session) {
        //获取当天时间
        String time=new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        //获取案件所有者id
        User user= (User) session.getAttribute("user");
        String id=user.getId();
        //将参数封装到实体类
        LawCase lawCase=new LawCase();
        lawCase.setOwner(id);
        lawCase.setNextCallTime(time);
        //从数据库中查询
        List<LawCase> result=lawCaseMapper.queryTodayCallLawCase(lawCase);
        return result;
    }
    //根据ID查单条。
    @Override
    public LawCase queryLawCaseById(String lawCaseId) {
        LawCase lawCase = lawCaseMapper.selectByPrimaryKey(lawCaseId);
        return lawCase;
    }

    //更新案件状态

    @Override
    public boolean updateState(String lawCaseId, String state) {
        boolean resule=false;
        LawCase lawCase=new LawCase();
        lawCase.setId(lawCaseId);
        lawCase.setState(state);
        int i = lawCaseMapper.updateByPrimaryKeySelective(lawCase);
        if(i>0){
            resule=true;
        }
        return resule;
    }
    //选择更新

    @Override
    public boolean updateSelective(LawCase lawCase) {
        int i = lawCaseMapper.updateByPrimaryKeySelective(lawCase);
        if(i>0){
            return true;
        }
        return false;
    }
    //获取到案件的总个数
    @Override
    public Integer publicLawCase() {
        Integer count=lawCaseMapper.count();
        return count;
    }
    //案件分页查询

    @Override
    public List<LawCase> page(String pageSize, String pageNumber) {
        Integer page=Integer.valueOf(pageNumber);
        Integer ipageSize=Integer.valueOf(pageSize);
        page=ipageSize*(page-1);
        List<LawCase> lawCaseList=lawCaseMapper.page(page,ipageSize);
        return lawCaseList;
    }
    //条件查询

    @Override
    public List<LawCase> query(LawCase lawCase,String minMoney,String maxMoney,String notCallTime) throws ParseException {
        List<LawCase> selective = lawCaseMapper.selective(lawCase);
        //根据案件金额进行数据筛选

        //当最小金额有数据，最大金额无数据
        if(!("".equals(minMoney)) && "".equals(maxMoney)){
            Iterator<LawCase> iterator=selective.iterator();
            while(iterator.hasNext()){
                LawCase lawCase1=iterator.next();
                BigDecimal money =new BigDecimal(minMoney);
                if(lawCase1.getCardMoney().compareTo(money) < 0){
                    iterator.remove();
                }
            }
            //当最大金额有数据，最小金额无数据
        }else if("".equals(minMoney) && !("".equals(maxMoney))){
            Iterator<LawCase> iterator=selective.iterator();
            while(iterator.hasNext()){
                LawCase lawCase1=iterator.next();
                BigDecimal money =new BigDecimal(maxMoney);
                if(lawCase1.getCardMoney().compareTo(money) > 0){
                    iterator.remove();
                }
            }
            //当最大金额和最小金额都有数据。
        }else if(!("".equals(minMoney)) && !("".equals(maxMoney))){
            Iterator<LawCase> iterator=selective.iterator();
            while(iterator.hasNext()){
                LawCase lawCase1=iterator.next();
                BigDecimal minMoneyDec =new BigDecimal(minMoney);
                BigDecimal maxMoneyDec =new BigDecimal(maxMoney);
                if(lawCase1.getCardMoney().compareTo(maxMoneyDec) > 0 || lawCase1.getCardMoney().compareTo(minMoneyDec)<0){
                    iterator.remove();
                }
            }
        }
        //如果未跟进时长有数据，筛选
        if(!"".equals(notCallTime)){
            Iterator<LawCase> iterator=selective.iterator();
            while(iterator.hasNext()){

                LawCase lawCase1=iterator.next();
                //如果是从为跟进的案件，直接从结果集中删除
                if("".equals(lawCase1.getLastCallTime())){
                    iterator.remove();
                    continue;
                }
                SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                //获取当前时间
                Date nowDate=new Date();
                //获取案件最后跟进时间
                Date nextCallTime=simpleDateFormat.parse(lawCase1.getLastCallTime());
                Long longTime=nowDate.getTime()-nextCallTime.getTime();
                Integer integer=(int)(longTime/86400000);
                if (integer<Integer.parseInt(notCallTime)){
                    iterator.remove();
                }
            }
        }


        return selective;
    }
    //根据id删除案件
    //并且删除与案件相关联的联系人与地址,与催记
    @Override
    public void delete(String lawCaseId) {
        lawCaseMapper.deleteByPrimaryKey(lawCaseId);
        noteService.deleteByLawCaseId(lawCaseId);
        contactsService.deleteByLawCaseId(lawCaseId);
        addressService.deleteByLawCaseId(lawCaseId);
    }
    //新增一条记录，并将信息补全
    //并且将案件中的地址信息保存到地址表中
    @Override
    @Transactional
    public boolean add(LawCase lawCase,HttpServletRequest request) {
        boolean result=true;
        lawCase.setId(UUID.randomUUID().toString().replace("-",""));
        lawCase.setState("查找");
        lawCase.setNextCallTime("未设置");
        lawCase.setLastCallTime("未跟进");
        lawCase.setOwner("000d9f92282746928fc5e42d41c8327e");
        int i = lawCaseMapper.insertSelective(lawCase);
        if(i<=0){
            result =false;
        }
        //保存一条催记
        Note note=new Note();
        note.setText("分配案件至公共账户。");
        note.setContactsId("000d9f92282746928fc5e42d41c8327e");
        note.setId(UUID.randomUUID().toString().replace("-",""));
        note.setLawcaseId(lawCase.getId());
        note.setTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        User user= (User) request.getSession(false).getAttribute("user");
        note.setUserName(user.getName());
        int i1 = noteMapper.insertSelective(note);
        if(i1<1){
            return false;
        }
        //拆解出地址信息，保存到地址表中
        Address lawcaseOwnerAddress=new Address();
        lawcaseOwnerAddress.setName("户籍地址");
        lawcaseOwnerAddress.setAddress(lawCase.getLawcaseOwnerAddress());
        lawcaseOwnerAddress.setLawcaseId(lawCase.getId());
        lawcaseOwnerAddress.setState("有效");
        lawcaseOwnerAddress.setId(UUID.randomUUID().toString().replace("-",""));

        Address workAddress=new Address();
        workAddress.setId(UUID.randomUUID().toString().replace("-",""));
        workAddress.setAddress(lawCase.getLawcaseOwnerWorkAddress());
        workAddress.setLawcaseId(lawCase.getId());
        workAddress.setState("有效");
        workAddress.setName(lawCase.getLawcaseOwnerWork());

        Address nowAddress=new Address();
        nowAddress.setId(UUID.randomUUID().toString().replace("-",""));
        nowAddress.setAddress(lawCase.getLawcaseOwnerNowAddress());
        nowAddress.setLawcaseId(lawCase.getId());
        nowAddress.setState("有效");
        nowAddress.setName("现居住地");


        Address listAddress=new Address();
        listAddress.setId(UUID.randomUUID().toString().replace("-",""));
        listAddress.setAddress(lawCase.getCardListAddress());
        listAddress.setName("账单地址");
        listAddress.setLawcaseId(lawCase.getId());
        listAddress.setState("有效");
        boolean listAddressResult=(boolean) addressService.addAddress(listAddress).get("result");
        boolean lawcaseOwnerAddressResult= (boolean) addressService.addAddress(lawcaseOwnerAddress).get("result");
        boolean workAddressResult= (boolean) addressService.addAddress(workAddress).get("result");
        boolean nowAddressResult= (boolean) addressService.addAddress(nowAddress).get("result");

        if(!(listAddressResult && lawcaseOwnerAddressResult && workAddressResult && nowAddressResult)){
            result=false;
        }
        return result;
    }

    @Transactional
    @Override
    public boolean multiAdd(HttpServletRequest request,MultipartFile file) throws IOException {

        EasyExcel.read(file.getInputStream(),LawCase.class,new LawCaseListener(this,request)).sheet().headRowNumber(3).doRead();

        return true;
    }

    @Override
    public boolean multiAddService(HttpServletRequest request,LawCase lawCase) {
        //先对案件进行插入，失败返回false
        if(lawCaseMapper.insertSelective(lawCase)<1){
            return false;
        }//保存一条催记
        //保存一条催记
        Note note=new Note();
        note.setText("分配案件至公共账户。");
        note.setContactsId("000d9f92282746928fc5e42d41c8327e");
        note.setId(UUID.randomUUID().toString().replace("-",""));
        note.setLawcaseId(lawCase.getId());
        note.setTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        User user= (User) request.getSession(false).getAttribute("user");
        note.setUserName(user.getName());
        int i1 = noteMapper.insertSelective(note);
        if(i1<1){
            return false;
        }
        //从案件中获取所有地址
        //并且将地址保存到地址表中。
        Address lawcaseOwnerAddress=new Address();
        lawcaseOwnerAddress.setName("户籍地址");
        lawcaseOwnerAddress.setAddress(lawCase.getLawcaseOwnerAddress());
        lawcaseOwnerAddress.setLawcaseId(lawCase.getId());
        lawcaseOwnerAddress.setId(UUID.randomUUID().toString().replace("-",""));
        lawcaseOwnerAddress.setState("有效");

        Address workAddress=new Address();
        workAddress.setId(UUID.randomUUID().toString().replace("-",""));
        workAddress.setAddress(lawCase.getLawcaseOwnerWorkAddress());
        workAddress.setName("单位地址");
        workAddress.setState("有效");
        workAddress.setLawcaseId(lawCase.getId());

        Address nowAddress=new Address();
        nowAddress.setId(UUID.randomUUID().toString().replace("-",""));
        nowAddress.setAddress(lawCase.getLawcaseOwnerNowAddress());
        nowAddress.setState("有效");
        nowAddress.setLawcaseId(lawCase.getId());
        nowAddress.setName("现居住地");

        Address listAddress=new Address();
        listAddress.setId(UUID.randomUUID().toString().replace("-",""));
        listAddress.setAddress(lawCase.getCardListAddress());
        listAddress.setName("账单地址");
        listAddress.setLawcaseId(lawCase.getId());
        listAddress.setState("有效");
        //将以上地址保存，如果失败返回flase
        if(!(boolean)addressService.addAddress(lawcaseOwnerAddress).get("result")){
            return false;
        }
        if(!(boolean)addressService.addAddress(workAddress).get("result")){
            return false;
        }
        if(!(boolean)addressService.addAddress(nowAddress).get("result")){
            return false;
        }
        if(!(boolean)addressService.addAddress(listAddress).get("result")){
            return false;
        }
        //判断联系人是否为空，如果不为空，从案件中抽取出来并且保存，如果保存失败返回false
        if(lawCase.getContactsNameOne()!="" && lawCase.getContactsNameOne()!=null){
            Contacts contacts=new Contacts();
            contacts.setId(UUID.randomUUID().toString().replace("-",""));
            contacts.setState("有效");
            contacts.setLawcaseId(lawCase.getId());
            contacts.setName(lawCase.getContactsNameOne());
            contacts.setRelationship(lawCase.getContactsRelationshipOne());
            contacts.setSex(lawCase.getContactsSexOne());
            contacts.setNumber(lawCase.getContactsNumberOne());
            if(!(boolean)contactsService.add(contacts).get("result")){
                return false;
            }
        }
        if(lawCase.getContactsNameTwo()!="" && lawCase.getContactsNameTwo()!=null){
            Contacts contactsTwo=new Contacts();
            contactsTwo.setId(UUID.randomUUID().toString().replace("-",""));
            contactsTwo.setState("有效");
            contactsTwo.setLawcaseId(lawCase.getId());
            contactsTwo.setName(lawCase.getContactsNameTwo());
            contactsTwo.setRelationship(lawCase.getContactsRelationshipTwo());
            contactsTwo.setSex(lawCase.getContactsSexTwo());
            contactsTwo.setNumber(lawCase.getContactsNumberTwo());
            if (!(boolean)contactsService.add(contactsTwo).get("result")){
                return false;
            }
        }

        if(lawCase.getContactsNameThree()!="" && lawCase.getContactsNameThree()!=null){
            Contacts contactsThree=new Contacts();
            contactsThree.setId(UUID.randomUUID().toString().replace("-",""));
            contactsThree.setState("有效");
            contactsThree.setLawcaseId(lawCase.getId());
            contactsThree.setName(lawCase.getContactsNameThree());
            contactsThree.setRelationship(lawCase.getContactsRelationshipThree());
            contactsThree.setSex(lawCase.getContactsSexThree());
            contactsThree.setNumber(lawCase.getContactsNumberThree());
            if (!(boolean)contactsService.add(contactsThree).get("result")){
                return false;
            }
        }



        return true;

    }

    //为案件分配催收员

    @Override
    @Transactional
    public int editLawCaseOwner(String[] lawCaseIds, String userId, HttpServletRequest request) {
        int result=0;
        for(int i=0;i<lawCaseIds.length;i++){
            LawCase lawCase=new LawCase();
            lawCase.setId(lawCaseIds[i]);
            lawCase.setOwner(userId);
            if(lawCaseMapper.updateByPrimaryKeySelective(lawCase)>0){
                result+=1;
                User user = (User) request.getSession(false).getAttribute("user");
                Note note=new Note();
                //查找被分配案件的催收员
                User toUser = userService.queryUserById(userId);
                //为本次分配案件操作新增一条催记
                note.setId(UUID.randomUUID().toString().replace("-",""));
                note.setTime(new SimpleDateFormat("yyyy-MM-dd HH-mm-ss").format(new Date()));
                note.setLawcaseId(lawCaseIds[i]);
                note.setUserName(user.getName());
                note.setText("分配案件至"+toUser.getName()+"。");
                //000d9f92282746928fc5e42d41c8327e是系统消息联系人
                note.setContactsId("000d9f92282746928fc5e42d41c8327e");
                noteService.add(note);

            }
        }
        return result;
    }

    //自动分配案件
    //将所有未分配，既在公共账户的案件平均分配至所有员工
    @Transactional
    @Override
    public Integer autoEditLawCaseOwner(HttpServletRequest request) {
        int result=0;
        //获取所有的未分配案件，即在公共账户中的案件
        List<LawCase> lawCaseList=lawCaseMapper.getPublicLawCase();
        if(lawCaseList.size()==0){
            return 0;
        }
        //获取所有的用户，除公共账户之外
        List<User> userList=userService.queryAllUserIdAndNameNotPublicUser();
        //判断案件数量是否多余员工数，成立则计算出每个员工平均分配到的案件数，以及是否有多余的案件。
        Integer userArgLawCaseNumber=0;
        Integer userArgLawcaseremainder=0;
        if (lawCaseList.size()>=userList.size()){
            userArgLawCaseNumber=lawCaseList.size()/userList.size();
            userArgLawcaseremainder=lawCaseList.size()%userList.size();
            int index=0;
            //循环遍历案件lsit
            out:for(int i=0;i<lawCaseList.size();i++){
                //循环遍历用户list
                for(;index<userList.size();){

                    //为案件 重新分配催收员
                    LawCase lawCase=lawCaseList.get(i);
                    User user=userList.get(index);
                    lawCase.setOwner(user.getId());
                    result+=lawCaseMapper.updateByPrimaryKeySelective(lawCase);
                    //为本次分配催收员的操作新增一条催记
                    Note note=new Note();
                    note.setId(UUID.randomUUID().toString().replace("-",""));
                    note.setText("分配案件至"+user.getName()+"。");
                    note.setTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
                    note.setLawcaseId(lawCase.getId());
                    //该联系人为系统通知联系人
                    note.setContactsId("000d9f92282746928fc5e42d41c8327e");
                    User loginUser= (User) request.getSession(false).getAttribute("user");
                    note.setUserName(loginUser.getName());
                    noteService.add(note);


                    //贩毒案每当循环到第平均案件数个案件时，将j++也就是开始为下一个员工分配。
                    if(i%userArgLawCaseNumber==0){
                        index++;
                        break ;
                    }
                    //判断如果案件是可以平均分配的最后一个案件，那么终止循环
                    //解释为什么注释代码，因为如果是最后一个案件，那么在这个for循环判断条件是否循环时，就已经到了最后一个员工，for循环不成立。这段代码永远不会执行，
//                    if(i==(lawCaseList.size()-userArgLawcaseremainder)-1){
//                        break out;
//                    }

                }
            }
            int index2=0;
            //循环分配剩下无法被平均分配的案件
            if(userArgLawcaseremainder!=0){
                for(int i=lawCaseList.size()-userArgLawcaseremainder;i<lawCaseList.size();i++){
                    for(;index2<userList.size();){
                        //为案件 重新分配催收员
                        LawCase lawCase=lawCaseList.get(i);
                        User user=userList.get(index2);
                        lawCase.setOwner(user.getId());
                        result+=lawCaseMapper.updateByPrimaryKeySelective(lawCase);
                        //为本次分配催收员的操作新增一条催记
                        Note note=new Note();
                        note.setId(UUID.randomUUID().toString().replace("-",""));
                        note.setText("分配案件至"+user.getName()+"。");
                        note.setTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
                        note.setLawcaseId(lawCase.getId());
                        //该联系人为系统通知联系人
                        note.setContactsId("000d9f92282746928fc5e42d41c8327e");
                        User loginUser= (User) request.getSession(false).getAttribute("user");
                        note.setUserName(loginUser.getName());
                        noteService.add(note);
                        index2++;
                        break;

                    }


                }
            }


        }
        //如果案件数量小于员工数量
        if(lawCaseList.size()<userList.size()){
            for (int i=0;i<lawCaseList.size();i++){
                //为案件 重新分配催收员
                LawCase lawCase=lawCaseList.get(i);
                User user=userList.get(i);
                lawCase.setOwner(user.getId());
                result+=lawCaseMapper.updateByPrimaryKeySelective(lawCase);
                //为本次分配催收员的操作新增一条催记
                Note note=new Note();
                note.setId(UUID.randomUUID().toString().replace("-",""));
                note.setText("分配案件至"+user.getName()+"。");
                note.setTime(new SimpleDateFormat("yyyy-MM-dd HH-mm-ss").format(new Date()));
                note.setLawcaseId(lawCase.getId());
                //该联系人为系统通知联系人
                note.setContactsId("000d9f92282746928fc5e42d41c8327e");
                User loginUser= (User) request.getSession(false).getAttribute("user");
                note.setUserName(loginUser.getName());
                noteService.add(note);
            }
        }

        return result;
    }

    @Override
    public Integer autoEditLawCaseOwner2(HttpServletRequest request) {
        User loginUser= (User) request.getSession(false).getAttribute("user");
        Integer count=0;
        //首先获取所有未分配的案件都是哪些银行
        List<String> bankList=lawCaseMapper.queryNotOwnerBank();
        //获取这些部门下的员工,以及属于这些部门的未分配案件
        for(String bank:bankList){
            List<LawCase> lawCaseList=lawCaseMapper.queryNotOwnerByBank(bank);
            List<User> userList=userMapper.queryUserByDept(bank+"部门");
            //如果该部门下的员工数为0，直接跳出本次循环。
            if (userList.size()<1){
                continue;
            }
            //如果该部门未分配的案件数大于该部门的员工数

            Integer avgLawCaseSize=0;
            Integer overLawCaseSize=0;
            //计算每个员工平均能分配到多少案件
            avgLawCaseSize=lawCaseList.size()/userList.size();
            //计算当平均分配完毕，剩下多少个案件无法被平均分配
            overLawCaseSize=lawCaseList.size()%userList.size();
            int j=0;
            //如果每个员工可平均分配数量大于0，开始分配
            if(avgLawCaseSize>0){
                for(int i=0;i<lawCaseList.size();i++){
                    while (j<userList.size()) {
                        //修改案件所有者
                        LawCase lawCase = lawCaseList.get(i);
                        User user=userList.get(j);
                        LawCase lawCase1=new LawCase();
                        lawCase1.setId(lawCase.getId());
                        lawCase1.setOwner(user.getId());
                        lawCaseMapper.updateByPrimaryKeySelective(lawCase1);
                        //为案件分配新增一条催记
                        Note note=new Note();
                        note.setUserName(loginUser.getName());
                        note.setTime(new SimpleDateFormat("yyyy-MM-dd HH-mm-ss").format(new Date()));
                        note.setId(UUID.randomUUID().toString().replace("-",""));
                        note.setText("自动分配案件至"+user.getName()+"!");
                        note.setContactsId("000d9f92282746928fc5e42d41c8327e");
                        note.setLawcaseId(lawCase.getId());
                        noteMapper.insertSelective(note);
                        //分配完毕后，记录数+1
                        count++;
                        //当当前用户已经分配够最大分配数量，开始为下一个用户分配。
                        if((i+1)%avgLawCaseSize==0){
                            j++;
                            break;
                        }else {
                            break;
                        }
                    }
                }
            }
            //如果有剩下为能平均分配的案件，开始分配
            if(overLawCaseSize>0){
                for(int i=lawCaseList.size()-overLawCaseSize-1;i<lawCaseList.size();i++){
                    LawCase lawCase=lawCaseList.get(i);
                    User user=userList.get(i);
                    //修改案件所有者
                    LawCase lawCase1=new LawCase();
                    lawCase1.setId(lawCase.getId());
                    lawCase1.setOwner(user.getId());
                    lawCaseMapper.updateByPrimaryKeySelective(lawCase1);
                    //为案件分配新增一条催记
                    Note note=new Note();
                    note.setUserName(loginUser.getName());
                    note.setTime(new SimpleDateFormat("yyyy-MM-dd HH-mm-ss").format(new Date()));
                    note.setId(UUID.randomUUID().toString().replace("-",""));
                    note.setText("自动分配案件至"+user.getName()+"!");
                    note.setContactsId("000d9f92282746928fc5e42d41c8327e");
                    note.setLawcaseId(lawCase.getId());
                    noteMapper.insertSelective(note);
                    //分配完毕后，记录数+1
                    count++;
                }
            }

        }

        return count;
    }

    //标记案件颜色 并且要添加一个催记，
    @Transactional
    @Override
    public Map<String,Object> markColor(String lawCaseId, String color,HttpServletRequest request) {
        boolean result=true;
        Note note=new Note();
        User user= (User) request.getSession(false).getAttribute("user");
        note.setId(UUID.randomUUID().toString().replace("-",""));
        note.setUserName(user.getName());
        note.setTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        if("red".equals(color)){
            note.setText("标记案件颜色为红色。");
        }
        if("blue".equals(color)){
            note.setText("标记案件颜色为蓝色。");
        }
        if("black".equals(color)){
            note.setText("清除案件颜色标记。");
        }
        note.setLawcaseId(lawCaseId);
        note.setContactsId("000d9f92282746928fc5e42d41c8327e");
        noteService.add(note);


        LawCase lawCase=new LawCase();
        lawCase.setId(lawCaseId);
        lawCase.setColor(color);

        int i = lawCaseMapper.updateByPrimaryKeySelective(lawCase);
        if(i<1){
            result=false;
        }
        Map<String,Object> resultMap=new HashMap<>();
        resultMap.put("success",result);
        resultMap.put("html",note);
        return resultMap;
    }
    //获取我的案件个数
    @Override
    public Integer myLawCase(String userId) {
        Integer count=lawCaseMapper.countByOwner(userId);
        return count;
    }

    //我的案件分页查询
    @Override
    public List<LawCase> pageByOwner(String pageSize, String pageNumber,String userId) {
        System.out.println(userId);
        Integer page=Integer.valueOf(pageNumber);
        Integer ipageSize=Integer.valueOf(pageSize);
        page=ipageSize*(page-1);
        List<LawCase> lawCaseList=lawCaseMapper.pageByOwner(page,ipageSize,userId);
        return lawCaseList;
    }
    //模糊查询，并且返回案件的个数
    //先查询出所有的案件，在将案件的姓名 委案编号 联系人。。。等等信息拼接成一个长字符串，在字符串中查找text，如果存在便符合条件
    @Override
    public Map<String, Object> likeQuery(String text) {
        Map<String,Object> resultMap=new HashMap<>();
        List<LawCase> all=lawCaseMapper.queryAllLawCase();
        List<LawCase> resultList=new ArrayList<>();
        for(LawCase lawCase:all){
            StringBuilder lawCaseStr=new StringBuilder();
            lawCaseStr.append(lawCase.toString());
            //查询与案件关联的联系人
            List<Contacts> contactsList=contactsService.getContactsByLawCaseId(lawCase.getId());
            for(Contacts contacts:contactsList){
                lawCaseStr.append(contacts.toString());
            }
            //查询与案件关联的地址
            List<Address> addressList=addressService.queryAddressByLawCaseId(lawCase.getId());
            for(Address address:addressList){
                lawCaseStr.append(address.toString());
            }
            //查询与案件相关的催记
            List<Note> noteList=noteService.queryNoteByLawCaseId(lawCase.getId());
            for(Note note:noteList){
                lawCaseStr.append(note.toString());
            }
            System.out.println(lawCaseStr);
            //判断需要查找的信息在生成的字符串中是否存在
            if(lawCaseStr.indexOf(text)>-1){

                resultList.add(lawCase);
            }



        }
        resultMap.put("lawCaseList",resultList);
        resultMap.put("count",resultList.size());
        return resultMap;
    }

    @Override
    public void repayment(MultipartFile file) throws IOException {
        System.out.println(123);
        EasyExcel.read(file.getInputStream(), Repayment.class,repaymentListener).sheet().headRowNumber(3).doRead();

    }
}
