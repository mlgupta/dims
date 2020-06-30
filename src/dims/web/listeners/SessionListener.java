package dims.web.listeners;

import dims.web.beans.users.UserProfile;
import dims.web.general.DIMSConstants;
/*import bulbul.boff.general.Treeview;
import bulbul.boff.user.beans.UserProfile;*/

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import org.apache.log4j.Logger;


/**
 *	Purpose: This class is used to  dispose off user specific resources whenever his/her 
 *           session is expired/invalidated.
 *           This is a session listener class whose sessionCreated() and sessionDestroyed() methods are 
 *           called corrospondingly whenever the user,s http session is created and destroyed.
 * 
 * @author              Sudheer Pujar
 * @version             1.0
 * 	Date of creation:   21-01-2005
 * 	Last Modfied by :     
 * 	Last Modfied Date:    
 */
public class SessionListener  implements  HttpSessionListener{

  private UserProfile userProfile = null;
  private HttpSession httpSession = null;
  //private Treeview fontTreeView =null;
 // private Treeview clipartTreeView =null;
  //private Treeview merchandiseTreeView =null;
  //private Treeview sizeTypeTreeView =null;
  private String userId;
  private Logger logger= Logger.getLogger(DIMSConstants.LOGGER.toString());
 
  /* Methods for the HttpSessionListener */
  public void sessionCreated(HttpSessionEvent hse){
    HttpSession httpSession=hse.getSession();
    //logger.info("Http Session created for user"+((UserInfo)httpSession.getAttribute("UserInfo")).getUserID());;
  }

  /**
   * Purpose : To provide definition for sessionDestroyed(HttpSessionEvent) function in HttpSessionListener
   *           interface.This function is called whenever user session is destroyed.
   *           Here the resources/files related to his/her treeview are freed
   *           and all other user specific session data is also cleaned up.
   * @param  : hse - HttpSessionEvent 
   * 
   */ 
  public void sessionDestroyed(HttpSessionEvent hse){
    logger.info("Session invalidation called.");
    logger.info("Starting Http Session cleanup.");                  
    HttpSession httpSession = hse.getSession();
    ServletContext context = httpSession.getServletContext();
    long  start = httpSession.getCreationTime();
    long  end = httpSession.getLastAccessedTime();
    userProfile=(UserProfile)httpSession.getAttribute("userProfile");
    if(userProfile!=null){
      userId=userProfile.getUserId();
      httpSession.removeAttribute("userProfile");

      logger.info("Time for which user '"+userId+"' stayed connected: "+((end - start)/60)+" seconds");
      logger.info("Http session for user '"+userId+"' invalidated");
    }else{
      logger.info("No attributes found in Http Session.");
    }
  }
}


 