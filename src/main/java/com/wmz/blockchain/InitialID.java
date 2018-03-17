package com.wmz.blockchain;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.util.UUID;

/**
 * Created by wmz on 2018/3/17.
 *
 * @author wmz
 */
@WebListener
public class InitialID implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        ServletContext servletContext = servletContextEvent.getServletContext();
        String uuid = UUID.randomUUID().toString();
        servletContext.setAttribute("uuid", uuid);
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {

    }
}
