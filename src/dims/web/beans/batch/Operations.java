package dims.web.beans.batch;

import dims.web.actionforms.batch.BatchListForm;
import dims.web.actionforms.batch.BatchNewEditForm;
import dims.web.beans.binders.BinderListBean;
import dims.web.general.DBConnection;
import dims.web.general.DIMSConstants;
import dims.web.general.GeneralUtil;
import dims.web.general.DateUtil;
import dims.web.general.PgSQLArray;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import javax.sql.DataSource;
import org.apache.log4j.Logger;

public final class Operations   {

  static Logger logger = Logger.getLogger(DIMSConstants.LOGGER.toString());
  
  public static void addBatch( DataSource ds , BatchNewEditForm form, StringBuffer binderString, String intUserTblPk) 
          throws SQLException , Exception {
    
    CallableStatement cs = null;
    DBConnection dbConnBean = null;
    int returnVal = 0;
    String query = "{? = call sp_ins_batch(?,?,?,?,?)}";
    
    try {
      dbConnBean = new DBConnection(ds);
      cs = dbConnBean.prepareCall(query);
      cs.registerOutParameter(1,Types.INTEGER);
      cs.setString(2,null);
      cs.setInt(3,Integer.parseInt(form.getCboLocations()));
      cs.setString(4,null);
      cs.setInt(5,Integer.parseInt(intUserTblPk));
      cs.setArray(6,new PgSQLArray(binderString.toString(),Types.VARCHAR));
      dbConnBean.executeCallableStatement(cs);
      returnVal = cs.getInt(1);
      logger.debug(" Return Value : "+returnVal);            
    }catch (SQLException sqle) {
      logger.error("SQLException occurred in addBatch");
      logger.error( sqle.toString() );
      throw sqle;
    }catch( Exception e ){
      logger.error("Exception occurred in addBatch");
      logger.error( e.toString() );
      throw e;
    }finally{
      dbConnBean.free(cs); 
    }
  }


  public static void saveEditBatch( DataSource ds , BatchNewEditForm form, StringBuffer binderString, String intUserTblPk ) throws SQLException , Exception {
    
    CallableStatement cs = null;
    DBConnection dbConnBean = null;
    int returnVal = 0;
    String query = "{? = call sp_upd_batch(?,?,?,?,?,?)}";
    
    try {
      dbConnBean = new DBConnection(ds);
      cs = dbConnBean.prepareCall(query);
      cs.registerOutParameter(1,Types.INTEGER);
      cs.setInt(2,Integer.parseInt(form.getTxtBatchNumber()));
      System.out.println("Batch Number : " + form.getTxtBatchNumber());
      cs.setString(3,null);
      cs.setInt(4,Integer.parseInt(form.getCboLocations()));
      System.out.println("Location : " + form.getCboLocations());
      cs.setString(5,null);
      cs.setInt(6,Integer.parseInt(intUserTblPk));
      System.out.println("User : " + intUserTblPk);
      cs.setArray(7,new PgSQLArray(binderString.toString(),Types.VARCHAR));
      dbConnBean.executeCallableStatement(cs);
      returnVal = cs.getInt(1);
      logger.debug(" Return Value : "+returnVal);
    }catch (SQLException sqle) {
      logger.error("SQLException occurred in editBatch");
      logger.error( sqle.toString() );
      throw sqle;
    }catch( Exception e ){
      logger.error("Exception occurred in editBatch");
      logger.error( e.toString() );
      throw e;
    }finally{
      dbConnBean.free(cs); 
    }  
  }

  public static void deleteBatch( DataSource ds , String frm_batch_tbl_pk ) 
          throws SQLException , Exception {
    
    CallableStatement cs = null;
    DBConnection dbConnBean = null;
    int returnValue = 0;
    String query = "{? = call sp_del_batch(?)}";
    
    try {
      dbConnBean = new DBConnection(ds);
      cs = dbConnBean.prepareCall(query);
      cs.registerOutParameter(1,Types.INTEGER);
      cs.setInt(2,Integer.parseInt(frm_batch_tbl_pk));
      dbConnBean.executeCallableStatement(cs);
      returnValue = cs.getInt(1);
      logger.debug(" Return Value : "+ returnValue);            
    }catch (SQLException sqle) {
      logger.error("SQLException occurred in deleteBatch");
      logger.error( sqle.toString() );
      throw sqle;
    }catch( Exception e ){
      logger.error("Exception occurred in deleteBatch");
      logger.error( e.toString() );
      throw e;
    }finally{
      dbConnBean.free(cs); 
    }
  }

  public static ArrayList listBatchs( DataSource ds , BatchListForm form , int numberOfRecords ) 
          throws SQLException , Exception {
    String DATE_FORMAT="MM/dd/yyyy";     
    DBConnection dbConnBean = null;
    ResultSet rs = null;
    ArrayList batchs = new ArrayList();
    BatchListBean batch = null;
    String createFromDate=null;
    String createToDate=null;
    //StringBuffer query = new StringBuffer("select batch_tbl_pk, batch_number, location_city, record_updated_on, dept_name from batch_list_vw WHERE 1=1 ");
    
    StringBuffer query = new StringBuffer("SELECT bt.batch_tbl_pk, bt.batch_number, bt.batch_desc, lt.location_city, bt.consignment_number, bt.record_updated_by, lt.location_tbl_pk, split_part((date_trunc('day'::text, bt.record_updated_on))::text, ' '::text, 1) AS record_updated_on, ");
    query.append("(SELECT dt.dept_name FROM dept_tbl dt WHERE (dt.dept_tbl_pk = bt.dept_tbl_fk) ) AS dept_name FROM batch_tbl bt, location_tbl lt WHERE 1=1 AND bt.location_tbl_fk = lt.location_tbl_pk");
    int pageNumber = 1;
    int pageCount = 1;
    int startIndex = 1;
    int endIndex = 1;
    
    if((!form.getCboSearchLocations().equals("-1")) && (!form.getCboSearchLocations().equals("Select"))){
      query.append(" AND location_tbl_pk="+form.getCboSearchLocations());
    }
    if( form.getTxtSearchBatchNumber().trim().length()!=0 ){
      query.append(" AND batch_number ="+form.getTxtSearchBatchNumber().trim());
    }
    
    if((form.getTxtSearchBatchFromDate()!=null) ||(form.getTxtSearchBatchToDate()!=null)){
      if((form.getTxtSearchBatchFromDate().length()>0) && (form.getTxtSearchBatchToDate().length()>0)){
        createFromDate=form.getTxtSearchBatchFromDate();
        createToDate=form.getTxtSearchBatchToDate();
        query.append(" AND record_updated_on between "+ "'" +createFromDate + "'" + " and " + "'" + createToDate + "'");
      }else{ 
      
        if((form.getTxtSearchBatchFromDate().length()>0)){
          createFromDate=form.getTxtSearchBatchFromDate();
          query.append(" AND record_updated_on > "+ "'" +createFromDate + "'");
        }
        
        if((form.getTxtSearchBatchToDate().length()>0)){
          createToDate=form.getTxtSearchBatchToDate();
          query.append(" AND record_updated_on < "+ "'" + createToDate + "'");
        }
  
      }
    }
    query.append(" ORDER BY bt.batch_tbl_pk");
    logger.debug(query);
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
          batch = new BatchListBean();
          batch.setBatchTblPk(Integer.toString(rs.getInt("batch_tbl_pk")));
          logger.debug("batch.getBatchTblPk() : "+batch.getBatchTblPk());
          batch.setBatchNumber(rs.getString("batch_number"));
          logger.debug("batch.getBatchNumber() : "+batch.getBatchNumber());
          batch.setLocation(rs.getString("location_city"));
          logger.debug("batch.getLocation() : "+batch.getLocation());
          batch.setBatchDate(DateUtil.format(DateUtil.parse(rs.getString("record_updated_on").toString(), "yyyy-MM-dd"), "dd-MM-yyyy"));
          logger.debug("batch.getBatchDate() : "+batch.getBatchDate());
          if(rs.getString("dept_name")!=null){
            batch.setDepartment(rs.getString("dept_name"));
            logger.debug("batch.getDepartment() : "+batch.getDepartment());
          }
          
          batchs.add(batch);
          startIndex++;
          if (startIndex > endIndex) {
            break;
          }
        }
        batchs.add(0,new Integer(pageCount));
      }
    }catch (SQLException sqle) {
      logger.error("SQLException occurred in listBatchs");
      logger.error( sqle.toString() );
      throw sqle;
    }catch( Exception e ){
      logger.error("Exception occurred in listBatchs");
      logger.error( e.toString() );
      throw e;
    }finally{
      dbConnBean.free(rs); 
    }
    return batchs;
  }

  public static String listBatchsForAjax( DataSource ds , int locationTblPK ) 
          throws SQLException , Exception {
    
    DBConnection dbConnBean = null;
    ResultSet rs = null;
    StringBuffer batchs = new StringBuffer("Select,-1|");
    
    String query = "select batch_tbl_pk , batch_number from batch_tbl WHERE 1=1 AND location_tbl_fk="+locationTblPK+" ORDER BY batch_tbl_pk";
    
    try {
      dbConnBean = new DBConnection(ds);
      rs = dbConnBean.executeQuery(query,ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
      if( rs != null ){
        while( rs.next() ){
          if( ! rs.isLast() ){
            batchs.append(rs.getString("batch_number")+","+Integer.toString(rs.getInt("batch_tbl_pk"))+"|");
          }else{
            batchs.append(rs.getString("batch_number")+","+Integer.toString(rs.getInt("batch_tbl_pk")));
          }
        }
      }
    }catch (SQLException sqle) {
      logger.error("SQLException occurred in listBatchs");
      logger.error( sqle.toString() );
      throw sqle;
    }catch( Exception e ){
      logger.error("Exception occurred in listBatchs");
      logger.error( e.toString() );
      throw e;
    }finally{
      dbConnBean.free(rs); 
    }
    return batchs.toString();
  }


  public static ArrayList listBatchs( DataSource ds , int locationTblPK ) 
          throws SQLException , Exception {
    
    DBConnection dbConnBean = null;
    ResultSet rs = null;
    ArrayList batchs = new ArrayList();
    BatchListBean batch = null;
    
    String query = "select batch_tbl_pk , batch_number from batch_tbl WHERE 1=1 AND location_tbl_fk="+locationTblPK+" ORDER BY batch_tbl_pk";
    
    try {
      dbConnBean = new DBConnection(ds);
      rs = dbConnBean.executeQuery(query,ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
      if( rs != null ){
        while( rs.next() ){
          batch = new BatchListBean();
          batch.setBatchTblPk(Integer.toString(rs.getInt("batch_tbl_pk")));
          batch.setBatchNumber(rs.getString("batch_number"));
          batchs.add(batch);
        }
        batch = new BatchListBean();
        batch.setBatchTblPk("-1");
        batch.setBatchNumber("Select");
        batchs.add(0,batch);
      }
    }catch (SQLException sqle) {
      logger.error("SQLException occurred in listBatchs");
      logger.error( sqle.toString() );
      throw sqle;
    }catch( Exception e ){
      logger.error("Exception occurred in listBatchs");
      logger.error( e.toString() );
      throw e;
    }finally{
      dbConnBean.free(rs); 
    }
    return batchs;
  }

  public static BatchNewEditForm getBatchDetails( DataSource ds, String batch_tbl_pk ) 
          throws SQLException , Exception {
    
    DBConnection dbConnBean = null;
    ResultSet rs = null;
    String query = " select batch_number , location_tbl_fk, split_part((date_trunc('day'::text, record_updated_on))::text,' '::text, 1) AS record_updated_on from batch_tbl WHERE batch_tbl_pk="+batch_tbl_pk;
    
    BatchNewEditForm batchNewEditForm = null; 
    logger.debug("Query : " + query);
    try {
      dbConnBean = new DBConnection(ds);
      rs = dbConnBean.executeQuery(query);
      
      if( rs != null ){
        while( rs.next() ){
          batchNewEditForm = new BatchNewEditForm();
          batchNewEditForm.setHdnBatchTblPk(batch_tbl_pk);
          batchNewEditForm.setTxtBatchNumber(rs.getString("batch_number"));
          logger.debug("Batch Number : " + rs.getString("batch_number"));
          batchNewEditForm.setCboLocations(String.valueOf(rs.getInt("location_tbl_fk")));
          logger.debug("Batch Number : " + String.valueOf(rs.getInt("location_tbl_fk")));
          //batchNewEditForm.setDtBatchDate(rs.getString("record_updated_on"));
          batchNewEditForm.setDtBatchDate(DateUtil.format(DateUtil.parse(rs.getString("record_updated_on").toString(), "yyyy-MM-dd"), "dd-MM-yyyy"));
          logger.debug("Batch Number : " + rs.getString("record_updated_on"));
        }
      }
    }catch (SQLException sqle) {
      logger.error("SQLException occurred in getBatchDetails");
      logger.error(sqle.toString());
      throw sqle;
    }catch (Exception e) {
      logger.error("Exception occurred in getBatchDetails");
      logger.error(e.toString());
      throw e;
    }finally{ 
      dbConnBean.free(rs);
    }
    return batchNewEditForm;
  }
 
 public static void submitToBatchProcess( DataSource ds, String batchTblPK, String intUserTblPk) 
          throws SQLException , Exception {
    
    CallableStatement cs = null;
    DBConnection dbConnBean = null;
    int returnVal = 0;
    String query = "{? = call sp_ins_batch_process(?,?,?)}";
    
    try {
      dbConnBean = new DBConnection(ds);
      cs = dbConnBean.prepareCall(query);
      cs.registerOutParameter(1,Types.INTEGER);
      cs.setInt(2,Integer.parseInt(batchTblPK));
      cs.setInt(3,Integer.parseInt(intUserTblPk));
      cs.setBoolean(4,true);
      dbConnBean.executeCallableStatement(cs);
      returnVal = cs.getInt(1);
      logger.debug(" Return Value : "+returnVal);            
    }catch (SQLException sqle) {
      logger.error("SQLException occurred in submitToBatchProcess");
      logger.error( sqle.toString() );
      throw sqle;
    }catch( Exception e ){
      logger.error("Exception occurred in submitToBatchProcess");
      logger.error( e.toString() );
      throw e;
    }finally{
      dbConnBean.free(cs); 
    }
  }
  
  public static ArrayList getBinders(DataSource ds, String batch_tbl_fk) 
          throws SQLException , Exception {
    
    DBConnection dbConnBean = null;
    ResultSet rs = null;
    String query = " select bt.binder_tbl_pk, bt.binder_prefix_tbl_fk, bt.binder_number, bt.binder_year from binder_tbl bt where 1=1 and batch_tbl_fk="+batch_tbl_fk;
    ArrayList binders = new ArrayList();
    BinderNewEditBean binderNewEditBean = null;
    //int pageNumber = 1;
    //int pageCount = 1;
    //int startIndex = 1;
    //int endIndex = 1;
    
    try {
      dbConnBean = new DBConnection(ds);
      rs = dbConnBean.executeQuery(query.toString(),ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
      if( rs != null ){
        //pageNumber = Integer.parseInt(formPageNumber);
        //pageCount = GeneralUtil.getPageCount(rs, numberOfRecords);
        
        //if (pageNumber > pageCount) {
          //pageNumber = pageCount;
        //}
        //startIndex = (pageNumber * numberOfRecords) - numberOfRecords;
        //endIndex = (startIndex + numberOfRecords) - 1;
        //if (startIndex > 0) {
          //rs.relative(startIndex);
        //}
        while( rs.next() ){
          String binderNumber=null;
          String startNumber=null;
          String endNumber=null;
          int indexTo=0;
          binderNewEditBean = new BinderNewEditBean();
          binderNewEditBean.setCboBinderPrefix(rs.getString("binder_prefix_tbl_fk"));
          binderNumber = rs.getString("binder_number");
          indexTo=binderNumber.indexOf("to");
          startNumber = binderNumber.substring(0,indexTo);
          endNumber = binderNumber.substring(indexTo+2);
          binderNewEditBean.setTxtBinderStartNumber(startNumber);
          binderNewEditBean.setTxtBinderEndNumber(endNumber);
          binderNewEditBean.setTxtBinderTblPK(String.valueOf(rs.getInt("binder_tbl_pk")));
          binderNewEditBean.setCboYear(rs.getString("binder_year"));
          binders.add(binderNewEditBean);
          //startIndex++;
          //if (startIndex > endIndex) {
            //break;
          //}
        }
        //binders.add(0,new Integer(pageCount));
      }
    }catch (SQLException sqle) {
      logger.error("SQLException occurred in getBatchProcessDetails");
      logger.error(sqle.toString());
      throw sqle;
    }catch (Exception e) {
      logger.error("Exception occurred in getBatchProcessDetails");
      logger.error(e.toString());
      throw e;
    }finally{
      dbConnBean.free(rs);
    }
    return binders;
  }
}