/**
 * Copyright (c) THALES, 2009. All rights reserved.
 */
package com.thalesgroup.orchestra.framework.webservices;

import java.io.IOException;

import javax.servlet.Servlet;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

/**
 * @author t0076261
 */
public class PingServlet implements Servlet {
  /**
   * @see javax.servlet.Servlet#destroy()
   */
  public void destroy() {
    // Nothing to do.
  }

  /**
   * @see javax.servlet.Servlet#getServletConfig()
   */
  public ServletConfig getServletConfig() {
    // Nothing to do.
    return null;
  }

  /**
   * @see javax.servlet.Servlet#getServletInfo()
   */
  public String getServletInfo() {
    // Nothing to do.
    return null;
  }

  /**
   * @see javax.servlet.Servlet#init(javax.servlet.ServletConfig)
   */
  public void init(ServletConfig config_p) throws ServletException {
    // Nothing to do.
  }

  /**
   * @see javax.servlet.Servlet#service(javax.servlet.ServletRequest, javax.servlet.ServletResponse)
   */
  public void service(ServletRequest req_p, ServletResponse res_p) throws ServletException, IOException {
    res_p.getWriter().write(System.getProperty("user.name")); //$NON-NLS-1$
  }
}