package dims.web.general;

/**
 *	Purpose: To generate the name for the constants used in program.
 *  Info: This class has one constructor whaich takes one string argument and initializes the
 *        constant_name with that value.
 *  @author               Suved Mishra
 *  @version              1.0
 * 	Date of creation:     26-07-2006
 * 	Last Modfied by :     
 * 	Last Modfied Date:    
 */

public final class DIMSConstants  {

  //Tag Name 
  private final String constant_name;

 //Private Contructor to use in this class only
  private DIMSConstants(String constant_name){
    this.constant_name=constant_name;
  }

   /**
	 * Purpose  : To Get the Contant , the toString() method of Object Class is overloaded 
	 * @returns : Returns an Contant Name
	 */
  public String toString(){
    return this.constant_name;
  }

  public static final DIMSConstants LOGGER = new  DIMSConstants("DIMSLogger");

}
