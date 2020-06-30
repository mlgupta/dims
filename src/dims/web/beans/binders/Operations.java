package dims.web.beans.binders;

import dims.web.actionforms.binders.BinderListForm;
import dims.web.actionforms.binders.BinderNewEditForm;
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

  public static void addBinderPrefix( DataSource ds , BinderNewEditForm form ) 
          throws SQLException , Exception {
    
    CallableStatement cs = null;
    DBConnection dbConnBean = null;
    int binder_prefix_tbl_pk = 0;
    String query = "{? = call sp_ins_binder_prefix(?,?)}";
    
    try {
      dbConnBean = new DBConnection(ds);
      cs = dbConnBean.prepareCall(query);
      cs.registerOutParameter(1,Types.INTEGER);
      cs.setString(2,form.getTxtBinderPrefixName());
      cs.setString(3,form.getTxtBinderPrefixDescription());
      dbConnBean.executeCallableStatement(cs);
      binder_prefix_tbl_pk = cs.getInt(1);
      logger.debug(" New Binder Prefix Number : "+binder_prefix_tbl_pk);            
    }catch (SQLException sqle) {
      logger.error("SQLException occurred in addBinderPrefix");
      logger.error( sqle.toString() );
      throw sqle;
    }catch( Exception e ){
      logger.error("Exception occurred in addBinderPrefix");
      logger.error( e.toString() );
      throw e;
    }finally{
      dbConnBean.free(cs); 
    }
  }

  public static void editBinderPrefix( DataSource ds , BinderNewEditForm form ) 
          throws SQLException , Exception {
    
    CallableStatement cs = null;
    DBConnection dbConnBean = null;
    int binder_prefix_tbl_pk = 0;
    String query = "{? = call sp_upd_binder_prefix(?,?,?)}";
    
    try {
      dbConnBean = new DBConnection(ds);
      cs = dbConnBean.prepareCall(query);
      cs.registerOutParameter(1,Types.INTEGER);
      cs.setString(2,form.getTxtBinderPrefixName());
      cs.setString(3,form.getTxtBinderPrefixDescription());
      cs.setInt(4,Integer.parseInt(form.getTxtBinderPrefixNumber()));
      dbConnBean.executeCallableStatement(cs);
      binder_prefix_tbl_pk = cs.getInt(1);
      logger.debug(" Binder Number : "+binder_prefix_tbl_pk);            
    }catch (SQLException sqle) {
      logger.error("SQLException occurred in editBinderPrefix");
      logger.error( sqle.toString() );
      throw sqle;
    }catch( Exception e ){
      logger.error("Exception occurred in editBinderPrefix");
      logger.error( e.toString() );
      throw e;
    }finally{
      dbConnBean.free(cs); 
    }
  }


  public static void deleteBinderPrefix( DataSource ds , String frm_binder_prefix_tbl_pk ) 
          throws SQLException , Exception {
    
    CallableStatement cs = null;
    DBConnection dbConnBean = null;
    int binder_prefix_tbl_pk = 0;
    String query = "{? = call sp_del_binder_prefix(?)}";
    
    try {
      dbConnBean = new DBConnection(ds);
      cs = dbConnBean.prepareCall(query);
      cs.registerOutParameter(1,Types.INTEGER);
      cs.setInt(2,Integer.parseInt(frm_binder_prefix_tbl_pk));
      dbConnBean.executeCallableStatement(cs);
      binder_prefix_tbl_pk = cs.getInt(1);
      logger.debug(" Binder Prefix Number Deleted : "+binder_prefix_tbl_pk);            
    }catch (SQLException sqle) {
      logger.error("SQLException occurred in deleteBinderPrefix");
      logger.error( sqle.toString() );
      throw sqle;
    }catch( Exception e ){
      logger.error("Exception occurred in deleteBinderPrefix");
      logger.error( e.toString() );
      throw e;
    }finally{
      dbConnBean.free(cs); 
    }
  }


  public static ArrayList listBinderPrefixes( DataSource ds , BinderListForm form , int numberOfRecords )
        throws SQLException , Exception {

    DBConnection dbConnBean = null;
    ResultSet rs = null;
    ArrayList binders = new ArrayList();
    BinderListBean binder = null;
    StringBuffer query = new StringBuffer("select binder_prefix_tbl_pk , binder_prefix_name , binder_prefix_desc from binder_prefix_tbl WHERE 1=1");
    
    int pageNumber = 1;
    int pageCount = 1;
    int startIndex = 1;
    int endIndex = 1;
    
    if( form.getTxtBinderPrefix().trim().length()!=0 ){
      query.append(" AND binder_prefix_name LIKE '"+form.getTxtBinderPrefix().replace('*','%')+"'");
    }
    query.append(" ORDER BY binder_prefix_tbl_pk");
    
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
          binder = new BinderListBean();
          binder.setBinderTblPk(Integer.toString(rs.getInt("binder_prefix_tbl_pk")));
          binder.setBinderPrefix(rs.getString("binder_prefix_name"));
          binder.setBinderPrefixDescription(rs.getString("binder_prefix_desc"));
          binders.add(binder);
          startIndex++;
          if (startIndex > endIndex) {
            break;
          }
        }
        binders.add(0,new Integer(pageCount));
      }
    }catch (SQLException sqle) {
      logger.error("SQLException occurred in listBinderPrefixes");
      logger.error( sqle.toString() );
      throw sqle;
    }catch( Exception e ){
      logger.error("Exception occurred in listBinderPrefixes");
      logger.error( e.toString() );
      throw e;
    }finally{
      dbConnBean.free(rs); 
    }
    return binders;
              
  }
  
  public static BinderNewEditForm getBinderPrefixDetails( DataSource ds , String binder_prefix_tbl_pk ) 
          throws SQLException , Exception {
    
    DBConnection dbConnBean = null;
    ResultSet rs = null;
    String query = " select binder_prefix_tbl_pk , binder_prefix_name , binder_prefix_desc from binder_prefix_tbl WHERE binder_prefix_tbl_pk="+binder_prefix_tbl_pk;
    
    BinderNewEditForm binderNewEditForm = null; 
    
    try {
      dbConnBean = new DBConnection(ds);
      rs = dbConnBean.executeQuery(query);
      if( rs != null ){
        while( rs.next() ){
          binderNewEditForm = new BinderNewEditForm();
          binderNewEditForm.setTxtBinderPrefixNumber(binder_prefix_tbl_pk);
          binderNewEditForm.setTxtBinderPrefixName(rs.getString("binder_prefix_name"));
          binderNewEditForm.setTxtBinderPrefixDescription(rs.getString("binder_prefix_desc"));
        }
      }
    }catch (SQLException sqle) {
      logger.error("SQLException occurred in getBinderPrefixDetails");
      logger.error(sqle.toString());
      throw sqle;
    }catch (Exception e) {
      logger.error("Exception occurred in getBinderPrefixDetails");
      logger.error(e.toString());
      throw e;
    }finally{
      dbConnBean.free(rs);
    }
    return binderNewEditForm;
  }
  
  public static ArrayList listBinderPrefixs(DataSource ds) throws SQLException , Exception {

    DBConnection dbConnBean = null;
    ResultSet rs = null;
    ArrayList prefixs = new ArrayList();
    BinderListBean prefix = null;
    StringBuffer query = new StringBuffer("select binder_prefix_tbl_pk, binder_prefix_name from binder_prefix_tbl WHERE 1=1");
    query.append(" ORDER BY binder_prefix_name");

    try {
      dbConnBean = new DBConnection(ds);
      rs = dbConnBean.executeQuery(query.toString(),ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
      if( rs != null ){
        while( rs.next() ){
          prefix = new BinderListBean();
          prefix.setBinderTblPk(String.valueOf(rs.getInt("binder_prefix_tbl_pk")));
          prefix.setBinderPrefix(rs.getString("binder_prefix_name"));
          prefixs.add(prefix);
        }
        //prefix = new BinderListBean();
        //prefix.setLocationTblPK("-1");
        //prefix.setLocationID("Select");
        //prefixs.add(0,location);
      }
    }catch (SQLException sqle) {
      logger.error("SQLException occurred in listBinderPrefixs");
      logger.error( sqle.toString() );
      throw sqle;
    }catch( Exception e ){
      logger.error("Exception occurred in listBinderPrefixs");
      logger.error( e.toString() );
      throw e;
    }finally{
      dbConnBean.free(rs);
    }
    return prefixs;
  }

  
}