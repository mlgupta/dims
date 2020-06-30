package dims.web.actionforms.department;
import org.apache.struts.action.ActionForm;

public class DepartmentListForm extends ActionForm  {
  private String txtDepartmentID;
  private String txtDepartmentName;
  private String[] radSelect;
  private String[] hdnDepartmentName;
  private String hdnPageNumber;
  private String hdnPageCount;

  public String getTxtDepartmentID() {
    return txtDepartmentID;
  }

  public void setTxtDepartmentID(String txtDepartmentID) {
    this.txtDepartmentID = txtDepartmentID;
  }

  public String getTxtDepartmentName() {
    return txtDepartmentName;
  }

  public void setTxtDepartmentName(String txtDepartmentName) {
    this.txtDepartmentName = txtDepartmentName;
  }

  public String[] getRadSelect() {
    return radSelect;
  }

  public void setRadSelect(String[] radSelect) {
    this.radSelect = radSelect;
  }

  public String[] getHdnDepartmentName() {
    return hdnDepartmentName;
  }

  public void setHdnDepartmentName(String[] hdnDepartmentName) {
    this.hdnDepartmentName = hdnDepartmentName;
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