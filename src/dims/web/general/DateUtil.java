package dims.web.general;

/**
 *	Purpose: To get the formatted date. 
 *  @author              Rajan Kamal Gupta
 *  @version             1.0
 * 	Last Modified by :     
 * 	Last Modified Date:    
 */
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil{

    public DateUtil() { }

    /**
     * Purpose   : Returns formatted date in dd-MMM-yyyy format.
     * @param    : date - Date
     * @returns  : formattedDate
     */
    public static String getFormattedDate(Date date){
        Date unformattedDate=date;
        SimpleDateFormat dateFormat=new SimpleDateFormat("dd-MM-yyyy");
        String formattedDate = dateFormat.format(unformattedDate);
        return formattedDate;
    }

    /**
     * Purpose   : Returns formatted date in dd-MMM-yy format.
     * @param    : strDate - String
     * @returns  : date in dd-MMM-yyyy
     */
    public static Date parseDate(String strDate) throws ParseException{
        Date date;
        SimpleDateFormat dateFormat=new SimpleDateFormat("dd-MM-yy");
        date = dateFormat.parse(strDate);
        return date;
    }
    
    public static String format(Date date, String pattern) {
        SimpleDateFormat formatter = new SimpleDateFormat(pattern);
        return formatter.format(date);    

    }
    /**
     * Purpose   : To convert a given String in Date object.
     * @param    date - String representation of date object.
     * @param    pattern - Pattern for the date.
     * @return   Date - the converted date.
     */
    public static Date parse(String date,String pattern){
        Date parsedDate=null;
        try{
            SimpleDateFormat parser = new SimpleDateFormat(pattern);
            parsedDate=parser.parse(date);
        }catch(ParseException pe){
          pe.getMessage();
        }
        return parsedDate;
    }



} 
