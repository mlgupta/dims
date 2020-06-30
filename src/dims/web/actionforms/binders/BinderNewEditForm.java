package dims.web.actionforms.binders;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.validator.ValidatorForm;

public class BinderNewEditForm extends ValidatorForm  {
  private String txtBinderPrefixNumber;
  private String txtBinderPrefixName;
  private String txtBinderPrefixDescription;

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

  public String getTxtBinderPrefixNumber() {
    return txtBinderPrefixNumber;
  }

  public void setTxtBinderPrefixNumber(String txtBinderPrefixNumber) {
    this.txtBinderPrefixNumber = txtBinderPrefixNumber;
  }

  public String getTxtBinderPrefixName() {
    return txtBinderPrefixName;
  }

  public void setTxtBinderPrefixName(String txtBinderPrefixName) {
    this.txtBinderPrefixName = txtBinderPrefixName;
  }

  public String getTxtBinderPrefixDescription() {
    return txtBinderPrefixDescription;
  }

  public void setTxtBinderPrefixDescription(String txtBinderPrefixDescription) {
    this.txtBinderPrefixDescription = txtBinderPrefixDescription;
  }


}