package dims.web.beans.users;

public class UserProfile  {
  private String userId;
  private String userName;
  private String userStatus;
  private String deptID;
  private String deptFK;
  private String userTblPk;
  private int recordsPerPage;

  public static final byte USER_ADMIN = 1;
  public static final byte USER_SUPERVISOR = 2;
  
  
  public UserProfile() {
    setRecordsPerPage(10);
  }

  public String getUserId() {
    return userId;
  }

  public void setUserId(String userId) {
    this.userId = userId;
  }

  public String getUserName() {
    return userName;
  }

  public void setUserName(String userName) {
    this.userName = userName;
  }

  public String getUserStatus() {
    return userStatus;
  }

  public void setUserStatus(String userStatus) {
    this.userStatus = userStatus;
  }

  public String getDeptID() {
    return deptID;
  }

  public void setDeptID(String deptID) {
    this.deptID = deptID;
  }

  public String getDeptFK() {
    return deptFK;
  }

  public void setDeptFK(String deptFK) {
    this.deptFK = deptFK;
  }

  public int getRecordsPerPage() {
    return recordsPerPage;
  }

  public void setRecordsPerPage(int recordsPerPage) {
    this.recordsPerPage = recordsPerPage;
  }

  public String getUserTblPk() {
    return userTblPk;
  }

  public void setUserTblPk(String userTblPk) {
    this.userTblPk = userTblPk;
  }
}