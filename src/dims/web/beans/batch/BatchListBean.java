package dims.web.beans.batch;

public class BatchListBean  {
  private String batchTblPk;
  private String batchNumber;
  private String batchDate;
  private String location;
  private String department;
  

  public void setBatchTblPk(String batchTblPk) {
    this.batchTblPk = batchTblPk;
  }

  public String getBatchTblPk() {
    return batchTblPk;
  }

  public void setBatchNumber(String batchNumber) {
    this.batchNumber = batchNumber;
  }

  public String getBatchNumber() {
    return batchNumber;
  }

  public void setBatchDate(String batchDate) {
    this.batchDate = batchDate;
  }

  public String getBatchDate() {
    return batchDate;
  }

  public void setLocation(String location) {
    this.location = location;
  }

  public String getLocation() {
    return location;
  }

  public void setDepartment(String department) {
    this.department = department;
  }

  public String getDepartment() {
    return department;
  }
}