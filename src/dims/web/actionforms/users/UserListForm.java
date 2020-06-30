package dims.web.actionforms.users;

import org.apache.struts.action.ActionForm;

public class UserListForm extends ActionForm  {

  private String cboSearchDepartments;
  private String txtSearchUserID;
  private String hdnPageNumber;
  private String hdnPageCount;
  private String[] radSelect;
  private String[] hdnUserId;

  public String getCboSearchDepartments() {
    return cboSearchDepartments;
  }

  public void setCboSearchDepartments(String cboSearchDepartments) {
    this.cboSearchDepartments = cboSearchDepartments;
  }

  public String getTxtSearchUserID() {
    return txtSearchUserID;
  }

  public void setTxtSearchUserID(String txtSearchUserID) {
    this.txtSearchUserID = txtSearchUserID;
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

  public String[] getRadSelect() {
    return radSelect;
  }

  public void setRadSelect(String[] radSelect) {
    this.radSelect = radSelect;
  }

  public String[] getHdnUserId() {
    return hdnUserId;
  }

  public void setHdnUserId(String[] hdnUserId) {
    this.hdnUserId = hdnUserId;
  }
}