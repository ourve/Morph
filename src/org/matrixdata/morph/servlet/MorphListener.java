package org.matrixdata.morph.servlet;

import org.matrixdata.morph.dal.DALConfig;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class MorphListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        DALConfig.init();
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
    }
}
