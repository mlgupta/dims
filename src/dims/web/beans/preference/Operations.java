package dims.web.beans.preference;

import dims.web.beans.department.DepartmentListBean;
import dims.web.general.DBConnection;
import dims.web.general.DIMSConstants;
import dims.web.general.PgSQLArray;

import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

import java.util.ArrayList;
import javax.sql.DataSource;

import org.apache.log4j.Logger;

public final class Operations  {

  static Logger logger = Logger.getLogger(DIMSConstants.LOGGER.toString());
  
  public static ArrayList listDepartments( DataSource ds ) 
          throws SQLException , Exception {
    
    DBConnection dbConnBean = null;
    ResultSet rs = null;
    ArrayList departments = new ArrayList();
    DepartmentListBean department = null;
    StringBuffer query = new StringBuffer("select dept_tbl_pk , dept_id , case when dept_control_sequence IS NOT NULL then dept_control_sequence else -1 end as dept_control_sequence , dept_enable from dept_tbl");
    query.append(" ORDER BY dept_tbl_pk");
    //logger.debug(query);
    try {
      dbConnBean = new DBConnection(ds);
      rs = dbConnBean.executeQuery(query.toString(),ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
      if( rs != null ){
        while( rs.next() ){
          department = new DepartmentListBean();
          department.setDepartmentTblPk(String.valueOf(rs.getInt("dept_tbl_pk")));
          department.setDepartmentId(rs.getString("dept_id"));
          /*int dept_control_seq = rs.getInt("dept_control_sequence");
          String dept_Control_Seq = String.valueOf(dept_control_seq);*/
          department.setDepartmentCntrlSeq(String.valueOf(rs.getInt("dept_control_sequence")));
          department.setDeptEnabled(new Boolean(rs.getBoolean("dept_enable")));
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

  public static ArrayList listDepartmentSequences( DataSource ds ) 
          throws SQLException , Exception {
    
    DBConnection dbConnBean = null;
    ResultSet rs = null;
    ArrayList departments = new ArrayList();
    DepartmentListBean department = null;
    int counter = 0;
    //StringBuffer query = new StringBuffer("select dept_control_sequence from dept_control_seq_vw");
    StringBuffer query = new StringBuffer("select count(dept_tbl_pk) from dept_tbl");
    
    try {
      dbConnBean = new DBConnection(ds);
      rs = dbConnBean.executeQuery(query.toString(),ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
      if( rs != null ){
        while( rs.next() ){
          counter = rs.getInt("count");
        }
        for( int index = 1; index <= counter; index++ ){
          department = new DepartmentListBean();
          department.setDepartmentCntrlSeq(String.valueOf(index));
          departments.add(department);
        }
        department = new DepartmentListBean();
        department.setDepartmentCntrlSeq("Select Control Sequence");
        departments.add(0,department);
      }
    }catch (SQLException sqle) {
      logger.error("SQLException occurred in listDepartmentSequences");
      logger.error( sqle.toString() );
      throw sqle;
    }catch( Exception e ){
      logger.error("Exception occurred in listDepartmentSequences");
      logger.error( e.toString() );
      throw e;
    }finally{
      dbConnBean.free(rs); 
    }
    return departments;
  }

  private static ArrayList listDepartmentPks( DataSource ds ) 
          throws SQLException , Exception {
    
    DBConnection dbConnBean = null;
    ResultSet rs = null;
    ArrayList departments = new ArrayList();
    StringBuffer query = new StringBuffer("select dept_tbl_pk from dept_tbl order by dept_tbl_pk;");
    
    try {
      dbConnBean = new DBConnection(ds);
      rs = dbConnBean.executeQuery(query.toString(),ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
      if( rs != null ){
        while( rs.next() ){
          departments.add(new Integer(rs.getInt("dept_tbl_pk")));
        }
      }
    }catch (SQLException sqle) {
      logger.error("SQLException occurred in listDepartmentPks");
      logger.error( sqle.toString() );
      throw sqle;
    }catch( Exception e ){
      logger.error("Exception occurred in listDepartmentPks");
      logger.error( e.toString() );
      throw e;
    }finally{
      dbConnBean.free(rs); 
    }
    return departments;
  }
  
  
  private static void setDeptSequences( DataSource ds , int [] deptTblPk , 
    int [] controlFlow , boolean [] enableDept) 
    throws SQLException , Exception  {
  
    CallableStatement cs = null;
    DBConnection dbConnBean = null;
    String query = "{? = call sp_upd_dept_sequence(?,?,?)}";    
    int returnVal = 0;

    try {

      StringBuffer deptTblArray = new StringBuffer("{");
      StringBuffer controlFlowArray = new StringBuffer("{");
      StringBuffer enableDeptArray = new StringBuffer("{");
      
      for( int index = 0; index < deptTblPk.length; index++ ){
        if( index < deptTblPk.length -1 ){
          deptTblArray.append( deptTblPk[index]+"," );
          controlFlowArray.append(controlFlow[index]+",");
          enableDeptArray.append( enableDept[index]+",");
        }else{
          deptTblArray.append( deptTblPk[index] );
          controlFlowArray.append(controlFlow[index]);
          enableDeptArray.append(enableDept[index]);
        }
      }
      deptTblArray.append("}");
      controlFlowArray.append("}");
      enableDeptArray.append("}");
      
      logger.debug(" DeptTblArray     :  "+deptTblArray);
      logger.debug(" ControlFlowArray :  "+controlFlowArray);
      logger.debug(" EnableDeptArray  :  "+enableDeptArray);

      dbConnBean = new DBConnection(ds);
      cs = dbConnBean.prepareCall(query);
      cs.registerOutParameter(1,Types.INTEGER);
      cs.setArray(2,new PgSQLArray(deptTblArray.toString(),Types.INTEGER));
      cs.setArray(3,new PgSQLArray(controlFlowArray.toString(),Types.INTEGER));
      cs.setArray(4,new PgSQLArray(enableDeptArray.toString(),Types.BOOLEAN));
      dbConnBean.executeCallableStatement(cs);
      returnVal = cs.getInt(1);
      logger.debug(" Return Value : "+returnVal);     
    }catch (SQLException sqle) {
      logger.error("SQLException occurred in setDeptSequences");
      logger.error( sqle.toString() );
      throw sqle;
    }catch( Exception e ){
      logger.error("Exception occurred in setDeptSequences");
      logger.error( e.toString() );
      throw e;
    }finally{
      dbConnBean.free(cs); 
    }
    
  }
  
  private static ArrayList getUncheckedDepts( String [] chkEnableDept , 
    String[] hdnDeptTblPk ) throws SQLException , Exception {
    
    ArrayList unCheckedDeptsArr = new ArrayList();
    int chkEnableCnt = ( chkEnableDept == null )?0:chkEnableDept.length;
    int hdnDeptTblPkCnt = ( hdnDeptTblPk == null )?0:hdnDeptTblPk.length;
    boolean found = false;
    
    for( int i = 0; i < hdnDeptTblPkCnt; i++){
      found = false;
      for( int j =0; j < chkEnableCnt; j++ ){
        if( hdnDeptTblPk[i].equals(chkEnableDept[j]) ){
          found = true;
          break;
        }
      }
      if( !found ){
        unCheckedDeptsArr.add(0,hdnDeptTblPk[i]);
      }
    }
    
    return unCheckedDeptsArr;
  }
  
  public static void setDeptSequences( DataSource ds , String[] chkEnableDept , 
      String [] cboControlFlow , String[] hdnDeptTblPk , String[] txtDeptID )
      throws SQLException , Exception {
      
      ArrayList deptTblPks = getUncheckedDepts(chkEnableDept,hdnDeptTblPk);
      int numberOfDepts = (chkEnableDept == null)?0:chkEnableDept.length;
      
      for( int index = 0; index < deptTblPks.size(); index++ ){
        logger.debug(" deptTblPks.get("+index+") : "+((String)deptTblPks.get(index)));
      }
      
      int [] newhdnDeptTblPk = new int[hdnDeptTblPk.length];
      int [] newcboControlFlow = new int[hdnDeptTblPk.length];
      boolean [] newchkEnableDept = new boolean[hdnDeptTblPk.length];

      int count = 0;
      for( int index = 0; index < deptTblPks.size(); index++  ){
        newhdnDeptTblPk[index] = Integer.parseInt(((String)deptTblPks.get(index)));
        newcboControlFlow[index] = -1;
        newchkEnableDept[index] = false;
        count++;
      }
      for( int index = 0; index < numberOfDepts; index++  ){
        newhdnDeptTblPk[count+index] = Integer.parseInt(chkEnableDept[index]);
        newcboControlFlow[count+index] = Integer.parseInt(cboControlFlow[index]);
        newchkEnableDept[count+index] = true;
      }
      
      
      for( int index = 0; index < newhdnDeptTblPk.length; index++ ){
        logger.debug("newhdnDeptTblPk["+index+"] : "+newhdnDeptTblPk[index]);
        logger.debug("newcboControlFlow["+index+"] : "+newcboControlFlow[index]);
        logger.debug("newchkEnableDept["+index+"] : "+newchkEnableDept[index]);
      }
      
     setDeptSequences(ds,newhdnDeptTblPk,newcboControlFlow,newchkEnableDept);
  }
}