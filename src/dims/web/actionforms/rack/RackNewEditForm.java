package dims.web.actionforms.rack;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.validator.ValidatorForm;

public class RackNewEditForm extends ValidatorForm  {

  private String hdnRackTblPk;
  private String txtRackNumber;
  private String txtNumberOfShelves;
  private String cboRoomNumber;
  private String cboLocations;

  public String getHdnRackTblPk() {
    return hdnRackTblPk;
  }

  public void setHdnRackTblPk(String hdnRackTblPk) {
    this.hdnRackTblPk = hdnRackTblPk;
  }

  public String getTxtRackNumber() {
    return txtRackNumber;
  }

  public void setTxtRackNumber(String txtRackNumber) {
    this.txtRackNumber = txtRackNumber;
  }

  public String getTxtNumberOfShelves() {
    return txtNumberOfShelves;
  }

  public void setTxtNumberOfShelves(String txtNumberOfShelves) {
    this.txtNumberOfShelves = txtNumberOfShelves;
  }

  public String getCboRoomNumber() {
    return cboRoomNumber;
  }

  public void setCboRoomNumber(String cboRoomNumber) {
    this.cboRoomNumber = cboRoomNumber;
  }

  public String getCboLocations() {
    return cboLocations;
  }

  public void setCboLocations(String cboLocations) {
    this.cboLocations = cboLocations;
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

}