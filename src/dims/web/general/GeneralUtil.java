package dims.web.general;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;
import org.apache.log4j.Logger;

public class GeneralUtil {

  private static Logger logger =
    Logger.getLogger(DIMSConstants.LOGGER.toString());


  public static int getPageCount(ResultSet rs, int numberOfRecords)throws SQLException{

      int recordCount;
      int pageCount=1;
      try{
        rs.last();
        recordCount=rs.getRow();
        if (recordCount!=0){
          pageCount=((recordCount%numberOfRecords)==0)?(recordCount/numberOfRecords):((recordCount/numberOfRecords)+1);
        }
        rs.beforeFirst();
      }catch(SQLException se){
        logger.error("***Exception in getPageCount() method"+se.getMessage());
        throw se;   
      }
      return pageCount;
  }
  
}
