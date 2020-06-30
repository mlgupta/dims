package dims.web.beans.rack;

import dims.web.actionforms.rack.RackListForm;
import dims.web.actionforms.rack.RackNewEditForm;
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

  public static void addRack( DataSource ds , RackNewEditForm form ) 
          throws SQLException , Exception {
    
    CallableStatement cs = null;
    DBConnection dbConnBean = null;
    int rack_tbl_pk = 0;
    String query = "{? = call sp_ins_rack(?,?,?)}";
    
    try {
      dbConnBean = new DBConnection(ds);
      cs = dbConnBean.prepareCall(query);
      cs.registerOutParameter(1,Types.INTEGER);
      logger.debug("form.getTxtRackNumber() : "+form.getTxtRackNumber());
      logger.debug("form.getCboRoomNumber() : "+form.getCboRoomNumber());
      logger.debug("form.getTxtNumberOfShelves() : "+form.getTxtNumberOfShelves());
      cs.setString(2,form.getTxtRackNumber());
      cs.setInt(3,Integer.parseInt(form.getCboRoomNumber()));
      cs.setInt(4,Integer.parseInt(form.getTxtNumberOfShelves()));
      dbConnBean.executeCallableStatement(cs);
      rack_tbl_pk = cs.getInt(1);
      logger.debug(" rack_tbl_pk : "+rack_tbl_pk);            
    }catch (SQLException sqle) {
      logger.error("SQLException occurred in addRack");
      logger.error( sqle.toString() );
      throw sqle;
    }catch( Exception e ){
      logger.error("Exception occurred in addRack");
      logger.error( e.toString() );
      e.printStackTrace();
      throw e;
    }finally{
      dbConnBean.free(cs); 
    }
  }


  public static void editRack( DataSource ds , RackNewEditForm form ) throws SQLException , Exception {
    
    CallableStatement cs = null;
    DBConnection dbConnBean = null;
    int returnVal = 0;
    String query = "{? = call sp_upd_rack(?,?,?,?)}";
    
    try {
      dbConnBean = new DBConnection(ds);
      cs = dbConnBean.prepareCall(query);
      cs.registerOutParameter(1,Types.INTEGER);
      cs.setInt(2,Integer.parseInt(form.getHdnRackTblPk()));
      cs.setString(3,form.getTxtRackNumber());
      cs.setInt(4,Integer.parseInt(form.getTxtNumberOfShelves()));
      cs.setInt(5,Integer.parseInt(form.getCboRoomNumber()));
      dbConnBean.executeCallableStatement(cs);
      returnVal = cs.getInt(1);
      logger.debug(" Return Value : "+returnVal);
    }catch (SQLException sqle) {
      logger.error("SQLException occurred in editRack");
      logger.error( sqle.toString() );
      throw sqle;
    }catch( Exception e ){
      logger.error("Exception occurred in editRack");
      logger.error( e.toString() );
      throw e;
    }finally{
      dbConnBean.free(cs); 
    }
  }

  public static void deleteRack( DataSource ds , String frm_rack_tbl_pk ) throws SQLException , Exception {
    
    CallableStatement cs = null;
    DBConnection dbConnBean = null;
    int returnVal = 0;
    String query = "{? = call sp_del_rack(?)}";
    
    try {
      dbConnBean = new DBConnection(ds);
      cs = dbConnBean.prepareCall(query);
      cs.registerOutParameter(1,Types.INTEGER);
      cs.setInt(2,Integer.parseInt(frm_rack_tbl_pk));
      dbConnBean.executeCallableStatement(cs);
      returnVal = cs.getInt(1);
      logger.debug(" Return Value : "+returnVal);
    }catch (SQLException sqle) {
      logger.error("SQLException occurred in deleteRack");
      logger.error( sqle.toString() );
      throw sqle;
    }catch( Exception e ){
      logger.error("Exception occurred in deleteRack");
      logger.error( e.toString() );
      throw e;
    }finally{
      dbConnBean.free(cs); 
    }
  }

  public static ArrayList listRacks( DataSource ds , RackListForm form , int numberOfRecords ) 
          throws SQLException , Exception {
    
    DBConnection dbConnBean = null;
    ResultSet rs = null;
    ArrayList racks = new ArrayList();
    RackListBean rack = null;
    
    StringBuffer query = new StringBuffer("select rkt.rack_tbl_pk , rkt.rack_number , rt.room_number , lt.location_id from rack_tbl rkt , room_tbl rt , location_tbl lt WHERE 1=1 AND rkt.room_tbl_fk=rt.room_tbl_pk AND rt.location_tbl_fk=lt.location_tbl_pk");

    int pageNumber = 1;
    int pageCount = 1;
    int startIndex = 1;
    int endIndex = 1;
    
    if( (form.getCboSearchLocations()!=null) &&  (form.getCboSearchLocations().trim().length()!=0) && (!form.getCboSearchLocations().equals("-1")) && (!form.getCboSearchLocations().equals("Select")) ){
      query.append(" AND lt.location_tbl_pk="+form.getCboSearchLocations());
    }
    
    if( (form.getCboSearchRoomNumber()!=null) &&  (form.getCboSearchRoomNumber().trim().length()!=0) && (!form.getCboSearchRoomNumber().equals("-1")) && (!form.getCboSearchRoomNumber().equals("Select"))){
      query.append(" AND rt.room_tbl_pk="+form.getCboSearchRoomNumber());
    }
    
    if( form.getTxtSearchRack().trim().length()!=0 ){
      query.append(" AND rkt.rack_number LIKE '"+form.getTxtSearchRack().replace('*','%')+"'");
    }

    query.append(" ORDER BY lt.location_id , rt.room_tbl_pk , rkt.rack_tbl_pk");

    logger.debug(" Query : ");
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
          rack = new RackListBean();
          rack.setRackTblPk(Integer.toString(rs.getInt("rack_tbl_pk")));
          rack.setRackNumber(rs.getString("rack_number"));
          rack.setRoomNumber(rs.getString("room_number"));
          rack.setLocationID(rs.getString("location_id"));
          racks.add(rack);
          startIndex++;
          if (startIndex > endIndex) {
            break;
          }
        }
        racks.add(0,new Integer(pageCount));
      }
    }catch (SQLException sqle) {
      logger.error("SQLException occurred in listRacks");
      logger.error( sqle.toString() );
      throw sqle;
    }catch( Exception e ){
      logger.error("Exception occurred in listRacks");
      logger.error( e.toString() );
      throw e;
    }finally{
      dbConnBean.free(rs); 
    }
    return racks;
  }

  public static RackNewEditForm getRackDetails( DataSource ds , String rack_tbl_pk ) 
          throws SQLException , Exception {
    
    DBConnection dbConnBean = null;
    ResultSet rs = null;
    StringBuffer query = new StringBuffer(" select rt.rack_tbl_pk, rt.rack_number, rt.room_tbl_fk,");
    query.append("(select max(shelf_number) from shelf_tbl st where st.rack_tbl_fk="+rack_tbl_pk+") As number_of_shelves ,");
    query.append("( select rmt.location_tbl_fk from room_tbl rmt where rmt.room_tbl_pk=rt.room_tbl_fk ) As location_tbl_pk");
    query.append(" from rack_tbl rt WHERE 1=1 AND rt.rack_tbl_pk="+rack_tbl_pk);
    
    RackNewEditForm rackNewEditForm = null; 
    
    try {
      dbConnBean = new DBConnection(ds);
      rs = dbConnBean.executeQuery(query.toString());
      if( rs != null ){
        while( rs.next() ){
          rackNewEditForm = new RackNewEditForm();
          rackNewEditForm.setHdnRackTblPk(String.valueOf(rs.getString("rack_tbl_pk")));
          rackNewEditForm.setTxtRackNumber(rs.getString("rack_number"));
          rackNewEditForm.setCboRoomNumber(String.valueOf(rs.getInt("room_tbl_fk")));
          rackNewEditForm.setTxtNumberOfShelves(String.valueOf(rs.getInt("number_of_shelves")));
          rackNewEditForm.setCboLocations(String.valueOf(rs.getInt("location_tbl_pk")));
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
    return rackNewEditForm;
  }

}