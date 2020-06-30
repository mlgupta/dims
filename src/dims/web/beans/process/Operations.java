package dims.web.beans.process;

import dims.web.actionforms.process.BatchProcessListForm;
import dims.web.general.DBConnection;
import dims.web.general.DIMSConstants;
import dims.web.general.DateUtil;
import dims.web.general.GeneralUtil;
import dims.web.general.PgSQLArray;
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
  
  public static ArrayList listBatchProcesses( DataSource ds, BatchProcessListForm form , int numberOfRecords )
          throws SQLException , Exception {
    
    DBConnection dbConnBean = null;
    ResultSet rs = null;
    ArrayList batches = new ArrayList();
    BatchProcessListBean batchProcessBean = null;
    StringBuffer query = new StringBuffer("select batch_process_tbl_pk,batch_tbl_fk,batch_number,batch_start_date,batch_end_date,is_processed,dept_id,dept_tbl_fk,percent_processed from batch_process_list_vw WHERE 1=1");
    
    int pageNumber = 1;
    int pageCount = 1;
    int startIndex = 1;
    int endIndex = 1;
    
    if( form.getTxtBatchNumber().trim().length()!=0 ){
      query.append(" AND batch_number LIKE '"+form.getTxtBatchNumber().replace('*','%')+"'");
    }
    if( form.getCboDepartmentId().trim().length()!=0 ){
      query.append(" AND dept_id = '"+form.getCboDepartmentId().replace('*','%')+"'");
    }
    if( form.getHdnBatchFromDate().trim().length()!=0 ){
      String startDate = null;
      String [] splitVals =form.getHdnBatchFromDate().split(" ");
      String [] splitVals1 = splitVals[0].split("/");
      for( int index =0; index < splitVals1.length; index++ ){
        logger.debug("splitVals1["+index+"]: "+splitVals1[index]);
      }
      startDate = splitVals1[2]+"-"+splitVals1[0]+"-"+splitVals1[1];
      
      query.append(" AND batch_start_date >= '"+startDate+"'");
    }
    if( form.getHdnBatchToDate().trim().length()!=0 ){
      String endDate = null;
      String [] splitVals =form.getHdnBatchToDate().split(" ");
      String [] splitVals1 = splitVals[0].split("/");
      for( int index =0; index < splitVals1.length; index++ ){
        logger.debug("splitVals1["+index+"]: "+splitVals1[index]);
      }
      endDate = splitVals1[2]+"-"+splitVals1[0]+"-"+splitVals1[1];
      query.append(" AND batch_end_date <= '"+endDate+"'");
    }
    query.append(" ORDER BY batch_process_tbl_pk");
    logger.debug("Query : \n");
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
          batchProcessBean = new BatchProcessListBean();
          batchProcessBean.setBatchProcessTblPk(String.valueOf(rs.getInt("batch_process_tbl_pk")));
          batchProcessBean.setBatchTblPk(String.valueOf(rs.getInt("batch_tbl_fk")));
          batchProcessBean.setBatchNumber(String.valueOf(rs.getInt("batch_number")));
          batchProcessBean.setBatchStartDate( DateUtil.format(DateUtil.parse(rs.getDate("batch_start_date").toString(),"yyyy-MM-dd"),"MM-dd-yyyy"));
          if( rs.getDate("batch_end_date") == null ){
            batchProcessBean.setBatchEndDate("");   
          }else{
            batchProcessBean.setBatchEndDate( DateUtil.format(DateUtil.parse(rs.getDate("batch_end_date").toString(),"yyyy-MM-dd"),"MM-dd-yyyy"));
          }
          batchProcessBean.setIsCompleted(new Boolean(rs.getBoolean("is_processed")));
          batchProcessBean.setDepartmentId(rs.getString("dept_id"));
          batchProcessBean.setPercentageCompleted((rs.getString("percent_processed")));
          batchProcessBean.setDepartmentTblFk(String.valueOf((rs.getInt("dept_tbl_fk"))));
          
          batches.add(batchProcessBean);
          startIndex++;
          if (startIndex > endIndex) {
            break;
          }
        }
        batches.add(0,new Integer(pageCount));
      }
    }catch (SQLException sqle) {
      logger.error("SQLException occurred in listBatchProcesses");
      logger.error( sqle.toString() );
      throw sqle;
    }catch( Exception e ){
      logger.error("Exception occurred in listBatchProcesses");
      logger.error( e.toString() );
      throw e;
    }finally{
      dbConnBean.free(rs); 
    }
    return batches;
              
  }

  public static ArrayList getBatchProcessDetails( DataSource ds , String batch_process_tbl_fk , String formPageNumber , int numberOfRecords ) 
          throws SQLException , Exception {
    
    DBConnection dbConnBean = null;
    ResultSet rs = null;
    String query = " select binder_process_tbl_pk,batch_process_tbl_fk,binder_tbl_fk,binder,assigned_to,is_processed,binder_process_comments from batch_process_edit_vw where batch_process_tbl_fk="+batch_process_tbl_fk;
    ArrayList binders = new ArrayList();
    BinderProcessListBean binderProcess = null;
    int pageNumber = 1;
    int pageCount = 1;
    int startIndex = 1;
    int endIndex = 1;
    
    try {
      dbConnBean = new DBConnection(ds);
      rs = dbConnBean.executeQuery(query.toString(),ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
      if( rs != null ){
        pageNumber = Integer.parseInt(formPageNumber);
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
          binderProcess = new BinderProcessListBean();
          binderProcess.setBinderProcessTblPk(String.valueOf(rs.getInt("binder_process_tbl_pk")));
          binderProcess.setBatchProcessTblFk(String.valueOf(rs.getInt("batch_process_tbl_fk")));
          binderProcess.setBinderTblFk(String.valueOf(rs.getInt("binder_tbl_fk")));
          binderProcess.setBinderName(rs.getString("binder"));
          binderProcess.setAssignedTo((rs.getString("assigned_to") == null)?" ":rs.getString("assigned_to"));
          binderProcess.setIsProcessed(new Boolean(rs.getBoolean("is_processed")));
          binderProcess.setBinderProcessComments((rs.getString("binder_process_comments") == null)?" ":rs.getString("binder_process_comments") );
          binders.add(binderProcess);
          startIndex++;
          if (startIndex > endIndex) {
            break;
          }
        }
        binders.add(0,new Integer(pageCount));
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


  public static void editBatchBinders( DataSource ds , boolean isBatchProcessed , 
          String [] binderProcessArray , int recordUpdatedBy ,  
          String batchProcessTblFk ) throws SQLException , Exception {
  

    CallableStatement cs = null;
    DBConnection dbConnBean = null;
    int returnValue = 0;
    String query = "{? = call sp_upd_binder_process(?,?,?,?)}";
    StringBuffer binderProcessArr = new StringBuffer("{");
    String rowVal = null;

    try {
      
      for( int index = 0; index < binderProcessArray.length; index++ ){
        
        rowVal = binderProcessArray[index].replace('|',',');
        
        if( rowVal.endsWith(",") ){
          rowVal = rowVal.substring(0,rowVal.length()-1);
        }else if( rowVal.trim().length() == 0 ){
          rowVal = "";
        }else{
          rowVal = rowVal.substring(0,rowVal.length());
        }
        
        logger.debug("rowVal : "+rowVal);
        
        if( index < binderProcessArray.length -1 ){
          binderProcessArr.append("\"{"+rowVal+"}\",");
        }else{
          binderProcessArr.append("\"{"+rowVal+"}\"");
        }
      }
      binderProcessArr.append("}");
  
      logger.debug(" Binder Process Arr :  "+binderProcessArr);
      
      dbConnBean = new DBConnection(ds);
      cs = dbConnBean.prepareCall(query);
      cs.registerOutParameter(1,Types.INTEGER);
      cs.setBoolean(2,isBatchProcessed);
      cs.setArray(3,new PgSQLArray(binderProcessArr.toString(),Types.VARCHAR));
      cs.setInt(4,recordUpdatedBy);
      cs.setInt(5,Integer.parseInt(batchProcessTblFk));
      dbConnBean.executeCallableStatement(cs);
      returnValue = cs.getInt(1);
      logger.debug(" Return Value : "+returnValue);            
    }catch (SQLException sqle) {
      logger.error("SQLException occurred in editBatchBinders");
      logger.error( sqle.toString() );
      throw sqle;
    }catch( Exception e ){
      logger.error("Exception occurred in editBatchBinders");
      logger.error( e.toString() );
      throw e;
    }finally{
      dbConnBean.free(cs); 
    }
  
  }

  public static ArrayList listRecords( DataSource ds , String binder_process_tbl_pk )
          throws SQLException , Exception {
    
    DBConnection dbConnBean = null;
    ResultSet rs = null;
    ArrayList records = new ArrayList();
    RecordListBean recordProcessBean = null;
    StringBuffer query = new StringBuffer("select record_tbl_pk,binder_tbl_fk,record_number,is_record_available,record_desc from record_list_vw ");
    query.append(" WHERE binder_tbl_fk =");
    query.append(" (select bt.binder_tbl_fk from binder_process_tbl bt where bt.binder_process_tbl_pk="+binder_process_tbl_pk+")");
    query.append("ORDER BY record_tbl_pk");

    logger.debug("Query : \n");
    logger.debug(query);
    
    try {
      dbConnBean = new DBConnection(ds);
      rs = dbConnBean.executeQuery(query.toString(),ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
      if( rs != null ){
      
        while( rs.next() ){
          recordProcessBean = new RecordListBean();
          recordProcessBean.setRecordTblPK( String.valueOf(rs.getInt("record_tbl_pk")));
          recordProcessBean.setBinderTblFk(String.valueOf(rs.getInt("binder_tbl_fk")));
          recordProcessBean.setRecordNumber(rs.getString("record_number"));
          recordProcessBean.setIsAvailable(new Boolean(rs.getBoolean("is_record_available")));
          recordProcessBean.setRecordComments((rs.getString("record_desc") == null)?"":rs.getString("record_desc"));
          
          records.add(recordProcessBean);
        }
      }
    }catch (SQLException sqle) {
      logger.error("SQLException occurred in listRecords");
      logger.error( sqle.toString() );
      throw sqle;
    }catch( Exception e ){
      logger.error("Exception occurred in listRecords");
      logger.error( e.toString() );
      throw e;
    }finally{
      dbConnBean.free(rs); 
    }
    return records;
              
  }

  public static void editRecords( DataSource ds , String [] recordArray , 
          int recordUpdatedBy ) throws SQLException , Exception {
  

    CallableStatement cs = null;
    DBConnection dbConnBean = null;
    int returnValue = 0;
    String query = "{? = call sp_upd_record(?,?)}";
    StringBuffer recordArrayBuffer = new StringBuffer("{");
    String rowVal = null;

    try {
      
      for( int index = 0; index < recordArray.length; index++ ){
        
        rowVal = recordArray[index].replace('|',',');
        
        if( rowVal.endsWith(",") ){
          rowVal = rowVal.substring(0,rowVal.length()-1);
        }else if( rowVal.trim().length() == 0 ){
          rowVal = "";
        }else{
          rowVal = rowVal.substring(0,rowVal.length());
        }
        
        logger.debug("rowVal : "+rowVal);
        
        if( index < recordArray.length -1 ){
          recordArrayBuffer.append("\"{"+rowVal+"}\",");
        }else{
          recordArrayBuffer.append("\"{"+rowVal+"}\"");
        }
      }
      recordArrayBuffer.append("}");
  
      logger.debug(" Record Array Buffer :  "+recordArrayBuffer);
      
      dbConnBean = new DBConnection(ds);
      cs = dbConnBean.prepareCall(query);
      cs.registerOutParameter(1,Types.INTEGER);
      cs.setArray(2,new PgSQLArray(recordArrayBuffer.toString(),Types.VARCHAR));
      cs.setInt(3,recordUpdatedBy);
      dbConnBean.executeCallableStatement(cs);
      returnValue = cs.getInt(1);
      logger.debug(" Return Value : "+returnValue);            
    }catch (SQLException sqle) {
      logger.error("SQLException occurred in editRecords");
      logger.error( sqle.toString() );
      throw sqle;
    }catch( Exception e ){
      logger.error("Exception occurred in editRecords");
      logger.error( e.toString() );
      throw e;
    }finally{
      dbConnBean.free(cs); 
    }
  
  }

}