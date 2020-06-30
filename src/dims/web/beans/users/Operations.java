package dims.web.beans.users;

import dims.web.actionforms.users.UserListForm;
import dims.web.actionforms.users.UserNewEditForm;
import dims.web.general.DBConnection;
import dims.web.general.DIMSConstants;
import dims.web.general.GeneralUtil;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import javax.sql.DataSource;
import org.apache.log4j.Logger;


public final class Operations  {

  static Logger logger = Logger.getLogger(DIMSConstants.LOGGER.toString());
  
  public static void addUser( DataSource ds , UserNewEditForm form ) 
          throws SQLException , Exception {
    
    CallableStatement cs = null;
    DBConnection dbConnBean = null;
    int user_tbl_pk = 0;
    String query = "{? = call sp_ins_user(?,?,?,?,?)}";
    
    try {
      dbConnBean = new DBConnection(ds);
      cs = dbConnBean.prepareCall(query);
      cs.registerOutParameter(1,Types.INTEGER);
      cs.setString(2,form.getTxtUserID());
      cs.setString(3,form.getTxtUserName());
      cs.setString(4,form.getTxtUserPassword());
      cs.setString(5,((form.getRadStatus().equals("1")?"A":"S")));
      cs.setInt(6,Integer.parseInt(form.getCboDepartments()));
      dbConnBean.executeCallableStatement(cs);
      user_tbl_pk = cs.getInt(1);
      logger.debug(" New User Number : "+user_tbl_pk);            
    }catch (SQLException sqle) {
      logger.error("SQLException occurred in addUser");
      logger.error( sqle.toString() );
      throw sqle;
    }catch( Exception e ){
      logger.error("Exception occurred in addUser");
      logger.error( e.toString() );
      throw e;
    }finally{
      dbConnBean.free(cs); 
    }
  }


  public static void editUser( DataSource ds , UserNewEditForm form ) 
          throws SQLException , Exception {
    
    CallableStatement cs = null;
    DBConnection dbConnBean = null;
    int user_tbl_pk = 0;
    String query = "{? = call sp_upd_user(?,?,?,?,?,?)}";
    
    try {
      dbConnBean = new DBConnection(ds);
      cs = dbConnBean.prepareCall(query);
      cs.registerOutParameter(1,Types.INTEGER);
      cs.setInt(2,Integer.parseInt(form.getCboDepartments()));
      cs.setString(3,form.getTxtUserID());
      cs.setString(4,form.getTxtUserPassword());
      cs.setString(5,form.getTxtUserName());
      cs.setString(6,((form.getRadStatus().equals("1")?"A":"S")));
      cs.setInt(7,(Integer.parseInt((form.getHdnUserTblPk()))));
      
      dbConnBean.executeCallableStatement(cs);
      user_tbl_pk = cs.getInt(1);
      logger.debug(" Edit User Number : "+user_tbl_pk);            
    }catch (SQLException sqle) {
      logger.error("SQLException occurred in editUser");
      logger.error( sqle.toString() );
      throw sqle;
    }catch( Exception e ){
      logger.error("Exception occurred in editUser");
      logger.error( e.toString() );
      throw e;
    }finally{
      dbConnBean.free(cs); 
    }
  }


  public static void deleteUser( DataSource ds , String frm_user_tbl_pk ) 
          throws SQLException , Exception {
    
    CallableStatement cs = null;
    DBConnection dbConnBean = null;
    int user_tbl_pk = 0;
    String query = "{? = call sp_del_user(?)}";
    
    try {
      dbConnBean = new DBConnection(ds);
      cs = dbConnBean.prepareCall(query);
      cs.registerOutParameter(1,Types.INTEGER);
      cs.setInt(2,Integer.parseInt(frm_user_tbl_pk));
      dbConnBean.executeCallableStatement(cs);
      user_tbl_pk = cs.getInt(1);
      logger.debug(" User Deleted : "+user_tbl_pk);            
    }catch (SQLException sqle) {
      logger.error("SQLException occurred in deleteUser");
      logger.error( sqle.toString() );
      throw sqle;
    }catch( Exception e ){
      logger.error("Exception occurred in deleteUser");
      logger.error( e.toString() );
      throw e;
    }finally{
      dbConnBean.free(cs); 
    }
  }


  public static ArrayList listUsers( DataSource ds , UserListForm form , int numberOfRecords ) 
          throws SQLException , Exception {
    
    DBConnection dbConnBean = null;
    ResultSet rs = null;
    ArrayList users = new ArrayList();
    UserListBean user = null;
    StringBuffer query = new StringBuffer("select ut.user_tbl_pk , ut.user_id , ut.user_name , ut.user_status , ut.dept_tbl_fk , dt.dept_id from user_tbl ut , dept_tbl dt WHERE 1=1 AND ut.dept_tbl_fk=dt.dept_tbl_pk");
    
    int pageNumber = 1;
    int pageCount = 1;
    int startIndex = 1;
    int endIndex = 1;
    
    if( !form.getCboSearchDepartments().equals("Select") ){
      query.append(" AND dt.dept_id='"+form.getCboSearchDepartments()+"'");
    }
    if( form.getTxtSearchUserID().trim().length()!=0 ){
      query.append(" AND ut.user_id LIKE '"+form.getTxtSearchUserID().replace('*','%')+"'");
    }
    query.append(" ORDER BY ut.dept_tbl_fk , ut.user_tbl_pk");

    try {
      dbConnBean = new DBConnection(ds);
      rs = dbConnBean.executeQuery(query.toString(),ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
      if( rs != null ){
        pageNumber = Integer.parseInt(form.getHdnPageNumber());
        pageCount = GeneralUtil.getPageCount(rs, numberOfRecords);
        
        if (pageNumber > pageCount) {
          pageNumber = pageCount;
        }
        startIndex = (pageNumber * numberOfRecords) - numberOfRecords;
        endIndex = (startIndex + numberOfRecords) - 1;
        if (startIndex > 0) {
          rs.relative(startIndex);
        }
      
        while( rs.next() ){
          user = new UserListBean();
          user.setUserTblPk(Integer.toString(rs.getInt("user_tbl_pk")));
          user.setUserID(rs.getString("user_id"));
          user.setUserName(rs.getString("user_name"));
          user.setUserType((rs.getString("user_status").equalsIgnoreCase("A")?"Admin":"Supervisor"));
          user.setDepartmentID(rs.getString("dept_id"));
          users.add(user);
          startIndex++;
          if (startIndex > endIndex) {
            break;
          }
        }
        users.add(0,new Integer(pageCount));
      }
    }catch (SQLException sqle) {
      logger.error("SQLException occurred in listDepartments");
      logger.error( sqle.toString() );
      throw sqle;
    }catch( Exception e ){
      logger.error("Exception occurred in listDepartments");
      logger.error( e.toString() );
      throw e;
    }finally{
      dbConnBean.free(rs); 
    }
    return users;
  }

  public static UserNewEditForm getUserDetails( DataSource ds , String user_tbl_pk ) 
          throws SQLException , Exception {
    
    DBConnection dbConnBean = null;
    ResultSet rs = null;
    String query = " select ut.user_tbl_pk , ut.user_id , ut.user_name , ut.user_password , ut.user_status , dt.dept_id from user_tbl ut , dept_tbl dt WHERE ut.user_tbl_pk="+user_tbl_pk+" AND ut.dept_tbl_fk=dt.dept_tbl_pk";
    
    UserNewEditForm userNewEditForm = null; 
    
    try {
      dbConnBean = new DBConnection(ds);
      rs = dbConnBean.executeQuery(query);
      if( rs != null ){
        while( rs.next() ){
          userNewEditForm = new UserNewEditForm();
          userNewEditForm.setHdnUserTblPk(user_tbl_pk);
          userNewEditForm.setTxtUserID(rs.getString("user_id"));
          userNewEditForm.setTxtUserName(rs.getString("user_name"));
          userNewEditForm.setTxtUserPassword(rs.getString("user_password"));
          userNewEditForm.setTxtConfirmPassword(rs.getString("user_password"));
          userNewEditForm.setRadStatus((rs.getString("user_status").equals("A"))?"1":"2");
          userNewEditForm.setCboDepartments(rs.getString("dept_id"));
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
    return userNewEditForm;
  }

 
}