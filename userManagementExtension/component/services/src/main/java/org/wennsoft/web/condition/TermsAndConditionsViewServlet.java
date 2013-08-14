package org.wennsoft.web.condition;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


/**
 * @author MedAmine Krout
 */
public class TermsAndConditionsViewServlet extends HttpServlet {
  private static final long serialVersionUID = 2L;
  private final static String TC_JSP_RESOURCE = "/WEB-INF/jsp/condition/wennsoftconditions.jsp";

  @Override
  protected void doPost(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws ServletException, IOException {

      httpServletRequest.setAttribute("contextPath", httpServletRequest.getContextPath());
      httpServletRequest.setAttribute("lang", httpServletRequest.getLocale().getLanguage());
      httpServletResponse.setContentType("text/html; charset=UTF-8");
      httpServletResponse.setCharacterEncoding("UTF-8");
      getServletContext().getRequestDispatcher(TC_JSP_RESOURCE).include(httpServletRequest, httpServletResponse);
  }

  @Override
  protected void doGet(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws ServletException, IOException {
    doPost(httpServletRequest, httpServletResponse);
  }

}