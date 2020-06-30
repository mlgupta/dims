package dims.web.actionforms.process;
import java.util.HashMap;
import java.util.Map;
import org.apache.struts.action.ActionForm;

public class RecordListForm extends ActionForm  {
  private String txtBatchNumber;
  private String txtBinderNumber;
  private String[] chkIsRecordAvailable;
  private String[] txtComments;
  private String[] hdnRecordTblPk;
  private String[] hdnRowValue;
  
  private Map rowValueMap = new HashMap();
  private Map recordTblPkMap = new HashMap();
  private Map commentsMap = new HashMap();
  private Map isRecordAvailableMap = new HashMap();

  public String getTxtBatchNumber() {
    return txtBatchNumber;
  }

  public void setTxtBatchNumber(String txtBatchNumber) {
    this.txtBatchNumber = txtBatchNumber;
  }

  public String getTxtBinderNumber() {
    return txtBinderNumber;
  }

  public void setTxtBinderNumber(String txtBinderNumber) {
    this.txtBinderNumber = txtBinderNumber;
  }

  public String getHdnRowValue( int index ){
    return (String)rowValueMap.get(new Integer(index));
  }

  public void setHdnRowValue( int index , String hdnRowValue){
    this.rowValueMap.put(new Integer(index),hdnRowValue);
  }

  public String[] getHdnRowValues(){
    return (String[])rowValueMap.values().toArray(new String[rowValueMap.size()]);
  }

  public String getHdnRecordTblPk( int index ){
    return (String)recordTblPkMap.get(new Integer(index));
  }

  public void setHdnRecordTblPk( int index , String hdnRecordTblPk){
    this.recordTblPkMap.put(new Integer(index),hdnRecordTblPk);
  }

  public String[] getHdnRecordTblPks(){
    return (String[])recordTblPkMap.values().toArray(new String[recordTblPkMap.size()]);
  }

  public String getTxtComments( int index ){
    return (String)commentsMap.get(new Integer(index));
  }

  public void setTxtComments( int index , String txtComments){
    this.commentsMap.put(new Integer(index),txtComments);
  }

  public String[] getTxtCommentss(){
    return (String[])commentsMap.values().toArray(new String[commentsMap.size()]);
  }

  public String getChkIsRecordAvailable( int index ){
    return (String)isRecordAvailableMap.get(new Integer(index));
  }

  public void setChkIsRecordAvailable( int index , String chkIsRecordAvailable){
    this.isRecordAvailableMap.put(new Integer(index),chkIsRecordAvailable);
  }

  public String[] getChkIsRecordAvailables(){
    return (String[])isRecordAvailableMap.values().toArray(new String[isRecordAvailableMap.size()]);
  }

}