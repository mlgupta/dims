package dims.web.beans.batch;

import java.util.ArrayList;

public class BinderNewEditBean  {
  private String txtBinderTblPK;
  private String txtBinderStartNumber;
  private String txtBinderEndNumber;
  private String cboBinderPrefix;
  private String cboYear;

  public void setTxtBinderStartNumber(String txtBinderStartNumber) {
    this.txtBinderStartNumber = txtBinderStartNumber;
  }

  public String getTxtBinderStartNumber() {
    return txtBinderStartNumber;
  }

  public void setTxtBinderEndNumber(String txtBinderEndNumber) {
    this.txtBinderEndNumber = txtBinderEndNumber;
  }

  public String getTxtBinderEndNumber() {
    return txtBinderEndNumber;
  }

  public void setCboBinderPrefix(String cboBinderPrefix) {
    this.cboBinderPrefix = cboBinderPrefix;
  }

  public String getCboBinderPrefix() {
    return cboBinderPrefix;
  }

  public void setCboYear(String cboYear) {
    this.cboYear = cboYear;
  }

  public String getCboYear() {
    return cboYear;
  }


  public void setTxtBinderTblPK(String txtBinderTblPK) {
    this.txtBinderTblPK = txtBinderTblPK;
  }


  public String getTxtBinderTblPK() {
    return txtBinderTblPK;
  }
}