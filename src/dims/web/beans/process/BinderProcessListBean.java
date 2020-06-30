package dims.web.beans.process;

public class BinderProcessListBean  {
  private String binderProcessTblPk;
  private String batchProcessTblFk;
  private String binderTblFk;
  private String binderName;
  private String assignedTo;
  private Boolean isProcessed;
  private String binderProcessComments;

  public BinderProcessListBean() {
  }

  public String getBinderProcessTblPk() {
    return binderProcessTblPk;
  }

  public void setBinderProcessTblPk(String binderProcessTblPk) {
    this.binderProcessTblPk = binderProcessTblPk;
  }

  public String getBatchProcessTblFk() {
    return batchProcessTblFk;
  }

  public void setBatchProcessTblFk(String batchProcessTblFk) {
    this.batchProcessTblFk = batchProcessTblFk;
  }

  public String getBinderTblFk() {
    return binderTblFk;
  }

  public void setBinderTblFk(String binderTblFk) {
    this.binderTblFk = binderTblFk;
  }

  public String getBinderName() {
    return binderName;
  }

  public void setBinderName(String binderName) {
    this.binderName = binderName;
  }

  public String getAssignedTo() {
    return assignedTo;
  }

  public void setAssignedTo(String assignedTo) {
    this.assignedTo = assignedTo;
  }

  public Boolean getIsProcessed() {
    return isProcessed;
  }

  public void setIsProcessed(Boolean isProcessed) {
    this.isProcessed = isProcessed;
  }

  public String getBinderProcessComments() {
    return binderProcessComments;
  }

  public void setBinderProcessComments(String binderProcessComments) {
    this.binderProcessComments = binderProcessComments;
  }
}