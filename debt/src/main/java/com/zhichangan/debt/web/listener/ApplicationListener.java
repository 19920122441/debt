package com.zhichangan.debt.web.listener;

import com.zhichangan.debt.dic.entity.Dic;
import com.zhichangan.debt.dic.mapper.DicMapper;
import com.zhichangan.debt.dic.service.DicService;
import com.zhichangan.debt.dic.service.impl.DicServiceImp;
import com.zhichangan.debt.timer.SalTimer;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationEvent;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.text.ParseException;
import java.util.List;
import java.util.Map;

@WebListener
public class ApplicationListener implements ServletContextListener{


    @Override
    public void contextInitialized(ServletContextEvent sce) {
        try {
            new SalTimer().timerRun();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        ServletContextListener.super.contextDestroyed(sce);
    }


}
