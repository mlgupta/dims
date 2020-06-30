package dims.web.beans.room;

public class RoomListBean  {
  private String roomTblPk;
  private String roomNumber;
  private String locationID;

  public RoomListBean() {
  }

  public String getRoomTblPk() {
    return roomTblPk;
  }

  public void setRoomTblPk(String roomTblPk) {
    this.roomTblPk = roomTblPk;
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