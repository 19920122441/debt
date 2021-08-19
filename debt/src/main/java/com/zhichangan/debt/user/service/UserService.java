package com.zhichangan.debt.user.service;

import com.zhichangan.debt.mail.entity.Mail;
import com.zhichangan.debt.user.entity.User;

import java.util.List;

public interface UserService {
    User login(String userName, String password);


    List<Mail> addMailListResourceName(List<Mail> mailList);

    User queryUserById(String owner);

    List<User> queryUserByDeptId(String deptId);

    List<User> queryAllUserIdAndNameNotPublicUser();

    List<User> pageQuery(String pageNumber, String pageSize);

    boolean edit(User user);

    Integer delete(String[] ids);

    Boolean add(User user);

    List<User> query(User user);

    User queryUserByJobNumber(String jobNumber);
}
