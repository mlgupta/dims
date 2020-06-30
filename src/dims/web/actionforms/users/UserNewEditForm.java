package dims.web.actionforms.users;
import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.validator.ValidatorForm;

public class UserNewEditForm extends ValidatorForm  {
  private String cboDepartments;
  private String txtUserID;
  private String txtUserPassword;
  private String txtConfirmPassword;
  private String txtUserName;
  private String radStatus;
  private String hdnUserTblPk;

  public String getCboDepartments() {
    return cboDepartments;
  }

  public void setCboDepartments(String cboDepartments) {
    this.cboDepartments = cboDepartments;
  }

  public String getTxtUserID() {
    return txtUserID;
  }

  public void setTxtUserID(String txtUserID) {
    this.txtUserID = txtUserID;
  }

  public String getTxtUserPassword() {
    return txtUserPassword;
  }

  public void setTxtUserPassword(String txtUserPassword) {
    this.txtUserPassword = txtUserPassword;
  }

  public String getTxtConfirmPassword() {
    return txtConfirmPassword;
  }

  public void setTxtConfirmPassword(String txtConfirmPassword) {
    this.txtConfirmPassword = txtConfirmPassword;
  }

  public String getTxtUserName() {
    return txtUserName;
  }

  public void setTxtUserName(String txtUserName) {
    this.txtUserName = txtUserName;
  }

  public String getRadStatus() {
    return radStatus;
  }

  public void setRadStatus(String radStatus) {
    this.radStatus = radStatus;
  }
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

  public String getHdnUserTblPk() {
    return hdnUserTblPk;
  }

  public void setHdnUserTblPk(String hdnUserTblPk) {
    this.hdnUserTblPk = hdnUserTblPk;
  }

}