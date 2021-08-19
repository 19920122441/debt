package com.zhichangan.debt.mail.service.impl;

import com.zhichangan.debt.dept.entity.Dept;
import com.zhichangan.debt.dept.mapper.DeptMapper;
import com.zhichangan.debt.mail.entity.Mail;
import com.zhichangan.debt.mail.mapper.MailMapper;
import com.zhichangan.debt.mail.service.MailService;
import com.zhichangan.debt.user.entity.User;
import com.zhichangan.debt.user.mapper.UserMapper;
import com.zhichangan.debt.userMailRelationship.entity.UserMailRelationship;
import com.zhichangan.debt.userMailRelationship.mapper.UserMailRelationshipMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class MailServiceImpl implements MailService {
    @Autowired
    private MailMapper mapper;
    @Autowired
    private UserMailRelationshipMapper userMailRelationshipMapper;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private DeptMapper deptMapper;
    /**
     * 查询未读邮件
     * @param session
     * @return
     */
    @Override
    public List<Mail> queryNotReadMail(HttpSession session) {
        //准备参数
        User user= (User) session.getAttribute("user");

        //从数据库中获取
        //该方法第二个参数代表邮件的状态，是否已读
        List<Mail> mailList=mapper.queryNotReadMail(user.getId(),"未读");

        return mailList;
    }

    /**
     * 通过邮件id参数。获取mail。以及该mail的所有收件人姓名
     * 判断附件路径是否为空，如果不为空截取文件名保存咋filename
     * @param mailId
     * @return
     */
    @Override
    public Map<String, Object> detailMail(String mailId,String userId) {
        Map<String,Object> resultMap=new HashMap<>();
        //查询邮件
        Mail mail=mapper.selectByPrimaryKey(mailId);
        //通过mail中的resource查找用户名，保存到mail中的临时属性中
        User mailUser=userMapper.selectByPrimaryKey(mail.getResource());
        mail.setResourceName(mailUser.getName());
        //判断附件路径是否为空，如果不为空截取文件名保存咋filename
        if(mail.getFilePath()!=null){
            String filePath=mail.getFilePath();
            String fileName=filePath.substring(filePath.lastIndexOf("/")+1);
            mail.setFileName(fileName);
            
        }
        //查询所有和该邮件有关系的用户id
        List<UserMailRelationship> userMailRelationshipList=userMailRelationshipMapper.queryRelationshipByMailId(mailId);
        //查询所有用户名。
        List<String> mailUserNameList=new ArrayList<>();
        //如果遍历到当前用户与mail对应的记录，那么将她标记为已读。
        for(UserMailRelationship userMailRelationship:userMailRelationshipList){
            if(userMailRelationship.getUserId().equals(userId)){
                userMailRelationship.setState("已读");
                int i = userMailRelationshipMapper.updateByPrimaryKey(userMailRelationship);
                System.out.println(i);
            }
            User user=userMapper.selectByPrimaryKey(userMailRelationship.getUserId());
            mailUserNameList.add(user.getName());
        }
        mail.setState("已读");
        resultMap.put("mail",mail);
        resultMap.put("mailUserNameList",mailUserNameList);

        return resultMap;
    }

    @Override
    public String downloadFile(String filePath, HttpServletResponse response) {
        FileInputStream fileInputStream=null;
        BufferedInputStream bufferedInputStream=null;
        OutputStream outputStream=null;
        String fileName=filePath.substring(filePath.lastIndexOf("/")+1);
        byte[] buff=new byte[1024];
        try{
            ClassPathResource classPathResource=new ClassPathResource(filePath);
            bufferedInputStream=new BufferedInputStream(classPathResource.getInputStream());
            response.setContentType(MediaType.APPLICATION_OCTET_STREAM.toString());
            response.addHeader("Content-Disposition", "attachment;fileName=" + URLEncoder.encode(fileName, "UTF-8"));
            outputStream=response.getOutputStream();
            while (bufferedInputStream.read(buff)!=-1){
                outputStream.write(buff);
            }
            outputStream.flush();
        } catch (Exception e) {
            e.printStackTrace();
            return "文件下载失败";
        }finally {
            try {
                fileInputStream.close();
            }catch (Exception e){
                e.printStackTrace();
            }
            try {
                bufferedInputStream.close();
            }catch (Exception e){
                e.printStackTrace();
            }
            try {
                outputStream.close();
            }catch (Exception e){
                e.printStackTrace();
            }

        }

        return "文件下载成功";
    }

    //删除邮件

    @Override
    public boolean delete(String mailId) {
        int i = mapper.deleteByPrimaryKey(mailId);
        if(i==0) {
            return false;
        }
        return true;
    }

    @Override
    public Map<String, Integer> mailMain(String id) {
        Map<String,Integer> resultMap=new HashMap<>();
        Integer notReadCount=0;

        List<UserMailRelationship> userMailRelationships=userMailRelationshipMapper.queryRelationshipByUserId(id);
        for(UserMailRelationship userMailRelationship:userMailRelationships){
            if ("未读".equals(userMailRelationship.getState())){
                notReadCount++;
            }
        }
        resultMap.put("mailCount",userMailRelationships.size());
        resultMap.put("notReadCount",notReadCount);
        return resultMap;
    }
    //分页查询
    @Override
    public List<Mail> pageQueryMailByUserId(String pageSize, String pageNumber, String id) {
        //先从用户邮件关系表中查询出所有和用户有关的记录
        //对结果进行遍历，获取mailid查询mail
        List<Mail> mailList=new ArrayList<>();
        //计算需要略过的条数
        Integer pageSizeInt=Integer.parseInt(pageSize);
        Integer pageNumberInt=Integer.parseInt(pageNumber);
        Integer number=(pageNumberInt-1)*pageSizeInt;
        List<UserMailRelationship> userMailRelationshipsList=userMailRelationshipMapper.pageQueryRelationshipByUserId(number,pageSizeInt,id);
        for(UserMailRelationship userMailRelationship:userMailRelationshipsList){
            Mail mail=mapper.selectByPrimaryKey(userMailRelationship.getMailId());
            //根据用户id，从用户表中查询发件人，保存在mail中
            User user = userMapper.selectByPrimaryKey(mail.getResource());
            mail.setResourceName(user.getName());
            mail.setState(userMailRelationship.getState());
            mailList.add(mail);
        }
        return mailList;
    }
    //获取所有的部门以及，部门下的员工作为收件人

    @Override
    public List<Map<String, Object>> getMailAsUser() {
        List<Map<String,Object>> result=new ArrayList<>();
        //查询到所有的部门
        List<Dept> depts=deptMapper.queryAllDept();
        //遍历然后通过部门id查找到该部门下的所有员工
        for(Dept dept:depts){
            Map<String,Object> resultMap=new HashMap<>();
            List<User> userList=userMapper.queryUserByDeptId(dept.getId());
            resultMap.put("dept",dept.getName());
            resultMap.put("user",userList);
            result.add(resultMap);
        }
        return result;

    }
    //发送邮件
    @Override
    public Map<String, Object> sendMail(HttpServletRequest request,String[] asUser, MultipartFile myFile, String title, String text) throws IOException {
        //获取当前登录用户，即收件人
        User user= (User) request.getSession(false).getAttribute("user");
        //现新增一条邮件
        Mail mail=new Mail();
        mail.setTitle(title);
        mail.setTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        mail.setText(text);
        mail.setId(UUID.randomUUID().toString().replace("-",""));
        mail.setResource(user.getId());
        //判断附件是否为空，如果为空，那么setfilename和path
        if(!myFile.isEmpty()){
            String path="/static/file/"+mail.getId()+"/"+myFile.getOriginalFilename();
            mail.setFilePath(path);
            mail.setFileName(myFile.getOriginalFilename());
            //将附件保存在resource/file目录下，并且使用mail生成一个目录，保存在其中
            File file=new File(ResourceUtils.getURL("classpath:").getPath()+path);
            System.out.println(file);
            file.mkdirs();
            myFile.transferTo(file);
        }
        //将邮件保存至数据库中。
        int i = mapper.insertSelective(mail);
        //记录发送成功的条数
        int count=0;
        //记录发送失败的条数
        int errorCount=0;
        //记录发送失败的收件人名称
        List<String> errorAsUsers=new ArrayList<>();
        if(i>0){
            //遍历收件人，生成收件人与邮件关系记录
            for (String s : asUser) {
                UserMailRelationship relationship = new UserMailRelationship();
                relationship.setState("未读");
                relationship.setId(UUID.randomUUID().toString().replace("-", ""));
                relationship.setMailId(mail.getId());
                relationship.setUserId(s);
                int userMailRelationshipResult = userMailRelationshipMapper.insertSelective(relationship);
                //记录发送失败的收件人名称，以及失败条数
                if (userMailRelationshipResult < 1) {
                    //查询失败的收件人名称
                    User errorUser = userMapper.selectByPrimaryKey(s);
                    errorCount++;
                    errorAsUsers.add(errorUser.getName());
                    continue;
                }
                count++;
            }

        }
        HashMap<String,Object> resultMap=new HashMap<>();
        resultMap.put("count",count);
        resultMap.put("errorCount",errorCount);
        resultMap.put("errorAsUser",errorAsUsers);
        return resultMap;
    }
    //查询当前用户发送的邮件，并且将邮件的信息补全
    @Override
    public List<Mail> queryMySendMail(String id,String name) {
        List<Mail> mailList=mapper.queryByResource(id);
        //因为查询到的所有邮件发件人一定是当前用户，无需去数据库中查发件人姓名
        //直接将当前用户姓名保存在mail中
        for(Mail mail:mailList){
            mail.setResourceName(name);
        }
        return mailList;
    }
    //模糊查询
    @Override
    public List<Mail> likeQuery(String id,String name,String text) {
        List<Mail> mailList = mapper.queryByResource(id);
        List<Mail> resultList=new ArrayList<>();
        //遍历所有收到的邮件，检索是否包含text字眼
        for(Mail mail:mailList){
            mail.setResourceName(name);
            int index=mail.toString().indexOf(text);
            if(index>-1){
                //如果这条邮件是符合条件的，将是否已读信息补全，保存在结果集中
                UserMailRelationship userMailRelationship=userMailRelationshipMapper.queryRelationshipByMailIdAndUserId(id,mail.getId());
                mail.setState(userMailRelationship.getState());
                resultList.add(mail);
            }
        }
        return resultList;
    }
}
