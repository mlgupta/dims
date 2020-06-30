package dims.web.actionforms.binders;

import org.apache.struts.action.ActionForm;

public class BinderListForm extends ActionForm  {

  private String txtBinderPrefix;
  private String[] radSelect;
  private String[] hdnBinderPrefix;
  private String hdnPageNumber;
  private String hdnPageCount;

  public String getTxtBinderPrefix() {
    return txtBinderPrefix;
  }

  public void setTxtBinderPrefix(String txtBinderPrefix) {
    this.txtBinderPrefix = txtBinderPrefix;
  }

  public String[] getRadSelect() {
    return radSelect;
  }

  public void setRadSelect(String[] radSelect) {
    this.radSelect = radSelect;
  }

  public String[] getHdnBinderPrefix() {
    return hdnBinderPrefix;
  }

  public void setHdnBinderPrefix(String[] hdnBinderPrefix) {
    this.hdnBinderPrefix = hdnBinderPrefix;
  }

  public String getHdnPageNumber() {
    return hdnPageNumber;
  }

  public void setHdnPageNumber(String hdnPageNumber) {
    this.hdnPageNumber = hdnPageNumber;
  }

  public String getHdnPageCount() {
    return hdnPageCount;
  }

  public void setHdnPageCount(String hdnPageCount) {
    this.hdnPageCount = hdnPageCount;
  }

}