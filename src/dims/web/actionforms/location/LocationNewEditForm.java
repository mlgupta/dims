package dims.web.actionforms.location;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.validator.ValidatorForm;

public class LocationNewEditForm extends ValidatorForm  {

  private String locationTblPK; 
  private String txtLocationNo;
  private String txtLocationID; 
  private String txtLocationDescription; 
  private String txtLocationCity; 
  private String txtLocationState;
  private String txtLocationCountry;
  private String txtLocationZipcode;

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

  public void setLocationTblPK(String locationTblPK) {
    this.locationTblPK = locationTblPK;
  }

  public String getLocationTblPK() {
    return locationTblPK;
  }

  public void setTxtLocationID(String txtLocationID) {
    this.txtLocationID = txtLocationID;
  }

  public String getTxtLocationID() {
    return txtLocationID;
  }

  public void setTxtLocationDescription(String txtLocationDescription) {
    this.txtLocationDescription = txtLocationDescription;
  }

  public String getTxtLocationDescription() {
    return txtLocationDescription;
  }

  public void setTxtLocationCity(String txtLocationCity) {
    this.txtLocationCity = txtLocationCity;
  }

  public String getTxtLocationCity() {
    return txtLocationCity;
  }

  public void setTxtLocationState(String txtLocationState) {
    this.txtLocationState = txtLocationState;
  }

  public String getTxtLocationState() {
    return txtLocationState;
  }

  public void setTxtLocationCountry(String txtLocationCountry) {
    this.txtLocationCountry = txtLocationCountry;
  }

  public String getTxtLocationCountry() {
    return txtLocationCountry;
  }

  public void setTxtLocationZipcode(String txtLocationZipcode) {
    this.txtLocationZipcode = txtLocationZipcode;
  }

  public String getTxtLocationZipcode() {
    return txtLocationZipcode;
  } 

  public String getTxtLocationNo() {
    return txtLocationNo;
  }

  public void setTxtLocationNo(String txtLocationNo) {
    this.txtLocationNo = txtLocationNo;
  }
}