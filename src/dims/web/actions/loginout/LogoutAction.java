/*
 *****************************************************************************
 *                       Confidentiality Information                         *
 *                                                                           *
 * This module is the confidential and proprietary information of            *
 * DBSentry Corp.; it is not to be copied, reproduced, or transmitted in any *
 * form, by any means, in whole or in part, nor is it to be used for any     *
 * purpose other than that for which it is expressly provided without the    *
 * written permission of DBSentry Corp.                                      *
 *                                                                           *
 * Copyright (c) 2004-2005 DBSentry Corp.  All Rights Reserved.              *
 *                                                                           *
 *****************************************************************************
 * $Id: LogoutAction.java,v 1.1.1.1 2006/09/29 06:16:21 suved Exp $
 *****************************************************************************
 */
package dims.web.actions.loginout; 
/* Java API */
import dims.web.general.DIMSConstants;
import java.io.IOException;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
/* Struts API */
import org.apache.log4j.Logger;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
/**
 *	Purpose: Enable a user to logout
 *  @author             Suved Mishra 
 *  @version            1.0
 * 	Date of creation:   27-09-2006
 * 	Last Modified by :     
 * 	Last Modified Date:    
 */
public class LogoutAction extends Action  {
  /**
   * This is the main action called from the Struts framework.
   * @param mapping The ActionMapping used to select this instance.
   * @param form The optional ActionForm bean for this request.
   * @param request The HTTP Request we are processing.
   * @param response The HTTP Response we are processing.
   */
  public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
    //Initialize logger
    Logger logger = Logger.getLogger(DIMSConstants.LOGGER.toString());
    logger.info("Entering LogoutAction now...");
    //variable declaration
    HttpSession httpSession = null;        
    try{
      httpSession = request.getSession(false);
      logger.debug("httpSession id to be invalidated is: "+httpSession.getId());
      ServletContext context=request.getSession().getServletContext();
      logger.debug("httpSession id  removed from Context "+httpSession.getId());
      //context.removeAttribute(((UserInfo)httpSession.getAttribute("UserInfo")).getUserID());
      httpSession.invalidate();
    }catch(Exception ex){
      logger.error("An Exception occurred in LogoutAction... ");
      logger.error(ex.toString());
    }
    logger.info("Exiting LogoutAction now...");
    return mapping.findForward("success");
  }
}
    
