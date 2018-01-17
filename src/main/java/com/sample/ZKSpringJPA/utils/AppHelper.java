package com.sample.ZKSpringJPA.utils;

import org.zkoss.zk.ui.Executions;

public class AppHelper {

    public static final String SERVER_NAME = Executions.getCurrent().getServerName();
    public static final int PORT = Executions.getCurrent().getServerPort();
    public static final String SCHEME = Executions.getCurrent().getScheme();
    public static final String CONTEXT = Executions.getCurrent().getContextPath();
    public static final String APPLICATION_PATH = SCHEME + "://" + SERVER_NAME +":"+PORT+ CONTEXT ;
}
