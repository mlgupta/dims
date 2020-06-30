package dims.web.actionforms.room;

import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.validator.ValidatorForm;

public class RoomNewEditForm extends ValidatorForm  {

  private String cboLocations;
  private String txtRoomNumber;
  private String hdnRoomTblPk;

  public String getCboLocations() {
    return cboLocations;
  }

  public void setCboLocations(String cboLocations) {
    this.cboLocations = cboLocations;
  }

  public String getTxtRoomNumber() {
    return txtRoomNumber;
  }

  public void setTxtRoomNumber(String txtRoomNumber) {
    this.txtRoomNumber = txtRoomNumber;
  }

  public String getHdnRoomTblPk() {
    return hdnRoomTblPk;
  }

  public void setHdnRoomTblPk(String hdnRoomTblPk) {
    this.hdnRoomTblPk = hdnRoomTblPk;
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