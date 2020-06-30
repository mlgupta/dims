package dims.web.beans.process;

public class RecordListBean  {
  private String recordTblPK;
  private String recordNumber;
  private String recordComments;
  private Boolean isAvailable;
  private String binderTblFk;

  public RecordListBean() {
  }

  public String getRecordTblPK() {
    return recordTblPK;
  }

  public void setRecordTblPK(String recordTblPK) {
    this.recordTblPK = recordTblPK;
  }

  public String getRecordNumber() {
    return recordNumber;
  }

  public void setRecordNumber(String recordNumber) {
    this.recordNumber = recordNumber;
  }

  public String getRecordComments() {
    return recordComments;
  }

  public void setRecordComments(String recordComments) {
    this.recordComments = recordComments;
  }

  public Boolean getIsAvailable() {
    return isAvailable;
  }

  public void setIsAvailable(Boolean isAvailable) {
    this.isAvailable = isAvailable;
  }

  public String getBinderTblFk() {
    return binderTblFk;
  }

  public void setBinderTblFk(String binderTblFk) {
    this.binderTblFk = binderTblFk;
  }
}