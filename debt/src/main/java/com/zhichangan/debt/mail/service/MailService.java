package com.zhichangan.debt.mail.service;

import com.zhichangan.debt.mail.entity.Mail;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public interface MailService {
    List<Mail> queryNotReadMail(HttpSession session);

    Map<String, Object> detailMail(String mailId,String userId);

    String downloadFile(String filePath, HttpServletResponse response);

    boolean delete(String mailId);

    Map<String,Integer> mailMain(String id);

    List<Mail> pageQueryMailByUserId(String pageSize, String pageNumber, String id);

    List<Map<String, Object>> getMailAsUser();

    Map<String, Object> sendMail(HttpServletRequest request,String[] asUser, MultipartFile myFile, String title, String text) throws IOException;

    List<Mail> queryMySendMail(String id,String name);

    List<Mail> likeQuery(String id,String name,String text);
}
