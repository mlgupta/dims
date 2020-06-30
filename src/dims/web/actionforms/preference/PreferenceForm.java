package dims.web.actionforms.preference;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

public class PreferenceForm extends ActionForm  {

  private String[] cboControlFlow;
  private String[] chkEnableDept;
  private String[] hdnDeptTblPk;
  private String[] txtDeptID;

  
  private Map controlFlowMap = new HashMap();
  private Map enableDeptMap = new HashMap();
  private Map deptTblPkMap = new HashMap();
  private Map deptIDMap = new HashMap();

  /**
   * Reset all properties to their default values.
   * @param mapping The ActionMapping used to select this instance.
   * @param request The HTTP Request we are processing.
   */
  public void reset(ActionMapping mapping, HttpServletRequest request) {
      super.reset(mapping, request);
  }

  /**
   * Validate all properties to their default values.
   * @param mapping The ActionMapping used to select this instance.
   * @param request The HTTP Request we are processing.
   * @return ActionErrors A list of all errors found.
   */
  public ActionErrors validate(ActionMapping mapping, HttpServletRequest request) {
      return super.validate(mapping, request);
  }

////////////
  public String getCboControlFlow(int index) {
    return (String) controlFlowMap.get(new Integer(index));
  }

  public void setCboControlFlow(int index ,String newCboControlFlow) {
    controlFlowMap.put( new Integer( index ), newCboControlFlow );
  }
  
  public String[] getCboControlFlows() {
    return (String[]) controlFlowMap.values().toArray( new String[controlFlowMap.size()] );
  }
////////////
  public String getChkEnableDept(int index) {
    return (String) enableDeptMap.get(new Integer(index));
  }

  public void setChkEnableDept(int index ,String newChkEnableDept) {
    enableDeptMap.put( new Integer( index ), newChkEnableDept );
  }
  
  public String[] getChkEnableDepts() {
    return (String[]) enableDeptMap.values().toArray( new String[enableDeptMap.size()] );
  }
////////////  
  public String getHdnDeptTblPk(int index) {
    return (String) deptTblPkMap.get(new Integer(index));
  }

  public void setHdnDeptTblPk(int index ,String newHdnDeptTblPk) {
    deptTblPkMap.put( new Integer( index ), newHdnDeptTblPk );
  }
  
  public String[] getHdnDeptTblPks() {
    return (String[]) deptTblPkMap.values().toArray( new String[deptTblPkMap.size()] );
  }
  
  public int deptTblPkMapSize(){
    return deptTblPkMap.size();
  }
  
///////////
  public String getTxtDeptID(int index) {
    return (String) deptIDMap.get(new Integer(index));
  }

  public void setTxtDeptID(int index ,String newTxtDeptID) {
    deptIDMap.put( new Integer( index ), newTxtDeptID );
  }
  
  public String[] getTxtDeptIDs() {
    return (String[]) deptIDMap.values().toArray( new String[deptIDMap.size()] );
  }
  
  public int deptIDMapSize(){
    return deptIDMap.size();
  }
  
////////////  
}