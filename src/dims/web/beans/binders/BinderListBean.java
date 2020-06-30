package dims.web.beans.binders;

public class BinderListBean  {
  private String binderTblPk;
  private String binderPrefix;
  private String binderPrefixDescription;

  public BinderListBean() {
  }

  public String getBinderTblPk() {
    return binderTblPk;
  }

  public void setBinderTblPk(String binderTblPk) {
    this.binderTblPk = binderTblPk;
  }

  public String getBinderPrefix() {
    return binderPrefix;
  }

  public void setBinderPrefix(String binderPrefix) {
    this.binderPrefix = binderPrefix;
  }

  public String getBinderPrefixDescription() {
    return binderPrefixDescription;
  }

  public void setBinderPrefixDescription(String binderPrefixDescription) {
    this.binderPrefixDescription = binderPrefixDescription;
  }
}