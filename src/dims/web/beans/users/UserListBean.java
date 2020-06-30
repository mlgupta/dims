package dims.web.beans.users;

public class UserListBean  {
  private String userTblPk;
  private String userID;
  private String userName;
  private String userType;
  private String departmentID;

  public UserListBean() {
  }

  public String getUserTblPk() {
    return userTblPk;
  }

  public void setUserTblPk(String userTblPk) {
    this.userTblPk = userTblPk;
  }

  public String getUserID() {
    return userID;
  }

  public void setUserID(String userID) {
    this.userID = userID;
  }

  public String getUserName() {
    return userName;
  }

  public void setUserName(String userName) {
    this.userName = userName;
  }

  public String getUserType() {
    return userType;
  }

  public void setUserType(String userType) {
    this.userType = userType;
  }

  public String getDepartmentID() {
    return departmentID;
  }

  public void setDepartmentID(String departmentID) {
    this.departmentID = departmentID;
  }
}