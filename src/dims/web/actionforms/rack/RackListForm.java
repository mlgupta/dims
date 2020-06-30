package dims.web.actionforms.rack;
import org.apache.struts.action.ActionForm;

public class RackListForm extends ActionForm  {
  private String cboSearchLocations;
  private String cboSearchRoomNumber;
  private String hdnPageNumber;
  private String hdnPageCount;
  private String txtSearchRack;
  private String[] hdnRackNumber;
  private String[] radSelect;

  public String getCboSearchLocations() {
    return cboSearchLocations;
  }

  public void setCboSearchLocations(String cboSearchLocations) {
    this.cboSearchLocations = cboSearchLocations;
  }

  public String getCboSearchRoomNumber() {
    return cboSearchRoomNumber;
  }

  public void setCboSearchRoomNumber(String cboSearchRoomNumber) {
    this.cboSearchRoomNumber = cboSearchRoomNumber;
  }

  public String getTxtSearchRack() {
    return txtSearchRack;
  }

  public void setTxtSearchRack(String txtSearchRack) {
    this.txtSearchRack = txtSearchRack;
  }



  public String[] getHdnRackNumber() {
    return hdnRackNumber;
  }

  public void setHdnRackNumber(String[] hdnRackNumber) {
    this.hdnRackNumber = hdnRackNumber;
  }

  public String[] getRadSelect() {
    return radSelect;
  }

  public void setRadSelect(String[] radSelect) {
    this.radSelect = radSelect;
  }

  public String getHdnPageCount() {
    return hdnPageCount;
  }

  public void setHdnPageCount(String hdnPageCount) {
    this.hdnPageCount = hdnPageCount;
  }

  public String getHdnPageNumber() {
    return hdnPageNumber;
  }

  public void setHdnPageNumber(String hdnPageNumber) {
    this.hdnPageNumber = hdnPageNumber;
  }

}