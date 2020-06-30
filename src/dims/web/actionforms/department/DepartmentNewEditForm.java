package dims.web.actionforms.department;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.validator.ValidatorForm;

public class DepartmentNewEditForm extends ValidatorForm  {

  private String txtDepartmentId;
  private String txtDepartmentName;
  private String txaDescription;
  private String txtDepartmentNo;
  private String hdnDeptControlSeq;
  private boolean hdnBoolDeptEnable;

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

  public String getTxtDepartmentId() {
    return txtDepartmentId;
  }

  public void setTxtDepartmentId(String txtDepartmentId) {
    this.txtDepartmentId = txtDepartmentId;
  }

  public String getTxtDepartmentName() {
    return txtDepartmentName;
  }

  public void setTxtDepartmentName(String txtDepartmentName) {
    this.txtDepartmentName = txtDepartmentName;
  }

  public String getTxaDescription() {
    return txaDescription;
  }

  public void setTxaDescription(String txaDescription) {
    this.txaDescription = txaDescription;
  }

  public String getTxtDepartmentNo() {
    return txtDepartmentNo;
  }

  public void setTxtDepartmentNo(String txtDepartmentNo) {
    this.txtDepartmentNo = txtDepartmentNo;
  }

  public String getHdnDeptControlSeq() {
    return hdnDeptControlSeq;
  }

  public void setHdnDeptControlSeq(String hdnDeptControlSeq) {
    this.hdnDeptControlSeq = hdnDeptControlSeq;
  }

  public boolean isHdnBoolDeptEnable() {
    return hdnBoolDeptEnable;
  }

  public void setHdnBoolDeptEnable(boolean hdnBoolDeptEnable) {
    this.hdnBoolDeptEnable = hdnBoolDeptEnable;
  }


}