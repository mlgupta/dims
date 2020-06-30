package dims.web.beans.location;

import dims.web.actionforms.location.LocationListForm;
import dims.web.actionforms.location.LocationNewEditForm;
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
  
  public static void addLocation( DataSource ds , LocationNewEditForm form ) 
          throws SQLException , Exception {
    
    CallableStatement cs = null;
    DBConnection dbConnBean = null;
    int location_tbl_pk = 0;
    String query = "{? = call sp_ins_location(?,?,?,?,?,?)}";
    
    try {
      dbConnBean = new DBConnection(ds);
      cs = dbConnBean.prepareCall(query);
      cs.registerOutParameter(1,Types.INTEGER);
      cs.setString(2,form.getTxtLocationID());
      cs.setString(3,form.getTxtLocationDescription());
      cs.setString(4,form.getTxtLocationCity());
      cs.setString(5,form.getTxtLocationState());
      cs.setString(6,form.getTxtLocationCountry());
      cs.setString(7,form.getTxtLocationZipcode());
      dbConnBean.executeCallableStatement(cs);
      location_tbl_pk = cs.getInt(1);
      logger.debug(" New Location Number : "+location_tbl_pk);            
    }catch (SQLException sqle) {
      logger.error("SQLException occurred in addLocation");
      logger.error( sqle.toString() );
      throw sqle;
    }catch( Exception e ){
      logger.error("Exception occurred in addLocation");
      logger.error( e.toString() );
      throw e;
    }finally{
      dbConnBean.free(cs); 
    }
  }


  public static void editLocation( DataSource ds , LocationNewEditForm form ) throws SQLException , Exception {
    
    CallableStatement cs = null;
    DBConnection dbConnBean = null;
    int location_tbl_pk = 0;
    String query = "{? = call sp_upd_location(?,?,?,?,?,?,?)}";
    
    try {
      dbConnBean = new DBConnection(ds);
      cs = dbConnBean.prepareCall(query);
      cs.registerOutParameter(1,Types.INTEGER);
      cs.setInt(2,Integer.parseInt(form.getTxtLocationNo()));
      cs.setString(3,form.getTxtLocationID());
      cs.setString(4,form.getTxtLocationDescription());
      cs.setString(5,form.getTxtLocationCity());
      cs.setString(6,form.getTxtLocationState());
      cs.setString(7,form.getTxtLocationCountry());
      cs.setString(8,form.getTxtLocationZipcode());
      dbConnBean.executeCallableStatement(cs);
      location_tbl_pk = cs.getInt(1);
      logger.debug(" Location Number : "+location_tbl_pk);
    }catch (SQLException sqle) {
      logger.error("SQLException occurred in editLocation");
      logger.error( sqle.toString() );
      throw sqle;
    }catch( Exception e ){
      logger.error("Exception occurred in editLocation");
      logger.error( e.toString() );
      throw e;
    }finally{
      dbConnBean.free(cs); 
    }
  }

  public static void deleteLocation( DataSource ds , String frm_location_tbl_pk ) 
          throws SQLException , Exception {
    
    CallableStatement cs = null;
    DBConnection dbConnBean = null;
    int location_tbl_pk = 0;
    String query = "{? = call sp_del_location(?)}";
    try {
      dbConnBean = new DBConnection(ds);
      cs = dbConnBean.prepareCall(query);
      cs.registerOutParameter(1,Types.INTEGER);
      cs.setInt(2,Integer.parseInt(frm_location_tbl_pk));
      dbConnBean.executeCallableStatement(cs);
      location_tbl_pk = cs.getInt(1);
      logger.debug(" Location Number Deleted : "+ location_tbl_pk);            
    }catch (SQLException sqle) {
      logger.error("SQLException occurred in deleteLocation");
      logger.error( sqle.toString() );
      throw sqle;
    }catch( Exception e ){
      logger.error("Exception occurred in deleteLocation");
      logger.error( e.toString() );
      throw e;
    }finally{
      dbConnBean.free(cs); 
    }
  }


  public static ArrayList listLocations( DataSource ds , LocationListForm form , int numberOfRecords ) 
          throws SQLException , Exception {
    
    DBConnection dbConnBean = null;
    ResultSet rs = null;
    ArrayList locations = new ArrayList();
    LocationListBean location = null;
    StringBuffer query = new StringBuffer("select location_tbl_pk, location_id, location_city, location_state, location_country, location_zipcode from location_tbl WHERE 1=1");
    
    int pageNumber = 1;
    int pageCount = 1;
    int startIndex = 1;
    int endIndex = 1;
    
    if( form.getLocationID().trim().length()!=0 ){
      query.append(" AND location_id LIKE '"+form.getLocationID().replace('*','%')+"'");
    }
    
    if( form.getLocationCity().trim().length()!=0 ){
      query.append(" AND location_city LIKE '"+form.getLocationCity().toUpperCase().replace('*','%')+"'");
    }
    query.append(" ORDER BY location_id");
    
    logger.debug("Query : "+query);
    
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
          location = new LocationListBean();
          location.setLocationTblPK(Integer.toString(rs.getInt("location_tbl_pk")));
          location.setLocationID(rs.getString("location_id"));
          location.setLocationCity(rs.getString("location_city"));
          location.setLocationState(rs.getString("location_state"));
          location.setLocationCountry(rs.getString("location_country"));
          location.setLocationZipcode(rs.getString("location_zipcode"));
          locations.add(location);
          startIndex++;
          if (startIndex > endIndex) {
            break;
          }
        }
        locations.add(0,new Integer(pageCount));
      }
    }catch (SQLException sqle) {
      logger.error("SQLException occurred in listLocations");
      logger.error( sqle.toString() );
      throw sqle;
    }catch( Exception e ){
      logger.error("Exception occurred in listLocations");
      logger.error( e.toString() );
      throw e;
    }finally{
      dbConnBean.free(rs); 
    }
    return locations;
  }

  public static ArrayList listLocations( DataSource ds )
          throws SQLException , Exception {

    DBConnection dbConnBean = null;
    ResultSet rs = null;
    ArrayList locations = new ArrayList();
    LocationListBean location = null;
    StringBuffer query = new StringBuffer("select location_tbl_pk, location_id from location_tbl WHERE 1=1");
    query.append(" ORDER BY location_id");

    try {
      dbConnBean = new DBConnection(ds);
      rs = dbConnBean.executeQuery(query.toString(),ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
      if( rs != null ){
        while( rs.next() ){
          location = new LocationListBean();
          location.setLocationTblPK(String.valueOf(rs.getInt("location_tbl_pk")));
          location.setLocationID(rs.getString("location_id"));
          locations.add(location);
        }
        location = new LocationListBean();
        location.setLocationTblPK("-1");
        location.setLocationID("Select");
        locations.add(0,location);
      }
    }catch (SQLException sqle) {
      logger.error("SQLException occurred in listLocations");
      logger.error( sqle.toString() );
      throw sqle;
    }catch( Exception e ){
      logger.error("Exception occurred in listLocations");
      logger.error( e.toString() );
      throw e;
    }finally{
      dbConnBean.free(rs);
    }
    return locations;
  }


  public static LocationNewEditForm getLocationDetails( DataSource ds , String location_tbl_pk ) 
          throws SQLException , Exception {
    
    DBConnection dbConnBean = null;
    ResultSet rs = null;
    String query = " select location_tbl_pk, location_id, location_description, location_city, location_state, location_country, location_zipcode from location_tbl where location_tbl_pk="+location_tbl_pk;
    
    LocationNewEditForm locationNewEditForm = null; 
    
    try {
      dbConnBean = new DBConnection(ds);
      rs = dbConnBean.executeQuery(query);
      if( rs != null ){
        while( rs.next() ){
          locationNewEditForm = new LocationNewEditForm();
          locationNewEditForm.setLocationTblPK(location_tbl_pk);
          locationNewEditForm.setTxtLocationNo(location_tbl_pk);
          locationNewEditForm.setTxtLocationID(rs.getString("location_id"));
          locationNewEditForm.setTxtLocationDescription(rs.getString("location_description"));
          locationNewEditForm.setTxtLocationCity(rs.getString("location_city"));
          locationNewEditForm.setTxtLocationState(rs.getString("location_state"));
          locationNewEditForm.setTxtLocationCountry(rs.getString("location_country"));
          locationNewEditForm.setTxtLocationZipcode(rs.getString("location_zipcode"));
        }
      }
    }catch (SQLException sqle) {
      logger.error("SQLException occurred in getLocationDetails");
      logger.error(sqle.toString());
      throw sqle;
    }catch (Exception e) {
      logger.error("Exception occurred in getLocationDetails");
      logger.error(e.toString());
      throw e;
    }finally{
      dbConnBean.free(rs);
    }
    return locationNewEditForm;
  }
}