package dims.web.beans.loginout;

import dims.web.actionforms.loginout.LoginForm;
import dims.web.beans.users.UserProfile;
import dims.web.general.DBConnection;
import dims.web.general.DIMSConstants;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.sql.DataSource;
import org.apache.log4j.Logger;


public final class Operations  {

  static Logger logger = Logger.getLogger(DIMSConstants.LOGGER.toString());
  
  public static UserProfile authenticateUser( DataSource ds , LoginForm form ) 
                throws SQLException , Exception {
    
    DBConnection dbConnBean = null;
    ResultSet rs = null;
    UserProfile userProfile = null;
    
    StringBuffer query = new StringBuffer();
    query.append(" select ut.user_tbl_pk , ut.user_id , ut.user_name , ut.user_status , ut.dept_tbl_fk , dt.dept_id from user_tbl ut , dept_tbl dt WHERE 1=1 AND ut.user_id='"+form.getUserID()+"' AND ut.user_password='"+form.getUserPassword()+"' AND ut.dept_tbl_fk=dt.dept_tbl_pk");
    
    try{
      dbConnBean = new DBConnection(ds);
      rs = dbConnBean.executeQuery(query.toString());
      if( rs != null ){
        while( rs.next() ){
          userProfile = new UserProfile();
          userProfile.setUserTblPk(String.valueOf(rs.getInt("user_tbl_pk")));
          userProfile.setUserId(rs.getString("user_id"));
          userProfile.setUserName(rs.getString("user_name"));
          userProfile.setUserStatus((rs.getString("user_status").equals("A"))?"1":"2");
          userProfile.setDeptFK(rs.getString("dept_tbl_fk"));
          userProfile.setDeptID(rs.getString("dept_id"));
        }
      }
    }catch (SQLException sqle) {
      logger.error("SQLException occurred in getUserDetails");
      logger.error(sqle.toString());
      throw sqle;
    }catch (Exception e) {
      logger.error("Exception occurred in getUserDetails");
      logger.error(e.toString());
      throw e;
    }finally{
      dbConnBean.free(rs);
    }
    return userProfile;
  }
}