package dims.web.beans.rack;

public class RackListBean  {
  private String rackTblPk;
  private String rackNumber;
  private String shelfNumber;
  private String roomNumber;
  private String locationID;

  public RackListBean() {
  }

  public String getRackTblPk() {
    return rackTblPk;
  }

  public void setRackTblPk(String rackTblPk) {
    this.rackTblPk = rackTblPk;
  }

  public String getRackNumber() {
    return rackNumber;
  }

  public void setRackNumber(String rackNumber) {
    this.rackNumber = rackNumber;
  }

  public String getShelfNumber() {
    return shelfNumber;
  }

  public void setShelfNumber(String shelfNumber) {
    this.shelfNumber = shelfNumber;
  }

  public String getRoomNumber() {
    return roomNumber;
  }

  public void setRoomNumber(String roomNumber) {
    this.roomNumber = roomNumber;
  }

  public String getLocationID() {
    return locationID;
  }

  public void setLocationID(String locationID) {
    this.locationID = locationID;
  }
}