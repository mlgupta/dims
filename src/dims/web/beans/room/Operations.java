package dims.web.beans.room;

import dims.web.actionforms.room.RoomListForm;
import dims.web.actionforms.room.RoomNewEditForm;
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

public final class Operations   {

  static Logger logger = Logger.getLogger(DIMSConstants.LOGGER.toString());
  
  public static void addRoom( DataSource ds , RoomNewEditForm form ) 
          throws SQLException , Exception {
    
    CallableStatement cs = null;
    DBConnection dbConnBean = null;
    int returnVal = 0;
    String query = "{? = call sp_ins_room(?,?)}";
    
    try {
      dbConnBean = new DBConnection(ds);
      cs = dbConnBean.prepareCall(query);
      cs.registerOutParameter(1,Types.INTEGER);
      cs.setString(2,form.getTxtRoomNumber());
      cs.setInt(3,Integer.parseInt(form.getCboLocations()));
      dbConnBean.executeCallableStatement(cs);
      returnVal = cs.getInt(1);
      logger.debug(" Return Value : "+returnVal);            
    }catch (SQLException sqle) {
      logger.error("SQLException occurred in addRoom");
      logger.error( sqle.toString() );
      throw sqle;
    }catch( Exception e ){
      logger.error("Exception occurred in addRoom");
      logger.error( e.toString() );
      throw e;
    }finally{
      dbConnBean.free(cs); 
    }
  }


  public static void editRoom( DataSource ds , RoomNewEditForm form ) throws SQLException , Exception {
    
    CallableStatement cs = null;
    DBConnection dbConnBean = null;
    int returnVal = 0;
    String query = "{? = call sp_upd_room(?,?,?)}";
    
    try {
      dbConnBean = new DBConnection(ds);
      cs = dbConnBean.prepareCall(query);
      cs.registerOutParameter(1,Types.INTEGER);
      cs.setInt(2,Integer.parseInt(form.getCboLocations()));
      cs.setString(3,form.getTxtRoomNumber());
      cs.setInt(4,Integer.parseInt(form.getHdnRoomTblPk()));
      dbConnBean.executeCallableStatement(cs);
      returnVal = cs.getInt(1);
      logger.debug(" Return Value : "+returnVal);
    }catch (SQLException sqle) {
      logger.error("SQLException occurred in editRoom");
      logger.error( sqle.toString() );
      throw sqle;
    }catch( Exception e ){
      logger.error("Exception occurred in editRoom");
      logger.error( e.toString() );
      throw e;
    }finally{
      dbConnBean.free(cs); 
    }
  }

  public static void deleteRoom( DataSource ds , String frm_room_tbl_pk ) 
          throws SQLException , Exception {
    
    CallableStatement cs = null;
    DBConnection dbConnBean = null;
    int returnValue = 0;
    String query = "{? = call sp_del_room(?)}";
    
    try {
      dbConnBean = new DBConnection(ds);
      cs = dbConnBean.prepareCall(query);
      cs.registerOutParameter(1,Types.INTEGER);
      cs.setInt(2,Integer.parseInt(frm_room_tbl_pk));
      dbConnBean.executeCallableStatement(cs);
      returnValue = cs.getInt(1);
      logger.debug(" Return Value : "+ returnValue);            
    }catch (SQLException sqle) {
      logger.error("SQLException occurred in deleteRoom");
      logger.error( sqle.toString() );
      throw sqle;
    }catch( Exception e ){
      logger.error("Exception occurred in deleteRoom");
      logger.error( e.toString() );
      throw e;
    }finally{
      dbConnBean.free(cs); 
    }
  }


  public static ArrayList listRooms( DataSource ds , RoomListForm form , int numberOfRecords ) 
          throws SQLException , Exception {
    
    DBConnection dbConnBean = null;
    ResultSet rs = null;
    ArrayList rooms = new ArrayList();
    RoomListBean room = null;
    
    StringBuffer query = new StringBuffer("select rt.room_tbl_pk , rt.room_number , lt.location_id from room_tbl rt , location_tbl lt WHERE 1=1 AND rt.location_tbl_fk=lt.location_tbl_pk");
    
    int pageNumber = 1;
    int pageCount = 1;
    int startIndex = 1;
    int endIndex = 1;
    
    if( !form.getCboSearchLocations().equals("Select") ){
      query.append(" AND lt.location_id='"+form.getCboSearchLocations()+"'");
    }
    if( form.getTxtSearchRoomNumber().trim().length()!=0 ){
      query.append(" AND rt.room_number LIKE '"+form.getTxtSearchRoomNumber().replace('*','%')+"'");
    }
    query.append(" ORDER BY lt.location_id , rt.room_tbl_pk");

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
          room = new RoomListBean();
          room.setRoomTblPk(Integer.toString(rs.getInt("room_tbl_pk")));
          room.setRoomNumber(rs.getString("room_number"));
          room.setLocationID(rs.getString("location_id"));
          rooms.add(room);
          startIndex++;
          if (startIndex > endIndex) {
            break;
          }
        }
        rooms.add(0,new Integer(pageCount));
      }
    }catch (SQLException sqle) {
      logger.error("SQLException occurred in listRooms");
      logger.error( sqle.toString() );
      throw sqle;
    }catch( Exception e ){
      logger.error("Exception occurred in listRooms");
      logger.error( e.toString() );
      throw e;
    }finally{
      dbConnBean.free(rs); 
    }
    return rooms;
  }

  public static String listRoomsForAjax( DataSource ds , int locationTblPK ) 
          throws SQLException , Exception {
    
    DBConnection dbConnBean = null;
    ResultSet rs = null;
    StringBuffer rooms = new StringBuffer("Select,-1|");
    
    String query = "select room_tbl_pk , room_number from room_tbl WHERE 1=1 AND location_tbl_fk="+locationTblPK+" ORDER BY room_tbl_pk";
    
    try {
      dbConnBean = new DBConnection(ds);
      rs = dbConnBean.executeQuery(query,ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
      if( rs != null ){
        while( rs.next() ){
          if( ! rs.isLast() ){
            rooms.append(rs.getString("room_number")+","+Integer.toString(rs.getInt("room_tbl_pk"))+"|");
          }else{
            rooms.append(rs.getString("room_number")+","+Integer.toString(rs.getInt("room_tbl_pk")));
          }
        }
      }
    }catch (SQLException sqle) {
      logger.error("SQLException occurred in listRooms");
      logger.error( sqle.toString() );
      throw sqle;
    }catch( Exception e ){
      logger.error("Exception occurred in listRooms");
      logger.error( e.toString() );
      throw e;
    }finally{
      dbConnBean.free(rs); 
    }
    return rooms.toString();
  }


  public static ArrayList listRooms( DataSource ds , int locationTblPK ) 
          throws SQLException , Exception {
    
    DBConnection dbConnBean = null;
    ResultSet rs = null;
    ArrayList rooms = new ArrayList();
    RoomListBean room = null;
    
    String query = "select room_tbl_pk , room_number from room_tbl WHERE 1=1 AND location_tbl_fk="+locationTblPK+" ORDER BY room_tbl_pk";
    
    try {
      dbConnBean = new DBConnection(ds);
      rs = dbConnBean.executeQuery(query,ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
      if( rs != null ){
        while( rs.next() ){
          room = new RoomListBean();
          room.setRoomTblPk(Integer.toString(rs.getInt("room_tbl_pk")));
          room.setRoomNumber(rs.getString("room_number"));
          rooms.add(room);
        }
        room = new RoomListBean();
        room.setRoomTblPk("-1");
        room.setRoomNumber("Select");
        rooms.add(0,room);
      }
    }catch (SQLException sqle) {
      logger.error("SQLException occurred in listRooms");
      logger.error( sqle.toString() );
      throw sqle;
    }catch( Exception e ){
      logger.error("Exception occurred in listRooms");
      logger.error( e.toString() );
      throw e;
    }finally{
      dbConnBean.free(rs); 
    }
    return rooms;
  }


  public static RoomNewEditForm getRoomDetails( DataSource ds , String room_tbl_pk ) 
          throws SQLException , Exception {
    
    DBConnection dbConnBean = null;
    ResultSet rs = null;
    String query = " select room_number , location_tbl_fk from room_tbl WHERE room_tbl_pk="+room_tbl_pk;
    
    RoomNewEditForm roomNewEditForm = null; 
    
    try {
      dbConnBean = new DBConnection(ds);
      rs = dbConnBean.executeQuery(query);
      if( rs != null ){
        while( rs.next() ){
          roomNewEditForm = new RoomNewEditForm();
          roomNewEditForm.setHdnRoomTblPk(room_tbl_pk);
          roomNewEditForm.setTxtRoomNumber(rs.getString("room_number"));
          roomNewEditForm.setCboLocations(String.valueOf(rs.getInt("location_tbl_fk")));
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
    return roomNewEditForm;
  }
 

}