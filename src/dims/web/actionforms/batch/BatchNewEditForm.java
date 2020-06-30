package dims.web.actionforms.batch;

import java.util.ArrayList;
import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionForm;

public class BatchNewEditForm extends ActionForm  {
  private String txtBatchNumber;
  private String dtBatchDate;
  private String cboLocations;
  private String txtConsignmentNumber;
  private String hdnBatchTblPk;
  private String hdnRowValues;
    
  public void setDtBatchDate(String dtBatchDate) {
    this.dtBatchDate = dtBatchDate;
  }

  public String getDtBatchDate() {
    return dtBatchDate;
  }

  public void setCboLocations(String cboLocations) {
    this.cboLocations = cboLocations;
  }

  public String getCboLocations() {
    return cboLocations;
  }

  public void setTxtConsignmentNumber(String txtConsignmentNumber) {
    this.txtConsignmentNumber = txtConsignmentNumber;
  }

  public String getTxtConsignmentNumber() {
    return txtConsignmentNumber;
  }

  public void setHdnBatchTblPk(String hdnBatchTblPk) {
    this.hdnBatchTblPk = hdnBatchTblPk;
  }

  public String getHdnBatchTblPk() {
    return hdnBatchTblPk;
  }

  public void setTxtBatchNumber(String txtBatchNumber) {
    this.txtBatchNumber = txtBatchNumber;
  }

  public String getTxtBatchNumber() {
    return txtBatchNumber;
  }


  public void setHdnRowValues(String hdnRowValues) {
    this.hdnRowValues = hdnRowValues;
  }


  public String getHdnRowValues() {
    return hdnRowValues;
  }
}