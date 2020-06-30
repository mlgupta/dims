package dims.web.beans.process;

public class BatchProcessListBean  {
  private String batchProcessTblPk;
  private String batchNumber;
  private String batchStartDate;
  private String batchEndDate;
  private String departmentId;
  private String percentageCompleted;
  private Boolean isCompleted;
  private String batchTblPk;
  private String departmentTblFk;

  public BatchProcessListBean() {
  }

  public String getBatchProcessTblPk() {
    return batchProcessTblPk;
  }

  public void setBatchProcessTblPk(String batchProcessTblPk) {
    this.batchProcessTblPk = batchProcessTblPk;
  }

  public String getBatchNumber() {
    return batchNumber;
  }

  public void setBatchNumber(String batchNumber) {
    this.batchNumber = batchNumber;
  }

  public String getBatchStartDate() {
    return batchStartDate;
  }

  public void setBatchStartDate(String batchStartDate) {
    this.batchStartDate = batchStartDate;
  }

  public String getBatchEndDate() {
    return batchEndDate;
  }

  public void setBatchEndDate(String batchEndDate) {
    this.batchEndDate = batchEndDate;
  }

  public String getDepartmentId() {
    return departmentId;
  }

  public void setDepartmentId(String departmentId) {
    this.departmentId = departmentId;
  }

  public String getPercentageCompleted() {
    return percentageCompleted;
  }

  public void setPercentageCompleted(String percentageCompleted) {
    this.percentageCompleted = percentageCompleted;
  }

  public Boolean getIsCompleted() {
    return isCompleted;
  }

  public void setIsCompleted(Boolean isCompleted) {
    this.isCompleted = isCompleted;
  }

  public String getBatchTblPk() {
    return batchTblPk;
  }

  public void setBatchTblPk(String batchTblPk) {
    this.batchTblPk = batchTblPk;
  }

  public String getDepartmentTblFk() {
    return departmentTblFk;
  }

  public void setDepartmentTblFk(String departmentTblFk) {
    this.departmentTblFk = departmentTblFk;
  }
}