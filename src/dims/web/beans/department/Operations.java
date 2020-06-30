package dims.web.beans.department;

import dims.web.actionforms.department.DepartmentListForm;
import dims.web.actionforms.department.DepartmentNewEditForm;
import dims.web.beans.users.UserProfile;
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
  
  public static void addDepartment( DataSource ds , DepartmentNewEditForm form ) 
          throws SQLException , Exception {
    
    CallableStatement cs = null;
    DBConnection dbConnBean = null;
    int dept_tbl_pk = 0;
    String query = "{? = call sp_ins_dept(?,?,?,?,?)}";
    
    try {
      dbConnBean = new DBConnection(ds);
      cs = dbConnBean.prepareCall(query);
      cs.registerOutParameter(1,Types.INTEGER);
      cs.setString(2,form.getTxtDepartmentId());
      cs.setString(3,form.getTxtDepartmentName());
      cs.setString(4,form.getTxaDescription());
      cs.setInt(5,-1);
      cs.setBoolean(6,true);
      dbConnBean.executeCallableStatement(cs);
      dept_tbl_pk = cs.getInt(1);
      logger.debug(" New Department Number : "+dept_tbl_pk);            
    }catch (SQLException sqle) {
      logger.error("SQLException occurred in addDepartment");
      logger.error( sqle.toString() );
      throw sqle;
    }catch( Exception e ){
      logger.error("Exception occurred in addDepartment");
      logger.error( e.toString() );
      throw e;
    }finally{
      dbConnBean.free(cs); 
    }
  }


  public static void editDepartment( DataSource ds , DepartmentNewEditForm form ) 
          throws SQLException , Exception {
    
    CallableStatement cs = null;
    DBConnection dbConnBean = null;
    int dept_tbl_pk = 0;
    String query = "{? = call sp_upd_dept(?,?,?,?,?,?)}";
    
    try {
      dbConnBean = new DBConnection(ds);
      cs = dbConnBean.prepareCall(query);
      cs.registerOutParameter(1,Types.INTEGER);
      cs.setInt(2,Integer.parseInt(form.getTxtDepartmentNo()));
      cs.setString(3,form.getTxtDepartmentId());
      cs.setString(4,form.getTxtDepartmentName());
      cs.setString(5,form.getTxaDescription());
      cs.setInt(6,Integer.parseInt(form.getHdnDeptControlSeq()));
      cs.setBoolean(7,form.isHdnBoolDeptEnable());
      dbConnBean.executeCallableStatement(cs);
      dept_tbl_pk = cs.getInt(1);
      logger.debug(" Department Number : "+dept_tbl_pk);            
    }catch (SQLException sqle) {
      logger.error("SQLException occurred in editDepartment");
      logger.error( sqle.toString() );
      throw sqle;
    }catch( Exception e ){
      logger.error("Exception occurred in editDepartment");
      logger.error( e.toString() );
      throw e;
    }finally{
      dbConnBean.free(cs); 
    }
  }


  public static void deleteDepartment( DataSource ds , String frm_dept_tbl_pk ) 
          throws SQLException , Exception {
    
    CallableStatement cs = null;
    DBConnection dbConnBean = null;
    int dept_tbl_pk = 0;
    String query = "{? = call sp_del_dept(?)}";
    
    try {
      dbConnBean = new DBConnection(ds);
      cs = dbConnBean.prepareCall(query);
      cs.registerOutParameter(1,Types.INTEGER);
      cs.setInt(2,Integer.parseInt(frm_dept_tbl_pk));
      dbConnBean.executeCallableStatement(cs);
      dept_tbl_pk = cs.getInt(1);
      logger.debug(" Department Number Deleted : "+dept_tbl_pk);
      if( dept_tbl_pk == -1 ){
        throw new SQLException("23503");
      }
    }catch (SQLException sqle) {
      logger.error("SQLException occurred in deleteDepartment");
      logger.error( sqle.toString() );
      throw sqle;
    }catch( Exception e ){
      logger.error("Exception occurred in deleteDepartment");
      logger.error( e.toString() );
      throw e;
    }finally{
      dbConnBean.free(cs); 
    }
  }


  public static ArrayList listDepartments( DataSource ds , DepartmentListForm form , int numberOfRecords ) 
          throws SQLException , Exception {
    
    DBConnection dbConnBean = null;
    ResultSet rs = null;
    ArrayList departments = new ArrayList();
    DepartmentListBean department = null;
    StringBuffer query = new StringBuffer("select dept_tbl_pk , dept_id , dept_name from dept_tbl WHERE 1=1");
    
    int pageNumber = 1;
    int pageCount = 1;
    int startIndex = 1;
    int endIndex = 1;
    
    if( form.getTxtDepartmentName().trim().length()!=0 ){
      query.append(" AND dept_name LIKE '"+form.getTxtDepartmentName().replace('*','%')+"'");
    }
    if( form.getTxtDepartmentID().trim().length()!=0 ){
      query.append(" AND dept_id LIKE '"+form.getTxtDepartmentID().replace('*','%')+"'");
    }
    query.append(" ORDER BY dept_tbl_pk");
    
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
          department = new DepartmentListBean();
          department.setDepartmentTblPk(Integer.toString(rs.getInt("dept_tbl_pk")));
          department.setDepartmentId(rs.getString("dept_id"));
          department.setDepartmentName(rs.getString("dept_name"));
          departments.add(department);
          startIndex++;
          if (startIndex > endIndex) {
            break;
          }
        }
        departments.add(0,new Integer(pageCount));
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
    return departments;
  }

  

  public static ArrayList listDepartments( DataSource ds ) 
          throws SQLException , Exception {
    
    DBConnection dbConnBean = null;
    ResultSet rs = null;
    ArrayList departments = new ArrayList();
    DepartmentListBean department = null;
    StringBuffer query = new StringBuffer("select dept_tbl_pk , dept_id from dept_tbl WHERE 1=1");
    query.append(" ORDER BY dept_tbl_pk");
    
    try {
      dbConnBean = new DBConnection(ds);
      rs = dbConnBean.executeQuery(query.toString(),ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
      if( rs != null ){
      
        while( rs.next() ){
          department = new DepartmentListBean();
          department.setDepartmentTblPk(String.valueOf(rs.getInt("dept_tbl_pk")));
          department.setDepartmentId(rs.getString("dept_id"));
          departments.add(department);
        }
        department = new DepartmentListBean();
        department.setDepartmentTblPk("-1");
        department.setDepartmentId("Select");
        departments.add(0,department);
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
    return departments;
  }

  
  public static ArrayList listDepartments( DataSource ds ,UserProfile userProfile) 
          throws SQLException , Exception {
    
    DBConnection dbConnBean = null;
    ResultSet rs = null;
    ArrayList departments = new ArrayList();
    DepartmentListBean department = null;
    StringBuffer query = null;
    if( userProfile.getUserStatus().equals("1") ){
      query = new StringBuffer("select dept_tbl_pk , dept_id from dept_tbl WHERE 1=1");
      query.append(" ORDER BY dept_tbl_pk");
    }else{
      department = new DepartmentListBean();
      department.setDepartmentTblPk(userProfile.getDeptFK());
      department.setDepartmentId(userProfile.getDeptID());
      departments.add(department);
      return departments;
    }
    
    try {
      dbConnBean = new DBConnection(ds);
      rs = dbConnBean.executeQuery(query.toString(),ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
      if( rs != null ){
      
        while( rs.next() ){
          department = new DepartmentListBean();
          department.setDepartmentTblPk(String.valueOf(rs.getInt("dept_tbl_pk")));
          department.setDepartmentId(rs.getString("dept_id"));
          departments.add(department);
        }
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
    return departments;
  }

  
  public static DepartmentNewEditForm getDepartmentDetails( DataSource ds , String dept_tbl_pk ) 
          throws SQLException , Exception {
    
    DBConnection dbConnBean = null;
    ResultSet rs = null;
    String query = " select dept_id , dept_name , dept_description , dept_control_sequence , dept_enable from dept_tbl where dept_tbl_pk="+dept_tbl_pk;
    
    DepartmentNewEditForm departmentNewEditForm = null; 
    
    try {
      dbConnBean = new DBConnection(ds);
      rs = dbConnBean.executeQuery(query);
      if( rs != null ){
        while( rs.next() ){
          departmentNewEditForm = new DepartmentNewEditForm();
          departmentNewEditForm.setTxtDepartmentNo(dept_tbl_pk);
          departmentNewEditForm.setTxtDepartmentId(rs.getString("dept_id"));
          departmentNewEditForm.setTxtDepartmentName(rs.getString("dept_name"));
          departmentNewEditForm.setTxaDescription(rs.getString("dept_description"));
          departmentNewEditForm.setHdnDeptControlSeq(Integer.toString(rs.getInt("dept_control_sequence")));
          departmentNewEditForm.setHdnBoolDeptEnable(rs.getBoolean("dept_enable"));
        }
      }
    }catch (SQLException sqle) {
      logger.error("SQLException occurred in getDepartmentDetails");
      logger.error(sqle.toString());
      throw sqle;
    }catch (Exception e) {
      logger.error("Exception occurred in getDepartmentDetails");
      logger.error(e.toString());
      throw e;
    }finally{
      dbConnBean.free(rs);
    }
    return departmentNewEditForm;
  }

 
}
