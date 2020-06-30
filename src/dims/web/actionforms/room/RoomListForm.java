package dims.web.actionforms.room;
import org.apache.struts.action.ActionForm;

public class RoomListForm extends ActionForm  {
  private String cboSearchLocations;
  private String txtSearchRoomNumber;
  private String hdnPageNumber;
  private String hdnPageCount;
  private String[] radSelect;
  private String[] hdnRoomNumber;

  public String getCboSearchLocations() {
    return cboSearchLocations;
  }

  public void setCboSearchLocations(String cboSearchLocations) {
    this.cboSearchLocations = cboSearchLocations;
  }

  public String getTxtSearchRoomNumber() {
    return txtSearchRoomNumber;
  }

  public void setTxtSearchRoomNumber(String txtSearchRoomNumber) {
    this.txtSearchRoomNumber = txtSearchRoomNumber;
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

  public String[] getHdnRoomNumber() {
    return hdnRoomNumber;
  }

  public void setHdnRoomNumber(String[] hdnRoomNumber) {
    this.hdnRoomNumber = hdnRoomNumber;
  }
}