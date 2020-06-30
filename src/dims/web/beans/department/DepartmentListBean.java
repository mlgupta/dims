package dims.web.beans.department;

public class DepartmentListBean  {
  private String departmentId;
  private String departmentName;
  private String departmentTblPk;
  private String departmentCntrlSeq;
  private Boolean deptEnabled;

  public DepartmentListBean() {
  }

  public String getDepartmentId() {
    return departmentId;
  }

  public void setDepartmentId(String departmentId) {
    this.departmentId = departmentId;
  }

  public String getDepartmentName() {
    return departmentName;
  }

  public void setDepartmentName(String departmentName) {
    this.departmentName = departmentName;
  }

  public String getDepartmentTblPk() {
    return departmentTblPk;
  }

  public void setDepartmentTblPk(String departmentTblPk) {
    this.departmentTblPk = departmentTblPk;
  }

  public String getDepartmentCntrlSeq() {
    return departmentCntrlSeq;
  }

  public void setDepartmentCntrlSeq(String departmentCntrlSeq) {
    this.departmentCntrlSeq = departmentCntrlSeq;
  }

  public Boolean getDeptEnabled() {
    return deptEnabled;
  }

  public void setDeptEnabled(Boolean deptEnabled) {
    this.deptEnabled = deptEnabled;
  }
}